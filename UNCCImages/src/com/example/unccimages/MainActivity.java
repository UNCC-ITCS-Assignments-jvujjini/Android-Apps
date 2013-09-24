/*
*a. Assignment # : HW # 3
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/



package com.example.unccimages;

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
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	private String url = "http://api.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=3ac013425b6067f444aa828a787c1108&user_id=40729938%40N03&format=json&nojsoncallback=1";
	static String SEND_TEXT = "movie";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new GetInfo().execute(url);
		//ListView myList = (ListView) findViewById(R.id.listView1);
	}
	
	public class GetInfo extends AsyncTask<String, Void, ArrayList<Picture>> {
		
		
		ProgressDialog progressDialog;
		   
		   @Override
		   protected void onPreExecute()
		   {
		       progressDialog = ProgressDialog.show(MainActivity.this, "",
		               "Loading Photo Sets...");
		       progressDialog.setCancelable(false);
		   }
		
		@Override
		protected ArrayList<Picture> doInBackground(String... params) {
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
					ArrayList<Picture> picSets = PicJSONParser.parseLists(sb.toString());
					return picSets;
					
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
		
		protected void onPostExecute(final ArrayList<Picture> lists) {
			
			progressDialog.dismiss();
			
			ListView myList = (ListView) findViewById(R.id.listView1);
			
			ArrayList<String> result = new ArrayList<String>();
			
			for ( int i = 0; i<lists.size();i++) {
				result.add(lists.get(i).getTitle()+" ("+lists.get(i).getPicCount()+")");
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
					
					//Log.d("Demo", tv.getText().toString());
					
					for(Picture p:lists) {
						
						String temp = p.getTitle()+" ("+p.getPicCount()+")";
						
						//Log.d("Demo", temp);
						
						if(temp.equals(tv.getText().toString())) {
							
							Intent i = new Intent(getBaseContext(), GalleryActivity.class);
							i.putExtra(SEND_TEXT, p.getId());
							startActivity(i);
							break;
							
						}
						
					}

				}
			
			});

		}

	}
	
	public static class PicJSONParser{		
		static ArrayList<Picture> parseLists(String jsonString) throws JSONException{
			ArrayList<Picture> picList = new ArrayList<Picture>();
			//ArrayList<String> movieUni = new ArrayList<String>();
			JSONObject picJsonObj = new JSONObject(jsonString);
			JSONArray setArray = picJsonObj.getJSONObject("photosets").getJSONArray("photoset");
			for(int i=0; i<setArray.length(); i++) {
				JSONObject unqSet = setArray.getJSONObject(i);
				Picture  set = new Picture(unqSet);
				picList.add(set);
			}
			return picList;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}