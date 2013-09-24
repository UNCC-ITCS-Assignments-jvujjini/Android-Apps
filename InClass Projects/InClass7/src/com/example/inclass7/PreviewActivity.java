package com.example.inclass7;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class PreviewActivity extends Activity {
	
	Entry entry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		
		entry = (Entry) getIntent().getExtras().get("Position");
		
		TextView tv = (TextView) findViewById(R.id.textTitle);
		tv.setText(entry.getName());
		
		ImageView iv = (ImageView) findViewById(R.id.appImage);
		
		new GetBitmap(iv).execute(entry.getImage());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview, menu);
		return true;
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
