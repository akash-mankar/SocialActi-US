package com.adnan;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@SuppressWarnings("serial")
public class SubscriberServletAPI extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String emailId = req.getParameter("userId");
		List<Subscriber> sublst = OfyService.ofy().load().type(Subscriber.class).list();	
		ArrayList<ConnexusImage> images = new ArrayList<ConnexusImage>();
		int flag = 0;
		Long stId;
		for (Subscriber u : sublst) {				
			if ((u.getEmailId()).equals(emailId)) {
				for (int i = 0; i < u.getSubStreamSize();i++) {										
					stId = u.getSubStream(i);
					images.addAll(OfyService.ofy().load().type(ConnexusImage.class).filter("streamId",stId).list());					
				}
				break;
			}
		}
		Collections.sort(images);	
		if (images.size() >= 16) {
			for (int j = 16; j < images.size(); j++){
			images.remove(j);
			}
		}
	
		Gson gson = new Gson();
		String json = gson.toJson(images);
		resp.setContentType("application/json");
		resp.getWriter().print(json);
	
	}
}