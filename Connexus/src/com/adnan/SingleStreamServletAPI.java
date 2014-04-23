package com.adnan;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("serial")
public class SingleStreamServletAPI extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		List<ConnexusImage> allImages = OfyService.ofy().load().type(ConnexusImage.class).list();
		Long streamId = new Long(req.getParameter("streamId"));
		
		List<ConnexusImage> result = new ArrayList<ConnexusImage>();
		List<ConnexusImage> sendImages = new ArrayList<ConnexusImage>();
	       
		for (ConnexusImage s : allImages ) {
			if ( s.streamId.equals(streamId) ) {
				result.add(s);
			}
		}
		Collections.sort(result);
		Integer more = 0;
		if (req.getParameter("more") != null) {
			 more = Integer.parseInt(req.getParameter("more"));
		}	
		
		for (int j = more*16; (j < result.size()) && 
							  (j < (more+1)*16); j++)  {
		sendImages.add(result.get(j));
		}		
		
		Gson gson = new Gson();
		String json = gson.toJson(sendImages);
		resp.setContentType("application/json");
		resp.getWriter().print(json);	
	}
}