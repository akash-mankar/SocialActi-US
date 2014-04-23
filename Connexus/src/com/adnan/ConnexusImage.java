package com.adnan;
import java.util.Comparator;
import java.util.Date;

import com.google.common.base.Joiner;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class ConnexusImage implements Comparable<ConnexusImage> {

	@Id
	public Long id;
	@Index public Long streamId;
	public String streamName;
	public String bkUrl;
	public Date createDate;
	public String userId;
	public double lat;
	public double lon;

	@SuppressWarnings("unused")
	private ConnexusImage() {
	}

	public ConnexusImage(Long streamId, String sName, String user, String bkUrl) {
		this.streamId = streamId;
		this.bkUrl = bkUrl;
		this.streamName = sName;
		this.userId = user;
		this.createDate = new Date();
	}
	
	@Override
	public String toString() {
		// Joiner is from google Guava (Java utility library), makes the toString method a little cleaner
		Joiner joiner = Joiner.on(":");
		System.out.println(id);
		System.out.println(streamId);
		System.out.println(bkUrl);
		System.out.println(createDate.toString());
		return joiner.join(id.toString(), streamId, bkUrl==null ? "null" : bkUrl, createDate.toString());
	}
	
	
	public void setLocation(double lat,double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	
	public double distanceFrom(double lat2,  double lon2) {		
		double lat1 = this.lat;
		double lon1 = this.lon;
		if (lat1 == 0 || lat2== 0 || lon1 == 0 || lon2 == 0) {
			return -1;
		}
	    final int R = 6371; // Radius of the earth

	    Double latDistance = deg2rad(lat2 - lat1);
	    Double lonDistance = deg2rad(lon2 - lon1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters
	    return distance;
	}

	private double deg2rad(double deg) {
	    return (deg * Math.PI / 180.0);
	}

	// Need this for sorting images by date
	@Override
	public int compareTo(ConnexusImage other) {
		if (createDate.after(other.createDate)) {
			return -1;
		} else if (createDate.before(other.createDate)) {
			return 1;
		}
		return 0;
	}
	
	public Long getId() {
		return this.id;
	}
	public String getbkUrl() {
		return this.bkUrl;
	}	
	public Long getStreamId() {
		return this.streamId;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	
	public String getUsrId() {
		return this.userId;
	}
}
