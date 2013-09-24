/*
*a. Assignment # : MidTerm
*
*b. File Name : GetMoviesInfo.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/


package com.example.rottentomatoes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class GetMoviesInfo extends AsyncTask<String, Void, ArrayList<Movie>> {
	
	MoviesActivity moviesActivity;
	ProgressDialog progressDialog;

	public GetMoviesInfo(MoviesActivity moviesActivity) {
		
		this.moviesActivity = moviesActivity;

	}
	
   @Override
   protected void onPreExecute() {
	   
	   progressDialog = new ProgressDialog(moviesActivity);
	   progressDialog.setMessage("Loading Movies...");
	   progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	   progressDialog.setCancelable(false);
	   progressDialog.show();
   
   }


	@Override
	protected void onPostExecute(ArrayList<Movie> result) {

		progressDialog.dismiss();

		moviesActivity.setList(result);
		moviesActivity.setView();
		

	}

	@Override
	protected ArrayList<Movie> doInBackground(String... arg0) {
		
		String urlString = arg0[0];
		try {
			URL url = new URL(urlString);			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();			
			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {				
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = reader.readLine();
				while (line != null) {
					sb.append(line);
					line = reader.readLine();
				}
				ArrayList<Movie> movies = MovieJSONParser.parseMovie(sb.toString());
				
				return movies;
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}