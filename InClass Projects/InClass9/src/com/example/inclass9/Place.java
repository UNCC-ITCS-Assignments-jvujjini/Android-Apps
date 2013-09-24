/*
*a. Assignment # : In Class 9
*
*b. File Name : Place.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass9;

import org.json.JSONException;
import org.json.JSONObject;

public class Place {

	private double latitude;
	private double longitude;
	private String name;
	
	public Place(JSONObject personJSONObject) throws JSONException{
		this.latitude = personJSONObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
		this.longitude = personJSONObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
		this.name = personJSONObject.getString("name");
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Place [latitude=" + latitude + ", longitude=" + longitude
				+ ", name=" + name + "]";
	}

}
