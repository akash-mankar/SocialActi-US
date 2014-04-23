package com.adnan;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("serial")
public class AllStreamsServletAPI extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AllStreamsServletAPI.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		log.warning("Entering AllStreamsServletAPI");
		List<Stream> allStreams = OfyService.ofy().load().type(Stream.class).list();
        List<Stream> subsetStreams = null;
		String jsonSelector = req.getParameter("selector");
        if ( jsonSelector == null ) {
           subsetStreams = allStreams;
        } else {
          Gson gson = new Gson();
          Type t = new TypeToken<Selector>(){}.getType();
          Selector sel = gson.fromJson(jsonSelector, t);
          subsetStreams = sel.selectStreams(allStreams);
        }		
		
		Collections.sort(subsetStreams);
		Gson gson = new Gson();
		String json = gson.toJson(subsetStreams);
		resp.setContentType("application/json");
		resp.getWriter().print(json);
		
		// for debugging
		Type t = new TypeToken<List<Stream>>(){}.getType();
		List<Stream> fromJson = (List<Stream>) gson.fromJson(json, t);
		for (Stream s : fromJson ) {
			System.out.println(s);
		}
	}
}