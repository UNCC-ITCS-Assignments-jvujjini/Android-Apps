/*
*a. Assignment # : In Class 8
*
*b. File Name : FavouritesActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/


package com.example.inclass8;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FavouritesActivity extends Activity {
	
	DataManager dm;
	static String SEND_LINK = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourites);
		
		ListView lv = (ListView) findViewById(R.id.listView1);
		
		ArrayList<String> titles = new ArrayList<String>();
		
		final List<News> newsFavs = dm.getAllNotes();
		
		//Log.d("demo", Integer.toString(result.size()));
		for (int i = 0; i < newsFavs.size(); i++) {
			titles.add(newsFavs.get(i).getTitle());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				TextView tv = (TextView) arg1;
				for (News n : newsFavs) {
					if (n.getTitle().equals(tv.getText())) {
						Intent i = new Intent(getBaseContext(),PreviewActivity.class);
						i.putExtra(SEND_LINK, newsFavs.get(arg2).getUrl());
						startActivity(i);
					}
				}
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favourites, menu);
		return true;
		
		
	}

}
