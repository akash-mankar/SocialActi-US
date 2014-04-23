package com.adnan;
import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.*;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.*;
// Backs up CreateStream.html form submission. Trivial since there's no image uploaded, just a URL
@SuppressWarnings("serial")
public class SendReportServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String msgx = null;
		String weblink = "x-y-z-a.appspot.com/Trending.jsp"; 
		String unsubs = "\n\nTo unsubscribe from mailing list, please go to above link and click on <Do not send a report>";
		List<LeaderBoard> LBlist = OfyService.ofy().load().type(LeaderBoard.class).list();
  		Collections.sort(LBlist);
  		Stream top1;
  		Stream top2;
  		Stream top3;
  		Integer viewCount1;
  		Integer viewCount2;
  		Integer viewCount3;
  		int LBsize = LBlist.size();
  		/* figure out frequency of spam */
  		 
  		Date currentDate = new Date();
		String radioVal = req.getParameter("radios");		
		if (radioVal != null) { //when trending options are clicked
			CronData savePref = ofy().load().type(CronData.class).first().get();
			if (savePref == null) {
			savePref = new CronData(currentDate.getTime(),99999999999999L);
			}
			else {
				savePref.setstartTime(currentDate.getTime());
			}
			if(radioVal.equals("dont")){
				savePref.setFreq(99999999999999L);				
			}
			else if(radioVal.equals("fivemins")){
				savePref.setFreq(60 * 5 * 1000);
			}
			else if(radioVal.equals("onehour")){
				savePref.setFreq(60 * 60 * 1000);
			}
			else if(radioVal.equals("everyday")){
				savePref.setFreq(60 * 60 * 24 * 1000);
			}			
			ofy().save().entity(savePref).now();
			resp.sendRedirect("/Trending.jsp");
		}// if Trending button clicked
		else { //Cron job executing this code
			CronData savePref = ofy().load().type(CronData.class).first().get();
			if (savePref == null) { //no preference set
				msgx = "There are no Trending Streams in the past hour :(";
				//exit , no mails
			}
			else { //Prefernce set
		  		/* Find out the Trending streams */
		  		if (LBlist.isEmpty()){ 
		  			msgx = "There are no Trending Streams in the past hour :(";
		  		}
		  		else{ //if LB non empty, savePref not null, cronjob execution		 			
		  	  		LeaderBoard LBelement = LBlist.get(0);
		  	  		long MilliSec = currentDate.getTime() - (LBelement).getCreateDate().getTime(); 			
		  			if (MilliSec < 3600000) {  			  			
			  			Long Top1 = LBlist.get(0).getTop1();  			
			  			Long Top2 = (LBlist.get(0).getTop2());  			
			  			Long Top3 = (LBlist.get(0).getTop3());  			
			  			viewCount1 = (LBlist.get(0)).getViewCount1();
			  			viewCount2 = (LBlist.get(0)).getViewCount2();
			  			viewCount3 = (LBlist.get(0)).getViewCount3();
			  			top1 = (OfyService.ofy().load().type(Stream.class).id(Top1)).get(); 
			  			top2 = (OfyService.ofy().load().type(Stream.class).id(Top2)).get(); 
			  			top3 = (OfyService.ofy().load().type(Stream.class).id(Top3)).get();
			  			msgx = "Here are the trending streams from the past hour "
			  					+ "\n " + top1.getName() + " with view count of " + viewCount1 
			  					+ "\n " + top2.getName() + " with view count of " + viewCount2
			  					+ "\n " + top3.getName() + " with view count of " + viewCount3
			  					+ "\n " + "View them by following this link\n " + weblink;		  			
		  			} // if less  than 1 hr 
		  			else {
		  			msgx = "There are no Trending Streams in the past hour :(";
		  			}
		  		} // else Leaderboard not empty
		  		
		  		Date curDate = new Date();
		  		long curTime = curDate.getTime();
		  		long isItTime = (curTime - savePref.getstartTime());
		  		if (isItTime >= savePref.getFreq()) { //its time to spam
		  			savePref.setstartTime(isItTime);
		  			ofy().save().entity(savePref).now();
				  	/************* Mail **************************/
					Properties props = new Properties();
			        Session session = Session.getDefaultInstance(props, null);		        
			        resp.setContentType("text/html") ;		        
			        try {
			        	Message msg = new MimeMessage(session);
			            msg.setFrom(new InternetAddress("d.chinmaya@gmail.com"));	            
			            msg.addRecipient(Message.RecipientType.TO,
		                new InternetAddress("kamran.ks+aptmini@gmail.com"));	      
			            msg.setSubject("Connexus --> Trending streams of the hour");                         
			            msg.setText(msgx + unsubs);
			            Transport.send(msg);	        	
			        	} catch (AddressException e) {
			            // ...
			        	} catch (MessagingException e) {
			            // ...
			        	} catch (Exception e) {
			        	//out.println("email id wrong " + e);
			        	}
						/***************Mail done ****************/
		  			} //its time to mail
				} // if no preference set
			} // cron job execution		
		}//http
	}//class