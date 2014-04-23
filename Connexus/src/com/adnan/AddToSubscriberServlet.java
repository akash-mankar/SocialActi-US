package com.adnan;
import java.io.IOException;
import javax.servlet.http.*;

import static com.googlecode.objectify.ObjectifyService.ofy;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.*;
// Backs up CreateStream.html form submission. Trivial since there's no image uploaded, just a URL
@SuppressWarnings("serial")
public class AddToSubscriberServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    String emailId = user.getEmail();
	    List<Subscriber> sublst = ofy().load().type(Subscriber.class).list();
	    Long st = new Long(req.getParameter("stId"));
	    System.out.println("long val ="+st);
		for (Subscriber u : sublst) {
			if (u.getEmailId() == emailId) {
				u.subscribe(st);
				ofy().save().entity(u).now();
				break;
			}
		}	      		
		resp.sendRedirect("/ShowStream.jsp");
	}
}