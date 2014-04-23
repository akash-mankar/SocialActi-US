package com.adnan;
import java.util.Comparator;
import java.util.Date;

import com.google.common.base.Joiner;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


public class Distance  implements Comparable<Distance>{	
	public Long streamId;
	public String streamName;
	public String bkUrl;	
	public double dist;
	public int index;

	@SuppressWarnings("unused")
	private Distance() {
	}

	public Distance(Long streamId, String sName, String bkUrl, double dist, int index) {
		this.streamId = streamId;
		this.bkUrl = bkUrl;
		this.streamName = sName;
		this.dist = dist;
		this.index = index;
	}
	
	@Override
	public  int compareTo(Distance other) {
		if ((this.dist - other.dist) > 0) {
			return 1;
		}else 
		return -1;		
	}
		
	public String getbkUrl() {
		return this.bkUrl;
	}	
	public Long getStreamId() {
		return this.streamId;
	}
}
