/*
*a. Assignment # : MidTerm
*
*b. File Name : MovieJSONParser.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/


package com.example.rottentomatoes;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieJSONParser {
	static ArrayList<Movie> parseMovie(String jsonString) throws JSONException{
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		JSONObject movieObj = new JSONObject(jsonString);
		JSONArray movieArray = movieObj.getJSONArray("movies");
		for(int i=0; i<movieArray.length(); i++) {
			JSONObject uniqueMovie = movieArray.getJSONObject(i);
			Movie  movie = new Movie(uniqueMovie);
			movieList.add(movie);

		}
		return movieList;
	}
}