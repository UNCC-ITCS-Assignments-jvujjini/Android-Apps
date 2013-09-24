/*
*a. Assignment # : HW # 3
*
*b. File Name : ImageViewerActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.unccimages;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class ImageViewerActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_viewer);
		
		if(getIntent().getExtras() != null){
			if (getIntent().getExtras().containsKey(GalleryActivity.SEND_TEXT)) {
				String src = getIntent().getExtras().getString(GalleryActivity.SEND_TEXT);
				
				new GetInfo().execute(src);
				
				

			}	
		}
	}

	public class GetInfo extends AsyncTask<String, Void, Bitmap> {
		
		ProgressDialog progressDialog;
		
		@Override
	    protected void onPreExecute()
	    {
	       progressDialog = ProgressDialog.show(ImageViewerActivity.this, "",
	           "Loading Picture...");
	       progressDialog.setCancelable(false);
	    }

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			
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
		    }
			
			return null;
		}
		
		
		protected void onPostExecute(Bitmap bit) {
			
			progressDialog.dismiss();
			
			ImageView iv = (ImageView) findViewById(R.id.imageView1);
	        iv.setImageBitmap(bit);

		}
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_viewer, menu);
		return true;
	}

}
