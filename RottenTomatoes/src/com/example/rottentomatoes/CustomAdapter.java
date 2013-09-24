/*
*a. Assignment # : MidTerm
*
*b. File Name : CustomAdapter.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.rottentomatoes;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	
	ArrayList<Movie> movieList;
	int position;
	View movieView;
	MoviesActivity c;
	ImageView iv,iv1,iv2;

	public CustomAdapter(MoviesActivity c, ArrayList<Movie> list) {
		
		this.c = c;
		this.movieList = list;
		
	}

	@Override
	public int getCount() {
		
		return movieList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		this.position = arg0;
		this.movieView = arg1;
		
		if (this.movieView == null) {
			LayoutInflater inflater = (LayoutInflater) c.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        this.movieView = inflater.inflate(R.layout.movie, null);
	    }
		
		iv = (ImageView) movieView.findViewById(R.id.imageThumb);
		
		new GetBitmap(iv).execute(movieList.get(position).getThumbnail());
		
		TextView tv = (TextView) movieView.findViewById(R.id.textView1);
		tv.setText(movieList.get(position).getTitle());
		tv.setTextColor(Color.BLUE);
		
		tv = (TextView) movieView.findViewById(R.id.textView2);
		tv.setText(Integer.toString(movieList.get(position).getYear()));
		
		tv = (TextView) movieView.findViewById(R.id.textView3);
		tv.setText(movieList.get(position).getMpaa_rating());
		
		iv1 = (ImageView) movieView.findViewById(R.id.imageC);
		if(movieList.get(position).getAudience_rating().equals("Certified Fresh")) {
			iv1.setImageResource(R.drawable.certified_fresh);
		} else if(movieList.get(position).getAudience_rating().equals("Fresh")) {
			iv1.setImageResource(R.drawable.fresh);
		} else if(movieList.get(position).getAudience_rating().equals("Rotten")) {
			iv1.setImageResource(R.drawable.rotten);
		} else if(movieList.get(position).getAudience_rating().equals("Spilled")) {
			iv1.setImageResource(R.drawable.spilled);
		} else if(movieList.get(position).getAudience_rating().equals("Upright")) {
			iv1.setImageResource(R.drawable.upright);
		} else {
			iv1.setImageResource(R.drawable.notranked);
		}
		
		iv2 = (ImageView) movieView.findViewById(R.id.imageA);
		if(movieList.get(position).getCritics_rating().equals("Certified Fresh")) {
			iv2.setImageResource(R.drawable.certified_fresh);
		} else if(movieList.get(position).getCritics_rating().equals("Fresh")) {
			iv2.setImageResource(R.drawable.fresh);
		} else if(movieList.get(position).getCritics_rating().equals("Rotten")) {
			iv2.setImageResource(R.drawable.rotten);
		} else if(movieList.get(position).getCritics_rating().equals("Spilled")) {
			iv2.setImageResource(R.drawable.spilled);
		} else if(movieList.get(position).getCritics_rating().equals("Upright")) {
			iv2.setImageResource(R.drawable.upright);
		} else {
			iv2.setImageResource(R.drawable.notranked);
		}
		
		return movieView;

	}

}