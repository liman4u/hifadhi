package com.example.hifadhi;

import com.example.locateit.R;
import com.example.hifadhi.utils.AllConstants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HifadhiActivity extends Activity implements
		OnClickListener {
	/** Called when the activity is first created. */

	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in
	// Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in
	// Milliseconds

	protected LocationManager locationManager;

	private LinearLayout hotel, atm, restaurant;
	private static Context con;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homemenu);
		con = this;
		iUI();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				MINIMUM_TIME_BETWEEN_UPDATES,
				MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());
	}

	/* AlertMethod */

	protected void alertbox(String title, String mymessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your Device's GPS is Disable.Please,Turn on and wait few seconds.").setCancelable(false)
				.setTitle("Gps Status").setPositiveButton("Gps On",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// finish the current activity
								// AlertBoxAdvance.this.finish();
								Intent myIntent = new Intent(
										Settings.ACTION_SECURITY_SETTINGS);
								startActivity(myIntent);
								dialog.cancel();
							}
						}).setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// cancel the dialog box
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	protected void showCurrentLocation() {

		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (location != null) {
			String message = String.format(
					"Current Location \n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude());
			Toast.makeText(HifadhiActivity.this, message,
					Toast.LENGTH_LONG).show();

			Log.e("GeoData:", message);
			// final TextView myLat = (TextView) findViewById(R.id.myLat);

			double lat = location.getLatitude();

			// myLat.setText(String.valueOf(lat));

			// final TextView myLng = (TextView) findViewById(R.id.myLng);

			double lng = location.getLongitude();
			// myLng.setText(String.valueOf(lng));

			AllConstants.UPlat = String.valueOf(lat);
			AllConstants.UPlng = String.valueOf(lng);
		}

	}

	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			String message = String.format(
					"New Location \n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude());

			// Toast.makeText(SplashScreenActivity.this, message,
			// Toast.LENGTH_LONG).show();
		}

		public void onStatusChanged(String s, int i, Bundle b) {
			// Toast.makeText(CityGuideActivity.this, "Provider status changed",
			// Toast.LENGTH_LONG).show();
		}

		public void onProviderDisabled(String s) {
			alertbox("Gps Status!!", "Your GPS is: OFF");

			// Toast.makeText(CityGuideActivity.this,
			// "Provider disabled by the user. GPS turned off",
			// Toast.LENGTH_LONG).show();
		}

		public void onProviderEnabled(String s) {
			Toast.makeText(HifadhiActivity.this, "GPS turned on",
					Toast.LENGTH_LONG).show();
		}

	}

	/* Initialize User Interface */

	private void iUI() {

		restaurant = (LinearLayout) findViewById(R.id.nearbyrestaurant);
		restaurant.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		 showCurrentLocation();

		switch (v.getId()) {



		case R.id.nearbyrestaurant:
			AllConstants.query = "1";
			final Intent restaurant = new Intent(this, ListActivity.class);
			restaurant.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(restaurant);

		}

	}

	public void btnAbout(View v) {
		AllConstants.cWeb = "http://www.google.com";
		Intent next = new Intent(con, ReviewActivity.class);
		next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(next);
	}

	public void btnFacebook(View v) {

		Intent next = new Intent(con, FacebookActivity.class);
		next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(next);
	}

	public void btnTwitter(View v) {

		Intent next = new Intent(con, TwitterActivity.class);
		next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(next);
	}
	
	public void btnSearch(View v) {

		Intent next = new Intent(con, SearchActivity.class);
		next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(next);
	}
}