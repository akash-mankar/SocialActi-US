package com.adnan;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class AutoCompleteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
	String term = request.getParameter("term");
	
	List<Stream> th = OfyService.ofy().load().type(Stream.class).list();
	List<String> op = new ArrayList<String>();
	for (Stream s : th ) {
		if (s.nameTagSearch(term)) {	
			op.add(s.getName());
			if (op.size() > 20) {
				break;
			}
		}
	}
	Collections.sort(op ,Collections.reverseOrder());
	Gson gson = new Gson();
	String json = gson.toJson(op);
	response.setContentType("application/json");
	response.getWriter().write(json.toString());
	}

}  