package com.example.inclass4a;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
				new AsyncExample().execute();
					
			}
		});
        
        
    }


    
   public class AsyncExample extends AsyncTask<Void, Void, Double> {
	   
	   ProgressDialog progressDialog;
	   
	   @Override
	   protected void onPreExecute()
	   {
	       progressDialog = ProgressDialog.show(MainActivity.this, "",
	               "Loading...");
	   }
		   
	
		@Override
		protected Double doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//return null;
			
			Double randomNum = HeavyWork.getNumber();
			return randomNum;
			
		}
		
		protected void onPostExecute(Double result) {
			
			progressDialog.dismiss();
			 
			String result2 = String.valueOf(result);
			
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText(result2);
		
		}
	   
   }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
