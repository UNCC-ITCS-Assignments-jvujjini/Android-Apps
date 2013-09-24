/*
*a. Assignment # : MidTerm
*
*b. File Name : GetBitmap.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.rottentomatoes;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

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
