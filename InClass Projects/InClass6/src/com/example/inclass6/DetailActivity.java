/*
*a. Assignment # : In Class 6
*
*b. File Name : DetailActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass6;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DetailActivity extends Activity {
	
	private String movie = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		if(getIntent().getExtras() != null){
			if (getIntent().getExtras().containsKey(MainActivity.SEND_TEXT)) {
				movie = getIntent().getExtras().getString(MainActivity.SEND_TEXT);
				
				String[] splits = movie.split(";");
				
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText(splits[0]);
				
				tv = (TextView) findViewById(R.id.textView2);
				tv.setText(splits[1] + " mins");
				
				tv = (TextView) findViewById(R.id.textView3);
				tv.setText(splits[2]);
			}	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

}
