/*
*a. Assignment # : MidTerm
*
*b. File Name : GetFavoritesInfo.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/


package com.example.rottentomatoes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class GetFavoritesInfo extends AsyncTask<String, Void, ArrayList<Movie>> {
	
	MoviesActivity moviesActivity;
	ArrayList<Movie> movies = new ArrayList<Movie>();
	ProgressDialog progressDialog;

	public GetFavoritesInfo(MoviesActivity moviesActivity) {
		// TODO Auto-generated constructor stub
		
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
	protected ArrayList<Movie> doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost request = new HttpPost(params[0]);
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("uid",Config.getUid()));
	    try {
	    	UrlEncodedFormEntity formParams = new UrlEncodedFormEntity(nameValuePairs);
	    	request.setEntity(formParams);
	    	HttpResponse response = httpclient.execute(request);
	    	if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	    		
				InputStream in = response.getEntity().getContent();
				ArrayList<String> ids = MovieXMLPullParser.parseIds(in);
				
				for(String id:ids) {
					String urlString = "http://api.rottentomatoes.com/api/public/v1.0/movies/"+id+".json?apikey=3qraz344365v5qzg3r6tv76w";
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
						JSONObject movieObj = new JSONObject(sb.toString());
						Movie movie = new Movie(movieObj);
						movies.add(movie);	
					}
				}
				return movies;
	    	}
	    	
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    } catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<Movie> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		progressDialog.dismiss();
		
		moviesActivity.setList(result);
		moviesActivity.setView();
		
	}

}