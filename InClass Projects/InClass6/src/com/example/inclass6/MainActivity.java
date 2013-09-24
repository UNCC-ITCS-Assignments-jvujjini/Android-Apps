/*
*a. Assignment # : In Class 6
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/



package com.example.inclass6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?page_limit=25&page=1&country=us&apikey=3qraz344365v5qzg3r6tv76w";
	static String SEND_TEXT = "movie";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new GetInfo().execute(url);
		//ListView myList = (ListView) findViewById(R.id.listView1);
	}
	
	public class GetInfo extends AsyncTask<String, Void, ArrayList<Movie>> {
		@Override
		
		protected ArrayList<Movie> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlString = params[0];
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
					ArrayList<Movie> movie = PersonJSONParser.parsePersons(sb.toString());
					return movie;
					//Log.d("Demo",sb.toString());
					
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return null;
		}
		
		protected void onPostExecute(final ArrayList<Movie> list) {
			
			ListView myList = (ListView) findViewById(R.id.listView1);
			
			ArrayList<String> result = new ArrayList<String>();
			
			for ( int i = 0; i<list.size();i++) {
				result.add(list.get(i).getTitle());
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, result);
			myList.setAdapter(adapter);
			adapter.setNotifyOnChange(true);
			
			myList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					TextView tv = (TextView) arg1;
					
					for(Movie m:list) {
						
						if(m.getTitle().equals(tv.getText().toString())) {
							
							Intent i = new Intent(getBaseContext(), DetailActivity.class);
							i.putExtra(SEND_TEXT, m.toString());
							startActivity(i);
							
						}
						
					}

				}
			
			});

		}

	}
	
	public static class PersonJSONParser{		
		static ArrayList<Movie> parsePersons(String jsonString) throws JSONException{
			ArrayList<Movie> movieList = new ArrayList<Movie>();
			//ArrayList<String> movieUni = new ArrayList<String>();
			JSONObject movieObj = new JSONObject(jsonString);
			JSONArray movieArray = movieObj.getJSONArray("movies");
			for(int i=0; i<movieArray.length(); i++) {
				JSONObject movieUnq = movieArray.getJSONObject(i);
				Movie  movie = new Movie(movieUnq);
				movieList.add(movie);
				Log.d("Demo", movie.toString());
				
				
			}
			return movieList;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
