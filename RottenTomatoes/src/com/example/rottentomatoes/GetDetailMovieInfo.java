/*
*a. Assignment # : MidTerm
*
*b. File Name : GetDetailMovieInfo.java
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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class GetDetailMovieInfo extends AsyncTask<String, Void, Movie> {
	
	MovieActivity movieActivity;
	MoviesActivity moviesActivity;
	ProgressDialog progressDialog;

	public GetDetailMovieInfo(MovieActivity movieActivity) {
		// TODO Auto-generated constructor stub
		
		this.movieActivity = movieActivity;
	}

	public GetDetailMovieInfo(MoviesActivity moviesActivity) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
   protected void onPreExecute() {
		
	   progressDialog = new ProgressDialog(movieActivity);
	   progressDialog.setMessage("Getting Movie Information...");
	   progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	   progressDialog.setCancelable(false);
	   progressDialog.show();
   
	}

	@Override
	protected Movie doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		JSONObject movieObj;
		
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
				movieObj = new JSONObject(sb.toString());
				Movie movie = new Movie(movieObj);
				return movie;
				
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

	@Override
	protected void onPostExecute(Movie result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		progressDialog.dismiss();
		
		movieActivity.setDetailView(result);
		
	}

}
