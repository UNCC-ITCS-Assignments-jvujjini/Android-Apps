/*
*a. Assignment # : In Class 9
*
*b. File Name : PlacesActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

public class PlacesActivity extends Activity implements LocationListener {
	
	private static final long MIN_TIME_BW_UPDATES = 0;
	private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
	String place,placesUrl;
	GoogleMap mMap;
	Double lat,lon,myLat,myLon;
	LocationManager manager;
	Boolean gpsEnabled,networkEnabled,getLocation;
	Location location;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places);
		
		place = getIntent().getExtras().getString("place");
		myLat = myLocation().getLatitude();
		myLon = myLocation().getLongitude();
		placesUrl="https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
				"key=AIzaSyD71X-H1EzIK3fKgk_pIQ9QfBS6-OVlURo&location="+myLat+","+myLon+"&" +
				"radius=2000&sensor=false&types=" + place;
		
		mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLat, myLon),10));
		
		new GetPlaceInfo().execute(placesUrl);

		
		
		
	}
	
	private class GetPlaceInfo extends AsyncTask<String, Void, ArrayList<Place>> {

		@Override
		protected ArrayList<Place> doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlString = params[0];
			ArrayList<Place> places = new ArrayList<Place>();
			try {
				URL url = new URL(urlString);			
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.connect();			
				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {				
					BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line = reader.readLine();
					while (line != null) {
						sb.append(line);
						line = reader.readLine();
					}
					places = PlaceJSONParser.parsePlace(sb.toString());
					
					return places;
					
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Place> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			
				for(Place p:result) {
					lat=p.getLatitude();
					lon=p.getLongitude();
					mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(p.getName()));
				}
		
		}
		
	}
	
	protected static class PlaceJSONParser {
		 static ArrayList<Place> parsePlace(String jsonString) throws JSONException{
			ArrayList<Place> placeList = new ArrayList<Place>();
			JSONObject placeObj = new JSONObject(jsonString);
			JSONArray resultsArray = placeObj.getJSONArray("results");
			for(int i=0; i<resultsArray.length(); i++) {
				JSONObject uniquePlace = resultsArray.getJSONObject(i);
				Place  place = new Place(uniquePlace);
				placeList.add(place);
			}
			return placeList;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.places, menu);
		return true;
	}
	
	public Location myLocation() {
		try {
			manager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
			gpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			networkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (gpsEnabled && networkEnabled) {
				
				this.getLocation = true;
				if (networkEnabled) {
					manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if (manager != null) {
						location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							myLat = location.getLatitude();
							myLon = location.getLongitude();
						}
					}
				}
				if (gpsEnabled) {
					if (location == null) {
						manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
						if (manager != null) {
							location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								myLat = location.getLatitude();
								myLon = location.getLongitude();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
