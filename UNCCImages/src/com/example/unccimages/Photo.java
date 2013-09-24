/*
*a. Assignment # : HW # 3
*
*b. File Name : Photo.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.unccimages;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo {
	
	//private int id;
	//private int picCount;
	private String url_sq;
	private String url_m;
	
	public Photo(JSONObject picJSONObject) throws JSONException{
		this.url_sq = picJSONObject.getString("url_sq");
		this.url_m = picJSONObject.getString("url_m");
		//this.title = picJSONObject.getJSONObject("title").getString("_content");
	}
	
	
	public String getUrlSq() {
		return url_sq;
	}

	public void setUrlSq(String url_sq) {
		this.url_sq = url_sq;
	}

	@Override
	public String toString() {
		return "Photo [url_sq=" + url_sq + ", url_m=" + url_m + "]";
	}


	public String getUrlM() {
		return url_m;
	}

	public void setUrlM(String url_m) {
		this.url_m = url_m;
	}

	//@Override
	/*public String toString() {
		return url_sq + ";" + url_m;
	}*/

}