package com.adnan;

import java.lang.*;
import java.util.*;


import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.google.common.base.Joiner;
import com.googlecode.objectify.ObjectifyFactory;

@Entity
public class CronData  {

	static {
		 ObjectifyService.register(CronData.class);
	}
	// id is set by the datastore for us
	@Id
	public Long id;
	public long startTimems;
	public long timeToSendms;
   
	// TODO: figure out why this is needed
	@SuppressWarnings("unused")
	private CronData() {
	}
	
	public CronData(long startTime, long freq) {
		this.startTimems = startTime;
		this.timeToSendms = freq;
	}

	public long getFreq() {
		return this.timeToSendms;
	}
	public long getstartTime(){
		return this.startTimems;
	}
	public void setFreq(long x) {
		timeToSendms = x;
	}
	public void setstartTime(long x){
		startTimems = x;
	}
	
}

//Stream st = ofy().load().type(Stream.class).id(123L).get();