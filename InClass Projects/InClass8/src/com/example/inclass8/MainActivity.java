/*
*a. Assignment # : In Class 8
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass8;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	DataManager dm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			//private String SEND_TEXT;

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(getBaseContext(), NewsActivity.class);
				//i.putExtra(SEND_TEXT, "");
				startActivity(i);
				
			}
		});
		
		button = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new OnClickListener() {
			
			//private String SEND_TEXT;

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(getBaseContext(), FavouritesActivity.class);
				//i.putExtra(SEND_TEXT, "");
				startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
