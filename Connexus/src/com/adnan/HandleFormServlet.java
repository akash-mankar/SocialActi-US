package com.adnan;
import java.util.*;
import java.lang.*;
import java.io.*;
import static java.lang.System.out;
import javax.servlet.http.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import static com.googlecode.objectify.ObjectifyService.ofy;
import java.lang.*;

import java.util.Properties;


// Backs up CreateStream.html form submission. Trivial since there's no image uploaded, just a URL
@SuppressWarnings("serial")
public class HandleFormServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	    String answer = req.getParameter("answer"); 
		String msgx   = req.getParameter("Message");
		String ip     = req.getRemoteAddr();
		
		String freeGeoipUrl =  "https://freegeoip.net/json/" + ip;

	    String[] Splitpairs = null;
		try {
		    URL url = new URL(freeGeoipUrl);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		    String line;
		    while ((line = reader.readLine()) != null) {
		       //System.out.println("LINE:" + line);
		    	Splitpairs = line.split(",");
		    }
		    reader.close();

		} catch (MalformedURLException e) {
		    // ...
		}

	    String[] country = Splitpairs[2].split(":");  // index 2 is country
	    String[] state   = Splitpairs[4].split(":");  // index 4 is state
	    String[] city    = Splitpairs[5].split(":");   // index 5 is city
		
	    
	    if(city[1].isEmpty()){
	    	city[1] = "NA";
	    }
	    if(state[1].isEmpty()){
	    	state[1] = "NA";
	    }
	    if(state[1].isEmpty()){
	    	state[1] = "NA";
	    }
		
	    String finCountry = country[1].replace("\"","");
	    String finState = state[1].replace("\"","");
	    String finCity = city[1].replace("\"","");
	    
		if(answer.isEmpty() || msgx.isEmpty()){
			resp.sendRedirect("/Error.jsp");
		}else {
			// System.out.println("Ans:" +  answer + " msgx:" +  msgx + " ip:" + ip + " country:" +  country[1]
			//		+ " State:" + state[1] + " City:" + city[1]);
			Worker w = new Worker(answer, msgx, ip, finCountry, finState, finCity);
			OfyService.ofy().save().entity(w).now();
			resp.sendRedirect("/ThankYouResponse.jsp");
		}
	}
}