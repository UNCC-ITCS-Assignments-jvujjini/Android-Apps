/*
*a. Assignment # : MidTerm
*
*b. File Name : MovieActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/


package com.example.rottentomatoes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
	
	private String id, title, mpaa_rating, critics_rating, audience_rating, thumbnailSrc, detailedImageSrc, releaseDate, url, critics_score, audience_score,genre;
	private int year, runtime;
	JSONArray genreArray = null;
	
	public Movie(JSONObject uniqueMovie) throws JSONException {
		
		this.id = uniqueMovie.getString("id");
		this.title = uniqueMovie.getString("title");
		this.mpaa_rating = uniqueMovie.getString("mpaa_rating");
		if(uniqueMovie.getJSONObject("ratings").has("critics_rating")) {
			this.critics_rating = uniqueMovie.getJSONObject("ratings").getString("critics_rating");
		} else { this.critics_rating = "NA"; }
		if(uniqueMovie.getJSONObject("ratings").has("audience_rating")) {
			this.audience_rating = uniqueMovie.getJSONObject("ratings").getString("audience_rating");
		} else { this.audience_rating = "NA"; }
		if(uniqueMovie.getJSONObject("ratings").has("critics_score")) {
			this.critics_score = Integer.toString(uniqueMovie.getJSONObject("ratings").getInt("critics_score"));
		} else { this.critics_score = "NA"; }
		if(uniqueMovie.getJSONObject("ratings").has("audience_score")) {
			this.audience_score = Integer.toString(uniqueMovie.getJSONObject("ratings").getInt("audience_score"));
		} else { this.audience_score = "NA"; }
		this.thumbnailSrc = uniqueMovie.getJSONObject("posters").getString("thumbnail");
		this.detailedImageSrc = uniqueMovie.getJSONObject("posters").getString("detailed");
		this.releaseDate = uniqueMovie.getJSONObject("release_dates").getString("theater");
		this.genre = "";
		this.url = uniqueMovie.getJSONObject("links").getString("alternate");
		this.year = uniqueMovie.getInt("year");
		if(!uniqueMovie.get("runtime").equals("")) {
			this.runtime = uniqueMovie.getInt("runtime");
		} else { this.runtime = -1; }
		if(uniqueMovie.has("genres")) {
			this.genreArray = uniqueMovie.getJSONArray("genres");
			for(int i = 0; i<genreArray.length();i++) {
				this.genre += (String) genreArray.get(i);
			}
		}
		
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCritics_score() {
		return critics_score+"%";
	}

	public void setCritics_score(String critics_score) {
		this.critics_score = critics_score;
	}

	public String getAudience_score() {
		return audience_score+"%";
	}

	public void setAudience_score(String audience_score) {
		this.audience_score = audience_score;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDetailedImage() {
		return detailedImageSrc;
	}

	public void setDetailedImage(String detailedImageSrc) {
		this.detailedImageSrc = detailedImageSrc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMpaa_rating() {
		return mpaa_rating;
	}

	public void setMpaa_rating(String mpaa_rating) {
		this.mpaa_rating = mpaa_rating;
	}

	public String getCritics_rating() {
		return critics_rating;
	}

	public void setCritics_rating(String critics_rating) {
		this.critics_rating = critics_rating;
	}

	public String getAudience_rating() {
		return audience_rating;
	}

	public void setAudience_rating(String audience_rating) {
		this.audience_rating = audience_rating;
	}

	public String getThumbnail() {
		return thumbnailSrc;
	}

	public void setThumbnail(String thumbnailSrc) {
		this.thumbnailSrc = thumbnailSrc;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRuntime() {
		if(runtime != -1) {
			return runtime/60+" hr."+runtime%60+" min.";
		} else { return "NA"; }
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", mpaa_rating="
				+ mpaa_rating + ", critics_rating=" + critics_rating
				+ ", audience_rating=" + audience_rating + ", thumbnailSrc=" + thumbnailSrc
				+ ", year=" + year + ", runtime=" + runtime + "]";
	}

}
