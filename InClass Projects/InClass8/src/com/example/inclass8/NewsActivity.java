/*
*a. Assignment # : In Class 8
*
*b. File Name : NewsActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass8;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NewsActivity extends Activity {
	
	private String url = "http://rss.cnn.com/rss/cnn_tech.rss";
	static String SEND_LINK = "";
	DataManager dm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		
		new GetInfo().execute(url);
		//Log.d("DEMO1", url);
		
	}
	
	public class GetInfo extends AsyncTask<String, Void, ArrayList<News>> {

		@Override
		protected void onPostExecute(final ArrayList<News> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			ListView lv = (ListView) findViewById(R.id.listView1);
			ArrayList<String> titles = new ArrayList<String>();
			//Log.d("demo", Integer.toString(result.size()));
			for (int i = 0; i < result.size(); i++) {
				titles.add(result.get(i).getTitle());
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewsActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					TextView tv = (TextView) arg1;
					for (News n : result) {
						if (n.getTitle().equals(tv.getText())) {
							Intent i = new Intent(getBaseContext(),PreviewActivity.class);
							i.putExtra(SEND_LINK, result.get(arg2).getUrl());
							startActivity(i);
						}
					}
					
				}
			});
		}

		@Override
		protected ArrayList<News> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String urlString = arg0[0];
			try {
				URL url = new URL(urlString);			
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.connect();			
				int statusCode = con.getResponseCode();
				//Log.d("DEMO2", Integer.toString(statusCode));
				if (statusCode == HttpURLConnection.HTTP_OK) {
					InputStream in = con.getInputStream();
					ArrayList<News> newSets = NewsXMLPullParser.parseNews(in);
					return newSets;
					
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return null;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//return super.onOptionsItemSelected(item);
		
		switch (item.getItemId()) {
	        case R.id.item1:
	            dm = new DataManager(this);
	            List<News> newsList= dm.getAllNotes(); 
	            for(News n:newsList) {
	            	dm.deleteNote(n);
	            }
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}

	}
	
	static public class NewsXMLPullParser {
		static ArrayList<News> parseNews(InputStream xmlIn) throws XmlPullParserException, NumberFormatException, IOException{						
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			//factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			
			
			parser.setInput(xmlIn, "UTF-8");
			ArrayList<News> entries = null;
			News entry = null;
			Boolean appleEntry = true;
			Boolean appleLink = true;
			
			int event = parser.getEventType();			
			while(event != XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					entries = new ArrayList<News>();
					break;
				case XmlPullParser.START_TAG:
						
					if(parser.getName().equals("item")) {
						entry = new News();
						appleEntry = false;
						appleLink = false;
					} else if (parser.getName().equals("title") && !appleEntry) {
						entry.setTitle(parser.nextText().trim());
					} else if (parser.getName().equals("link") && !appleLink) {
						entry.setUrl(parser.nextText().trim());
					}
					break;
				case XmlPullParser.END_TAG:
					
					if(parser.getName().equals("item")) {
						Log.d("DEMOENTRY", entry.toString());
						entries.add(entry);
					}
					
				default:
					break;
				}
				event = parser.next();
			}

			return entries;
		}
	}

}