/*
*a. Assignment # : MidTerm
*
*b. File Name : MoviesActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.rottentomatoes;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class MoviesActivity extends Activity {
	
	private static final int UPDATE_FAVOURITE = 0;
	private String choice = null;
	private String url = null;
	ArrayList<Movie> movieList;
	private CustomAdapter adapter;
	private ListView lv;
	String ID = "id";
	String POSITION = "position";
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies);
		
		choice = getIntent().getExtras().getString("choice");
		
		lv = (ListView) findViewById(R.id.movieList);
		
		if(choice.equals("Box Office Movies")) {
			
			url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=3qraz344365v5qzg3r6tv76w&limit=50";
			new GetMoviesInfo(this).execute(url);
			
		} else if(choice.equals("In Theater Movies")) {
			
			url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=3qraz344365v5qzg3r6tv76w&page_limit=50";
			new GetMoviesInfo(this).execute(url);
			
		} else if(choice.equals("Opening Movies")) {
			
			url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?apikey=3qraz344365v5qzg3r6tv76w&limit=50";
			new GetMoviesInfo(this).execute(url);
			
		} else if(choice.equals("Upcoming Movies")) {
			
			url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey=3qraz344365v5qzg3r6tv76w&page_limit=50";
			new GetMoviesInfo(this).execute(url);
			
		} else if(choice.equals("My Favorite Movies")) {
			
			url = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/getAllFavorites.php";
			new GetFavoritesInfo(this).execute(url);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movies, menu);
		return true;
	}
	
	public void setList(ArrayList<Movie> list) {
		
		movieList = list;
		
	}
	
	public void setView() {

		adapter = new CustomAdapter(this, movieList);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(MoviesActivity.this, MovieActivity.class);
				i.putExtra(ID, movieList.get(arg2).getId());
				startActivityForResult(i, UPDATE_FAVOURITE);
				
			}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				position = arg2;
				
				if(choice.equals("My Favorite Movies")) {
					
					url = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteFavorite.php";
					new DeleteFavorite(MoviesActivity.this).execute(url);
		
				}	
				return false;
			}
		});
	}
	
	public void updateView() {
		
		movieList.remove(position);
		adapter.notifyDataSetChanged();
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == UPDATE_FAVOURITE) {
			
			if (resultCode == RESULT_OK && data.getExtras().containsKey("delete") && choice.equals("My Favorite Movies")) {
	            position = data.getIntExtra("delete", position);
	            //Log.d("return", data.getIntExtra("position"));
	            updateView();
	        }
			
		}
	}

}