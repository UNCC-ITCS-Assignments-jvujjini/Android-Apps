/*
*a. Assignment # : HW # 3
*
*b. File Name : Picture.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.unccimages;

import org.json.JSONException;
import org.json.JSONObject;

public class Picture {
	
	private String id;
	private int picCount;
	private String title;
	
	public Picture(JSONObject picJSONObject) throws JSONException{
		this.id = picJSONObject.getString("id");
		this.picCount = picJSONObject.getInt("photos");
		this.title = picJSONObject.getJSONObject("title").getString("_content");
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id + ";" + picCount + ";" + title;
	}

	public int getPicCount() {
		return picCount;
	}

	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}

}