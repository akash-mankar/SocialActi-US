package com.adnan;

import java.lang.*;
import java.util.*;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.google.common.base.Joiner;
import com.googlecode.objectify.ObjectifyFactory;

@Entity
public class StreamViews implements Comparable<StreamViews> {

	static {
		 ObjectifyService.register(StreamViews.class);
	}
	// id is set by the datastore for us
	@Id
	public Long id;
	public Long streamId;
	public Date viewDate;
  
	// TODO: figure out why this is needed
	@SuppressWarnings("unused")
	private StreamViews() {
	}
	
	@Override
	public String toString() {
		Joiner joiner = Joiner.on(":");
		return joiner.join(id.toString(), streamId.toString(), viewDate.toString());
 	}
	

	public StreamViews(Long streamId){
		this.streamId = streamId;
		this.viewDate = new Date();
	}

	@Override
	public int compareTo(StreamViews other) {
		if (viewDate.after(other.viewDate)) {
			return -1;
		} else if (viewDate.before(other.viewDate)) {
			return 1;
		}
		return 0;
	}
	
	public Long getStreamId() {
		return this.streamId;
	}
	public Date getviewDate() {
		return this.viewDate;
	}
	
/*pubc Date getRecentDate() {
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
	}*/	
}

//Stream st = ofy().load().type(Stream.class).id(123L).get();