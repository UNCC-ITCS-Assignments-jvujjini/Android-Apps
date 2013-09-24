/*
*a. Assignment # : HW # 3
*
*b. File Name : GalleryActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.unccimages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryActivity extends Activity {
	
	private String setId = "";
	private String url = "http://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=3ac013425b6067f444aa828a787c1108&photoset_id=";
	private ArrayList<Bitmap> bits;
	ArrayList<Photo> photos;
	static String SEND_TEXT = "url";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		
		if(getIntent().getExtras() != null){
			if (getIntent().getExtras().containsKey(MainActivity.SEND_TEXT)) {
				setId = getIntent().getExtras().getString(MainActivity.SEND_TEXT);
				
				url += setId+"&extras=url_sq%2Curl_m&format=json&nojsoncallback=1";
				
				new GetInfo().execute(url);

			}	
		}
	}
	
	public class GetInfo extends AsyncTask<String, Void, ArrayList<Bitmap>> {
		
		ProgressDialog progressDialog;
		   
	   @Override
	   protected void onPreExecute()
	   {
	       progressDialog = ProgressDialog.show(GalleryActivity.this, "",
	               "Loading Photos...");
	       progressDialog.setCancelable(false);
	   }
		
		   
		@Override
		protected ArrayList<Bitmap> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlString = params[0];
			bits = new ArrayList<Bitmap>();
			photos = new ArrayList<Photo>();
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
					photos = PhotoJSONParser.parsePhotos(sb.toString());
					for(int i =0;i<photos.size();i++) {
						
						String src = photos.get(i).getUrlSq();
						Bitmap bit = getBitmapFromURL(src);
						//Log.d("DEMO", bit.toString());
						bits.add(bit);
						
					}
					return bits;
					
					
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
		
		protected void onPostExecute(final ArrayList<Bitmap> list) {
			
			progressDialog.dismiss();
			
			//Log.d("DEMO1", list.toString());
			
			GridView myGrid = (GridView) findViewById(R.id.gridView1);
			myGrid.setAdapter(new ImageAdapter(list));
			myGrid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					Intent i = new Intent(GalleryActivity.this, ImageViewerActivity.class);
					Photo url = photos.get(arg2);
	                i.putExtra(SEND_TEXT, url.getUrlM());
	                startActivity(i);
					
				}
				
			});

		}

	}
	
	public static class PhotoJSONParser{		
		static ArrayList<Photo> parsePhotos(String jsonString) throws JSONException{
			ArrayList<Photo> photoList = new ArrayList<Photo>();
			//ArrayList<String> movieUni = new ArrayList<String>();
			JSONObject picJsonObj = new JSONObject(jsonString);
			JSONArray setArray = picJsonObj.getJSONObject("photoset").getJSONArray("photo");
			for(int i=0; i<setArray.length(); i++) {
				JSONObject unqPhoto = setArray.getJSONObject(i);
				Photo  pic = new Photo(unqPhoto);
				photoList.add(pic);
				//Log.d("Demo", pic.toString());
			}
			return photoList;
		}

	}
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
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
	
	public class ImageAdapter extends BaseAdapter {
		
		private ArrayList<Bitmap> list;

		public ImageAdapter(ArrayList<Bitmap> list) {
			// TODO Auto-generated constructor stub
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			ImageView iv;
			if(convertView != null) {
				iv = (ImageView) convertView;
			} else {
				iv = new ImageView(getBaseContext());
				iv.setLayoutParams(new GridView.LayoutParams(100,100));
				iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
				iv.setPadding(2,2,2,2);
			}
			iv.setImageBitmap(list.get(position));
			return iv;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gallery, menu);
		return true;
	}

}
