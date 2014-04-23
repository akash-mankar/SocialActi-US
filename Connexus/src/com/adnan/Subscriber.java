package com.adnan;

import java.util.*;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.google.common.base.Joiner;
import com.googlecode.objectify.ObjectifyFactory;

@Entity
public class Subscriber  {

	static {
		 ObjectifyService.register(Subscriber.class);
	}
	// id is set by the datastore for us
	@Id
	public Long id;
	public String emailId;
	public ArrayList<Long> subscribedStreams = new ArrayList<Long>();
	public ArrayList<Long> myStreams = new ArrayList<Long>();
   
	// TODO: figure out why this is needed
	@SuppressWarnings("unused")
	private Subscriber() {
	}
	
/*	@Override
	public String toString() {
		Joiner joiner = Joiner.on(":");
		return joiner.join(id.toString(), name, tags, createDate.toString());
 	}
*/
	public Subscriber(String email) {
		this.emailId = email;		
	}

	
	public Long getId() {
		return this.id;
	}
	
	public String getEmailId() {
		return this.emailId;
	}
	
	public void setEmailId(String id) {
		emailId = id;
	}
	
	public Long getSubStream(int i) {
		try {
			Long st = (this.subscribedStreams).get(i);
			return(st);
		} catch ( IndexOutOfBoundsException e ) {
			return (long)0;
		}
	}

	
	public int getSubStreamSize() {
		return (this.subscribedStreams).size();
	}
	
	public void subscribe(Long streamId) {
		subscribedStreams.add(streamId);
	}
	
	public void unsubscribe(Long streamId) {
		subscribedStreams.remove(streamId);
	}
	
	public void addStream(Long streamId) {
		myStreams.add(streamId);
	}
	
	public boolean alreadySub(Long st) {
		return (subscribedStreams.contains(st));		
		}
	
	
}

//Stream st = ofy().load().type(Stream.class).id(123L).get();