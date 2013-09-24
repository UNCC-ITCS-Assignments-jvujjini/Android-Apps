/*
*a. Assignment # : In Class 6
*
*b. File Name : Movie.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass6;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
	
	private String title;
	private String synopsis;
	private int runtime;
	
	public Movie(JSONObject personJSONObject) throws JSONException{
		this.title = personJSONObject.getString("title");
		this.runtime = personJSONObject.getInt("runtime");
		this.synopsis = personJSONObject.getString("synopsis");
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	@Override
	public String toString() {
		return title+ ";" + runtime + ";" + synopsis;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

}
