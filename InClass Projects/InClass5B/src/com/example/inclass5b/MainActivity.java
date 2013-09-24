/*
*a. Assignment # : In Class 5b
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass5b;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
	
	private String result = null, url = "http://cci-webdev.uncc.edu/~mshehab/api-rest/states/index.php";
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
		
		tv = (TextView) findViewById(R.id.textView1);
		tv1 = (TextView) findViewById(R.id.textView2);
		
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String text1 = text.getText().toString();
				
				if(text1.equalsIgnoreCase("")) {
					
					tv.setText("");
					tv1.setText("");
					
					context = getApplicationContext();
					text1 = "No State Provided!!";
					duration = Toast.LENGTH_SHORT;

					toast = Toast.makeText(context, text1, duration);
					toast.show();
					
				} else {
					
					url = url+"?method=getStatePopulation&state="+text1+"&format=XML";
					new GetInfo().execute(url);
					
				}

				
				
				
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
					InputStream in = con.getInputStream();	
					try {
						result = PersonXMLPullParser.parsePerson(in);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (XmlPullParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
	
	static public class PersonXMLPullParser {
		static String parsePerson(InputStream xmlIn) throws XmlPullParserException, NumberFormatException, IOException{						
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			String resultTemp = null;
			parser.setInput(xmlIn, "UTF-8");
			
			int event = parser.getEventType();			
			while(event != XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("state")){
						resultTemp = parser.getAttributeValue(null, "name").trim();
					} else if(parser.getName().equals("population")){
						resultTemp = resultTemp + ";" + parser.nextText().trim();
					} else if(parser.getName().equals("result")){
						if (parser.getAttributeValue(null, "error").trim() != null) {
							resultTemp = parser.getAttributeValue(null, "error").trim();
						}
					}
					break;
				default:
					break;
				}
				event = parser.next();
			}
			return resultTemp;
		}
	}
}
