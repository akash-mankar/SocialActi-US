package com.adnan;

import java.lang.*;
import java.util.*;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.google.common.base.Joiner;
import com.googlecode.objectify.ObjectifyFactory;

@Entity
public class LeaderBoard implements Comparable<LeaderBoard>{

	static {
		 ObjectifyService.register(LeaderBoard.class);
	}
	// id is set by the datastore for us
	@Id
	public Long id;
	public Long topStream1;
	public Integer viewCount1;
	public Long topStream2;
	public Integer   viewCount2;
	public Long topStream3;
	public Integer   viewCount3;
	public Date  createDate;
  
	// TODO: figure out why this is needed
	@SuppressWarnings("unused")
	private LeaderBoard() {
	}


	public LeaderBoard(Long topStream1, Integer viewCount1, Long topStream2, Integer viewCount2,
			Long topStream3, Integer viewCount3){
		this.topStream1 = topStream1;
		this.viewCount1 = viewCount1;
		this.topStream2 = topStream2;
		this.viewCount2 = viewCount2;
		this.topStream3 = topStream3;
		this.viewCount3 = viewCount3;
		this.createDate = new Date();
	}
	
	public Date getCreateDate() {		
		return this.createDate;
	}
	
	public Long getTop1(){
		return this.topStream1;
	}
	public Long getTop2(){
		return this.topStream2;
	}
	public Long getTop3(){
		return this.topStream3;
	}
	public Integer getViewCount1(){
		return this.viewCount1;
	}
	public Integer getViewCount2(){
		return this.viewCount2;
	}
	public Integer getViewCount3(){
		return this.viewCount3;
	}
	@Override
	public int compareTo(LeaderBoard other) {
		if (createDate.after(other.createDate)) {
			return -1;
		} else if (createDate.before(other.createDate)) {
			return 1;
		}
		return 0;
	}
}

//Stream st = ofy().load().type(Stream.class).id(123L).get();