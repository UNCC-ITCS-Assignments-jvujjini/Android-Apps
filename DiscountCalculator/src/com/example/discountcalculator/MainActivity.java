/*
*a. Assignment # : HomeWork 2
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*
****The Text for the RadioButtons have size less than 12sp to accommodate them in one line.
*/

package com.example.discountcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
	
	private Float f1 = (float) 0;
	private Float f2 = (float) 0;
	private EditText p;
	private SeekBar s;
	private TextView tv;
	private TextView tv1;
	private TextView tv2;
	private RadioGroup g;
	private RadioButton r;
	private RadioButton r1;
	private int x1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        p = (EditText) findViewById(R.id.editText1);
        s = (SeekBar) findViewById(R.id.seekBar1);
        tv = (TextView) findViewById(R.id.textView7);
        tv1 = (TextView) findViewById(R.id.textView8);
        tv2 = (TextView) findViewById(R.id.textView9);
        
        s.setProgress(25);
        s.setEnabled(false);
        
        g = (RadioGroup) findViewById(R.id.radioGroup1);
        
		r = (RadioButton) findViewById(R.id.radio0);
		r.setTag(R.id.tag_discount, 10);
        r.setOnClickListener(this);
        
        r = (RadioButton) findViewById(R.id.radio1);
        r.setTag(R.id.tag_discount, 25);
        r.setOnClickListener(this);
        
        r = (RadioButton) findViewById(R.id.radio2);
        r.setTag(R.id.tag_discount, 50);
        r.setOnClickListener(this);
        
        r = (RadioButton) findViewById(R.id.radio3);
        r.setTag(R.id.tag_discount, 100);
        r.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				s.setEnabled(true);
				x1 = s.getProgress();
				setValues(x1,p.getText().toString());
				
				s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						// TODO Auto-generated method stub
						
						int x = s.getProgress();
						tv2.setText(x+"%");
						
						s.setEnabled(true);
						
						setValues(x,p.getText().toString());
						
					}
				});
				
			}
		});
        
        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
		});
        
        p.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable edit) {
				// TODO Auto-generated method stub
				
				r1 = (RadioButton) findViewById(g.getCheckedRadioButtonId());
				x1 = (Integer) r1.getTag(R.id.tag_discount);
				String text = edit.toString();
				
				if(x1 == 100) {
					
					s.setEnabled(true);
					x1 = s.getProgress();
					setValues(x1,text);
					
				} else {
					
					setValues(x1,text);
					
				}
				
			}
		});
           
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int x = (Integer) v.getTag(R.id.tag_discount);
		
		s.setEnabled(false);
		
		setValues(x,p.getText().toString());
	
	}
	
	public void setValues(int cent, String text) {
		
		if(text.length() != 0) {
			
			f1 = Float.parseFloat(text);
			
			f2 = (cent*f1)/100;
			tv.setText("$ " +f2+"");
			
			f2 = ((100-cent)*f1)/100;
			tv1.setText("$ " +f2+"");
			
		} else {
			
			p.setError("Enter List Price");
			tv.setText("$ 0.00");
			tv1.setText("$ 0.00");
			
		}
		
	}
    
}