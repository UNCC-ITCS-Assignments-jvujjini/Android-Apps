/*
*a. Assignment # : MidTerm
*
*b. File Name : MovieActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.rottentomatoes;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MovieActivity extends Activity {
	
	protected static final int UPDATE_FAVOURITE = 0;
	private String detailUrl = "http://api.rottentomatoes.com/api/public/v1.0/movies/";
	private String id;
	ImageView iv,iv1,iv2,iv3,iv4,iv5,iv6,iv7;
	String movieId;
	private boolean fun;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		
		id = getIntent().getExtras().getString("id");
		position = getIntent().getExtras().getInt("position");
		
		detailUrl += id + ".json?apikey=3qraz344365v5qzg3r6tv76w";
		
		new GetDetailMovieInfo(this).execute(detailUrl);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie, menu);
		return true;
	}
	
	public void setDetailView(final Movie movie) {
		
		movieId = movie.getId();
		
		String isFavoriteUrl = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/isFavorite.php";
		
		iv = (ImageView) findViewById(R.id.imageFavourite);
		new IsFavorite(this).execute(isFavoriteUrl);
		
		iv5 = (ImageView) findViewById(R.id.imageDetail);
		
		new GetBitmap(iv5).execute(movie.getDetailedImage());
		
		iv3 = (ImageView) findViewById(R.id.imageAudience);
		if(movie.getAudience_rating().equals("Certified Fresh")) {
			iv3.setImageResource(R.drawable.certified_fresh);
		} else if(movie.getAudience_rating().equals("Fresh")) {
			iv3.setImageResource(R.drawable.fresh);
		} else if(movie.getAudience_rating().equals("Rotten")) {
			iv3.setImageResource(R.drawable.rotten);
		} else if(movie.getAudience_rating().equals("Spilled")) {
			iv3.setImageResource(R.drawable.spilled);
		} else if(movie.getAudience_rating().equals("Upright")) {
			iv3.setImageResource(R.drawable.upright);
		} else {
			iv3.setImageResource(R.drawable.notranked);
		}
		
		iv2 = (ImageView) findViewById(R.id.imageCritics);
		if(movie.getCritics_rating().equals("Certified Fresh")) {
			iv2.setImageResource(R.drawable.certified_fresh);
		} else if(movie.getCritics_rating().equals("Fresh")) {
			iv2.setImageResource(R.drawable.fresh);
		} else if(movie.getCritics_rating().equals("Rotten")) {
			iv2.setImageResource(R.drawable.rotten);
		} else if(movie.getCritics_rating().equals("Spilled")) {
			iv2.setImageResource(R.drawable.spilled);
		} else if(movie.getCritics_rating().equals("Upright")) {
			iv2.setImageResource(R.drawable.upright);
		} else {
			iv2.setImageResource(R.drawable.notranked);
		}
		
		TextView tv = (TextView) findViewById(R.id.titleDetail);
		tv.setText(movie.getTitle());
		
		tv = (TextView) findViewById(R.id.textDate);
		tv.setText(movie.getReleaseDate());
		
		tv = (TextView) findViewById(R.id.textRating);
		tv.setText(movie.getMpaa_rating());
		
		tv = (TextView) findViewById(R.id.textRuntime);
		tv.setText(movie.getRuntime());
		
		tv = (TextView) findViewById(R.id.textAPercent);
		tv.setText(movie.getAudience_score());
		
		tv = (TextView) findViewById(R.id.textCPercent);
		tv.setText(movie.getCritics_score());
		
		tv = (TextView) findViewById(R.id.textGenre);
		tv.setText(movie.getGenre());
		
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
                if(!fun) {
                	fun = true;
	                iv.setImageResource(R.drawable.favourite_yes);
	                String url = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/addToFavorites.php";
	                new AddToFavorites(MovieActivity.this).execute(url);
	                
                } else {
                	fun = false;
                    iv.setImageResource(R.drawable.favourite_no);
                    String url = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteFavorite.php";
                    new DeleteFavorite(MovieActivity.this).execute(url);
                }
            }
		});
		
		iv6 = (ImageView) findViewById(R.id.imageBrowser);
		iv6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(movie.getUrl())));
				
            }
		});
		
		iv7 = (ImageView) findViewById(R.id.imageBack);
		iv7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!fun) {
					Intent i = new Intent(MovieActivity.this, MoviesActivity.class);
					i.putExtra("delete", position);
					setResult(RESULT_OK, i);
				}
				finish(); 

            }
		});
		
	}

	public void setToast(String message) {
		
		if (message.equalsIgnoreCase("true")) {
			Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
		}

	}
	
	public void isFavoriteStar (String result) {
		
		fun = Boolean.parseBoolean(result);
	}
	
	public void updateList (String id) {
		
		
	}


}