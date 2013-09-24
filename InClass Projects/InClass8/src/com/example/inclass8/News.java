/*
*a. Assignment # : In Class 8
*
*b. File Name : News.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass8;

public class News {
	
	private String title;
	private String url;
	private long _id;

	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "News [title=" + title + ", url=" + url + "]";
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
