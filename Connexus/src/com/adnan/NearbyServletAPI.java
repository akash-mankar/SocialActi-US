package com.adnan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import static java.lang.System.out;


@SuppressWarnings("serial")
public class NearbyServletAPI extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		double lat;
		double lon;
		
		if (req.getParameter("lat") == null){
			lat = 0; lon = 0;
		} else {
		lat = Double.parseDouble(req.getParameter("lat"));
		lon = Double.parseDouble(req.getParameter("lon"));
		}
		List<ConnexusImage> images = OfyService.ofy().load().type(ConnexusImage.class).list();		
		ArrayList<Distance> distImages = new ArrayList<Distance>();
		ArrayList<Distance> sendImages = new ArrayList<Distance>();
		for (int i = 0; i < images.size(); i++) {
			ConnexusImage img = images.get(i);
			double dist = img.distanceFrom(lat, lon);
			if (dist == -1) continue;
			Distance d = new Distance(img.streamId, img.streamName, img.bkUrl,(float)dist,i);
			distImages.add(d);			
		}
		Collections.sort(distImages);
		Integer more = 0;
		if (req.getParameter("more") != null) {
			 more = Integer.parseInt(req.getParameter("more"));
		}	
		
		for (int j = more*16; (j < distImages.size()) && 
							  (j < (more+1)*16); j++)  {
		sendImages.add(distImages.get(j));
		}		
		
		Gson gson = new Gson();
		String json = gson.toJson(sendImages);
		resp.setContentType("application/json");
		resp.getWriter().print(json);	
	}
}