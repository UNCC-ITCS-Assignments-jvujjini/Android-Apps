/*
*a. Assignment # : In Class 2b
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/


package com.example.inclass2b;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;

public class MainActivity extends Activity{

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final SeekBar seek = (SeekBar) findViewById(R.id.seekBar1);
        float def = (float) 0.9;
        LinearLayout lay = (LinearLayout) findViewById(R.id.layout);
        lay.setAlpha(def);
        seek.setProgress(90);
        
        RadioButton black = (RadioButton) findViewById(R.id.radio0);
        black.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout lay = (LinearLayout) findViewById(R.id.layout);
				lay.setBackgroundColor(0xFF000000);
			}
		});
        
        RadioButton white = (RadioButton) findViewById(R.id.radio1);
        white.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout lay = (LinearLayout) findViewById(R.id.layout);
				lay.setBackgroundColor(0xffffffff);
			}
		});
        
        RadioButton red = (RadioButton) findViewById(R.id.radio2);
        red.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout lay = (LinearLayout) findViewById(R.id.layout);
				lay.setBackgroundColor(0xffff0000);
			}
		});
        
        RadioButton green = (RadioButton) findViewById(R.id.radio3);
        green.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout lay = (LinearLayout) findViewById(R.id.layout);
				lay.setBackgroundColor(0xff00ff00);
			}
		});
        
        RadioButton blue = (RadioButton) findViewById(R.id.radio4);
        blue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LinearLayout lay = (LinearLayout) findViewById(R.id.layout);
				lay.setBackgroundColor(0xff0000ff);
			}
		});
        
        
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
            // Here call your code when progress will changes
            	LinearLayout lay = (LinearLayout) findViewById(R.id.layout);
            	lay.setAlpha((float)progress/100);
            }

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
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
