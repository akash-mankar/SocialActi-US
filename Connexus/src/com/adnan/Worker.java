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
public class Worker{

	static {
		 ObjectifyService.register(Worker.class);
	}
	// id is set by the datastore for us
	@Id
	public Long id;
   // public String name;
	// public String mturkId;
	public String answer;
	public String message;
	public String ip;
	public String country;
	public String state;
	public String city;
   
	// TODO: figure out why this is needed
	@SuppressWarnings("unused")
	private Worker() {
	}
	
	@Override
	public String toString() {
		Joiner joiner = Joiner.on(":");
		return joiner.join(id.toString(), /*name, mturkId,*/ answer, message, ip, country, state, city);
 	}

	public Worker(/*String name, String mturkId,*/ String answer, String message, String ip, 
			String country, String state, String city) {
		//this.name = name;
		//this.mturkId =  mturkId;
		this.answer = answer;
		this.message = message;
		this.ip = ip;
		this.country = country;
		this.state = state;
		this.city = city;
	}

	public Long getWorkerId() {
		return this.id;
	}
	public String getAnswer(){
		return this.answer;
	}
	
	/*public String getName() {
		return this.name;
	}*/
	
	/*public String getMturkId() {
		return this.mturkId;
	}*/
	
	public String getMessage() {
		return this.message;
	}
	
	public String getIP(){
		return this.ip;
	}
	
	public String getLocation(){
		return this.country + " " + this.state + " " + this.city;
	}
	public String getCountry(){
		return this.country;
	}
	public String getState(){
		return this.state;
	}
	public String getCity(){
		return this.city;
	}
}