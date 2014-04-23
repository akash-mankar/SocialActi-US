package com.adnan;
import java.util.*;
import java.lang.*;
import java.io.*;
import static java.lang.System.out;
import java.io.IOException;
import javax.servlet.http.*;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.adnan.Stream;
import com.adnan.ConnexusImage;
import com.adnan.Subscriber;
import java.lang.*;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


// Backs up CreateStream.html form submission. Trivial since there's no image uploaded, just a URL
@SuppressWarnings("serial")
public class CreateStreamServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    String stN = req.getParameter("streamName");
	   	    
		String tag = req.getParameter("Tags"); 
		String msgx = req.getParameter("Message");
		if (msgx.equals("Type Invite here"))
		{
			msgx = "";
		}
		msgx += "\n\nHi,\nThis is Connexus @ http://x-y-z-a.appspot.com. A website to store streams, and pictures. You can upload your streams too!";
		msgx += "\nGo to http://x-y-z-a.appspot.com/Login.jsp to login to Connexus \n";
		msgx += "\nAfter logging in you can view the streams you have subscribed to under \"Manage\" section";

	    List<Stream> th = OfyService.ofy().load().type(Stream.class).list();
		Collections.sort(th);	
		String errMsg = "dummy";
		int flag = 0;
		for (Stream s : th ) {
			try {	
				if ((stN == null) || (stN == "") || stN.equals("Name your Stream") ) {
					resp.sendRedirect("Error.jsp?msg=2");
					flag = 1;
					break;
				}
				if ((s.getName().toLowerCase()).equals(stN.toLowerCase())) {
					resp.sendRedirect("Error.jsp?msg=1");
					flag = 1;
					break;
				}				
			} catch(Exception e) {
			     continue;
			}
		}
		if (flag == 0) {
		Stream s = new Stream(req.getParameter("streamName"),
		req.getParameter("Tags"), req.getParameter("url"), user.getEmail());

	    List<Subscriber> sublst = OfyService.ofy().load().type(Subscriber.class).list();
    	 
		 for (Subscriber u : sublst) {
			if (u.getEmailId().equals(user.getEmail())) {
				u.addStream(s.getStreamId());
				ofy().save().entity(u).now();
				break;
			}				
		 }
		// persist to datastore
		ofy().save().entity(s).now();
		//email subscribers
		String subs = new String(req.getParameter("Subscribers"));
		String[] tokens; 
		tokens = subs.split(";");
		out.println("subs = "+ subs + " tokens = " + tokens[0] );
		int i = 0;			
		 Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	        
	        resp.setContentType("text/html") ;
	        
	        try {
	        	while (i < tokens.length) {	
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(user.getEmail()));	            
	            msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress(tokens[i]));	      
	            msg.setSubject("Connexus --> You have been invited to view the photo stream "+stN);
	            msg.setText(msgx);
	            Transport.send(msg);
	            i++;
	        	}
	        } catch (AddressException e) {
	            // ...
	        } catch (MessagingException e) {
	            // ...
	        } catch (Exception e) {
	        	//out.println("email id wrong " + e);
	        }		
		
		 resp.sendRedirect("/ViewAllStreams.jsp");
		}
	}
}