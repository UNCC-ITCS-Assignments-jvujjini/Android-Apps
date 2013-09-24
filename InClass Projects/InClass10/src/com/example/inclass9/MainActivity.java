/*
*a. Assignment # : In Class 10
*
*b. File Name : MainActivity.java
*
*c. Full name of all students in your group: Jagan Mohan Rao Vujjini
*/

package com.example.inclass9;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.zxing.integration.android.*;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener{
	
	private static final long MIN_TIME_BW_UPDATES = 2000;
	private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
	Double currLat,currLong,myLat,myLong,nextLat,nextLong,startLat,startLong;
	Boolean gpsEnabled,networkEnabled,getLocation;
	Location location;
	LocationManager manager;
	GoogleMap mMap;
	IntentIntegrator integrator;
	String[] place,currPlaceInfo,nextPlaceInfo;
	String currName="",nextName="",startName="";
	PolylineOptions rectOptions = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		myLat = myLocation().getLatitude();
		myLong = myLocation().getLongitude();
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLat, myLong),18));
		
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
		case R.id.scan:
			integrator = new IntentIntegrator(MainActivity.this);
			integrator.initiateScan();
			break;
		case R.id.restart:
			 mMap.clear();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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
							myLong = location.getLongitude();
						}
					}
				}
				if (gpsEnabled) {
					if (location == null) {
						manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						if (manager != null) {
							location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								myLat = location.getLatitude();
								myLong = location.getLongitude();
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
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		  if (scanResult != null) {
		    // handle scan result
			parseResult(scanResult.getContents());
			
		  }
		  // else continue with any other code you need in the method
		}

	private void parseResult(String result) {
		// TODO Auto-generated method stub
		
		place = result.split(";");
		currPlaceInfo = place[0].split(",");
		nextPlaceInfo = place[1].split(",");
		currName = currPlaceInfo[0];
		currLat = Double.parseDouble(currPlaceInfo[1]);
		currLong = Double.parseDouble(currPlaceInfo[2]);
		nextName = nextPlaceInfo[0];
		nextLat = Double.parseDouble(nextPlaceInfo[1]);
		nextLong = Double.parseDouble(nextPlaceInfo[2]);
		mMap.addMarker(new MarkerOptions().position(new LatLng(currLat, currLong)).title(currName));
		mMap.addMarker(new MarkerOptions().position(new LatLng(nextLat, nextLong)).title(nextName));
		
		if(startName == currName) {
			
			Toast.makeText(this, "Successfully Completed Scavenger Hunt!!", Toast.LENGTH_LONG).show();
			
		}
		
		if(rectOptions == null) {
			rectOptions = new PolylineOptions().add(new LatLng(currLat, currLong));
			startName = currName;
		}
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		if(rectOptions != null) {
			rectOptions.add(new LatLng(location.getLatitude(), location.getLongitude()));
			mMap.addPolyline(rectOptions);
		}

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