/*
*a. Assignment # : In Class 3
*
*b. File Name : ActivityA.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/


package com.example.inclass3;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityA extends Activity {
	
	String number = null;
	final static String SEND_TEXT = "number";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		
		
		Button b = (Button) findViewById(R.id.next);
		
		if(getIntent().getExtras() != null){
			if (getIntent().getExtras().containsKey(MainActivity.SEND_TEXT)) {
				number = getIntent().getExtras().getString(MainActivity.SEND_TEXT);
				TextView tv = (TextView) findViewById(R.id.tv);
				tv.setText("Received " +number);
			}	
		}
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int temp = Integer.parseInt(number);
				temp = temp *2;
				Intent i = new Intent(getBaseContext(),ActivityB.class);
				i.putExtra(SEND_TEXT, temp+"");
				startActivity(i);
				
			}
		});
		
		b = (Button) findViewById(R.id.finish);
        b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				finish();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity, menu);
		return true;
	}

}
