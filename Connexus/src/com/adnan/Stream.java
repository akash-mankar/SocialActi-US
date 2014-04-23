package com.adnan;

import java.lang.*;
import java.util.*;


import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.google.common.base.Joiner;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Stream implements Comparable<Stream> {

	static {
		 ObjectifyService.register(Stream.class);
	}
	// id is set by the datastore for us
	@Id
	public Long id;
	@Index public String name;
	public String tags;
	public Date createDate;
	public String coverImageUrl;
	public String userId;  // New
	public int viewCount;
   
	// TODO: figure out why this is needed
	@SuppressWarnings("unused")
	private Stream() {
	}
	
	@Override
	public String toString() {
		Joiner joiner = Joiner.on(":");
		return joiner.join(id.toString(), name, tags, createDate.toString());
 	}

	public Stream(String name, String tags, String coverImageUrl, String userId) {
		this.name = name;
		this.tags = tags;
		this.coverImageUrl = coverImageUrl;
		this.userId = userId;
		this.createDate = new Date();
		this.viewCount = 0;
	}

	@Override
	public int compareTo(Stream other) {
		if (createDate.after(other.createDate)) {
			return -1;
		} else if (createDate.before(other.createDate)) {
			return 1;
		}
		return 0;
	}

	public Long getStreamId() {
		return this.id;
	}
	public String getCoverImageUrl(){
		return this.coverImageUrl;
	}
	
	public void setDate() {
		createDate = new Date();
	}
	
	public int getViewCount() {
		return this.viewCount;
	}
	
	public void incViewCount() {
		viewCount++;
	}
	
	public void decViewCount() {
		viewCount--;
	}
	
	public void clearViewCount() {
		viewCount = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public Date getRecentDate() {
		Date tempDate = new Date();
		tempDate.setDate(0);
		List<ConnexusImage> images = OfyService.ofy().load().type(ConnexusImage.class).list();
		for ( ConnexusImage img : images ) {
		    if ( (img.getStreamId()).equals(this.id) ) {        		    	
		    	if ((img.getCreateDate()).after(tempDate)) {
					tempDate=img.getCreateDate();        		
		    	}		    	
			}	       	
		}
		return tempDate;
	}
	
	public int getPicCount() {
		int tempCount = 0;
		List<ConnexusImage> images = OfyService.ofy().load().type(ConnexusImage.class).list();
		for ( ConnexusImage img : images ) {
		    if ( (img.getStreamId()).equals(this.id) ) {        		    	
		  		tempCount++;
			}	       	
		}
		return tempCount;
	}
	
	public boolean nameTagSearch(String sval) {
		if (sval == null) return false;
		StringTokenizer st = new StringTokenizer(sval);
		String str = new String();
		while (st.hasMoreTokens()) {
			str = st.nextToken();
		  	if (name == null || tags == null) {
		  		return false;
		  	}
			if (tags.toLowerCase().contains(str.toLowerCase()) || 
					name.toLowerCase().contains(str.toLowerCase())) {
				return (true);
			}
		}
		return (false);
	}
	
	public void appendTags(String s) {
		if (s == null) {
			return;
		}
		else {
			this.tags+=" " + s; 
		}
	}
}

//Stream st = ofy().load().type(Stream.class).id(123L).get();