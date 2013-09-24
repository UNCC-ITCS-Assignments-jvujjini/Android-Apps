/*
*a. Assignment # : In Class 5a
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/


package com.example.inclass5a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String url = "http://cci-webdev.uncc.edu/~mshehab/api-rest/states/index.php";
	private AutoCompleteTextView text;
	private TextView tv, tv1;
	private Context context;
	private CharSequence text1;
	private int duration;
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (AutoCompleteTextView) findViewById(R.id.autocomplete);
		
		String[] states = getResources().getStringArray(R.array.states_array);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, states);
		text.setAdapter(adapter);
		text.setThreshold(0);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String text1 = text.getText().toString();
				url = url+"?method=getStatePopulation&state="+text1;
				
				new GetInfo().execute(url);
				
			}
		});
		
		
	}
	
	public class GetInfo extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			String urlString = params[0];
			try {
				URL url = new URL(urlString);			
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.connect();			
				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					BufferedReader in =new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuffer sb = new StringBuffer("");
					String line ="";
					while((line = in.readLine()) !=null){
						sb.append(line);
					}
					in.close();
					String result = sb.toString();
					return result;
				}
			} catch (MalformedURLException e) {

				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return null; 

		}
		
		protected void onPostExecute(String result) {
			
			String[] splits = result.split(";");
			
			tv = (TextView) findViewById(R.id.textView1);
			tv1 = (TextView) findViewById(R.id.textView2);
			if(splits.length == 2) {
				
				tv.setText(splits[0]);
				tv1.setText(splits[1]);
				
			} else {
				
				tv.setText("");
				tv1.setText("");
				
				context = getApplicationContext();
				text1 = splits[0];
				duration = Toast.LENGTH_SHORT;

				toast = Toast.makeText(context, text1, duration);
				toast.show();
				
			}
		}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
	
}
