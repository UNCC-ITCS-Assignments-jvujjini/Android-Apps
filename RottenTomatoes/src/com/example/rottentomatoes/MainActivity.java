/*
*a. Assignment # : MidTerm
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/


package com.example.rottentomatoes;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ListView listview;
	private ArrayList<String> list;
	String SEND_KEY = "choice";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listview = (ListView) findViewById(R.id.movieCategories);
	    String[] values = new String[] { "My Favorite Movies", "Box Office Movies", "In Theater Movies", 
	    		"Opening Movies", "Upcoming Movies" };

	    list = new ArrayList<String>();
	    
	    for (int i = 0; i < values.length; ++i) {
	      list.add(values[i]);
	    }
	    
	    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, list);
	    listview.setAdapter(adapter);
	    
	    listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				TextView tv = (TextView) arg1;
				
				Intent i = new Intent(getBaseContext(),MoviesActivity.class);
				i.putExtra(SEND_KEY, tv.getText());
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
	
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.item1:
			new DeleteAllFavorites().execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteAllFavorites.php");
			break;
		case R.id.item2:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
