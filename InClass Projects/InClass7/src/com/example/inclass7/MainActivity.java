/*
*a. Assignment # : In Class 7
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass7;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Entry result = null;
	private String url = "http://itunes.apple.com/us/rss/topgrossingapplications/limit=10/xml";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new GetInfo().execute(url);
					

		
	}
	
	public class GetInfo extends AsyncTask<String, Void, ArrayList<Entry>> {
		
		ProgressDialog prog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			prog = ProgressDialog.show(MainActivity.this, "", "Loading Apps...");
			prog.setCancelable(false);
			
		}

		@Override
		protected ArrayList<Entry> doInBackground(String... params) {

			String urlString = params[0];
			try {
				URL url = new URL(urlString);			
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.connect();			
				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					InputStream in = con.getInputStream();	
					try {
						ArrayList<Entry> result = PersonXMLPullParser.parsePerson(in);
						return result;
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
						
					
				}
			} catch (MalformedURLException e) {

				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return null; 

		}
		
		protected void onPostExecute(final ArrayList<Entry> list) {
			
			prog.dismiss();
			
			ListView myList = (ListView) findViewById(R.id.listView1);
			myList.setAdapter(new CustomAdapter(list));
			myList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					Intent i = new Intent(MainActivity.this,PreviewActivity.class);
					i.putExtra("Position", list.get(arg2));
					startActivity(i);
					
				}
			});
			
		}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
	
	static public class PersonXMLPullParser {
		static ArrayList<Entry> parsePerson(InputStream xmlIn) throws XmlPullParserException, NumberFormatException, IOException{						
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			
			Entry entry = null;
			parser.setInput(xmlIn, "UTF-8");
			ArrayList<Entry> entries = new ArrayList<Entry>();
			
			int event = parser.getEventType();			
			while(event != XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("entry")) {
						entry = new Entry();
					} else if (parser.getName().equals("im:name")) {
						entry.setName(parser.nextText().trim());
					} else if (parser.getName().equals("category")) {
						entry.setCategory(parser.getAttributeValue(3).trim());
					} else if (parser.getName().equals("im:price")) {
						entry.setPrice(parser.nextText().trim());
					} else if (parser.getName().equals("im:releaseDate")) {
						entry.setReleaseDate(parser.getAttributeValue(0));
					} else if (parser.getName().equals("im:artist")) {
						entry.setDeveloper((parser.nextText().trim()));
					} else if (parser.getName().equals("im:image")) {
						if (parser.getAttributeValue(0).equals("53")) {
							entry.setThumbnail(parser.nextText().trim());
						} else if (parser.getAttributeValue(0).equals("100")) {
							entry.setImage(parser.nextText().trim());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("entry")) {
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
	
	public class CustomAdapter extends BaseAdapter {
		
		ArrayList<Entry> objects;

		public CustomAdapter(ArrayList<Entry> objects) {
			super();
			
			this.objects = objects;
			
		}

		@Override
		public int getCount() {

			return objects.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			
			if (arg1 == null) {
				LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        arg1 = inflater.inflate(R.layout.custom_list, null);
		    }
			
			ImageView iv = (ImageView) arg1.findViewById(R.id.imageView1);
			
			new GetBitmap(iv).execute(objects.get(arg0).getThumbnail());
			
			TextView tv = (TextView) arg1.findViewById(R.id.textView1);
			tv.setText(objects.get(arg0).getName());
			tv.setTextColor(Color.BLACK);
			
			tv = (TextView) arg1.findViewById(R.id.textView2);
			tv.setText((objects.get(arg0).getDeveloper()));
			tv.setTextColor(Color.BLACK);
			
			tv = (TextView) arg1.findViewById(R.id.textView3);
			tv.setText(objects.get(arg0).getReleaseDate());
			tv.setTextColor(Color.BLACK);
			
			tv = (TextView) arg1.findViewById(R.id.textView4);
			tv.setText(objects.get(arg0).getPrice());
			tv.setTextColor(Color.BLACK);
			
			tv = (TextView) arg1.findViewById(R.id.textView5);
			tv.setText(objects.get(arg0).getCategory());
			tv.setTextColor(Color.BLACK);
			
			return arg1;
		}
		
	}
	
	public class GetBitmap extends AsyncTask<String, Void, Bitmap> {
		
		ImageView iv;

		public GetBitmap(ImageView iv) {
			
			this.iv = iv;
			
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			
			try {
		        URL url = new URL(params[0]);
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoInput(true);
		        connection.connect();
		        InputStream input = connection.getInputStream();
		        Bitmap myBitmap = BitmapFactory.decodeStream(input);
		        return myBitmap;
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    }
			
		}

		protected void onPostExecute(Bitmap result) {

			super.onPostExecute(result);
			
			iv.setImageBitmap(result);
			
			
		}
	}

}
