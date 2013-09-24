/*
*a. Assignment # : In Class 2a
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass2a;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        Button black = (Button) findViewById(R.id.black);
        black.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RelativeLayout lay = (RelativeLayout) findViewById(R.id.layout);
				lay.setBackgroundColor(0xFF000000);
			}
		});
        
        Button white = (Button) findViewById(R.id.White);
        white.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RelativeLayout lay = (RelativeLayout) findViewById(R.id.layout);

				lay.setBackgroundColor(0xffffffff);
			}
		});
        
        Button red = (Button) findViewById(R.id.Red);
        red.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RelativeLayout lay = (RelativeLayout) findViewById(R.id.layout);

				lay.setBackgroundColor(0xffff0000);
				// TODO Auto-generated method stub
			}
		});
        
        Button green = (Button) findViewById(R.id.Green);
        green.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RelativeLayout lay = (RelativeLayout) findViewById(R.id.layout);

				lay.setBackgroundColor(0xff00ff00);
			}
		});
        
        Button blue = (Button) findViewById(R.id.Blue);
        blue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RelativeLayout lay = (RelativeLayout) findViewById(R.id.layout);

				lay.setBackgroundColor(0xff0000ff);
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
