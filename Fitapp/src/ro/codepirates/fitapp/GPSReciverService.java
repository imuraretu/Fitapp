package ro.codepirates.fitapp;

import java.text.DecimalFormat;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GPSReciverService extends Service {
	// Actions for Intents and IntentFilters
	public static final String ACTION_START = "ro.codepirates.fitapp.action.START";
	public static final String ACTION_STOP = "ro.codepirates.fitapp.action.STOP";

	public static final String ACTION_TO_SERVICE = "ro.codepirates.fitapp.action.TO_SERVICE";
	public static final String ACTION_FROM_SERVICE = "ro.codepirates.fitapp.action.FROM_SERVICE";

	private LocationManager lm;
	private LocationListener locationListener;

	private static long minTimeMillis = 500;
	private static long minDistanceMeters = 10;
	// private static float minAccuracyMeters = 35;

	Location curent = null;
	Location last = null;
	double mDist;

	/**
	 * Receiver for messages sent from Activity to Service.
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

		}
	};

	private void startLoggerService() {

		// ---use the LocationManager class to obtain GPS locations---
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locationListener = new MyLocationListener();

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeMillis,
				0, locationListener);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		startLoggerService();

		final IntentFilter myFilter = new IntentFilter(ACTION_TO_SERVICE);
		registerReceiver(mReceiver, myFilter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sendToActivity("Service started");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		shutdownLoggerService();
		//sendToActivity("Destroying service");
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	private void shutdownLoggerService() {
		lm.removeUpdates(locationListener);
	}

	/**
	 * Sends broadcast with text stored in intent extra data ("data" key).
	 * 
	 * @param text
	 *            text to send
	 */
	void sendToActivity(CharSequence text) {
		Log.d("BroadcastService", "Sending message to activity: " + text);
		final Intent intent = new Intent(GPSReciverService.ACTION_FROM_SERVICE);
		intent.putExtra("data", text);
		sendBroadcast(intent, ro.codepirates.fitapp.Manifest.permission.ALLOW);
	}

	void sendToActivity(Location location) {
		DecimalFormat form = new DecimalFormat("0.00");

		curent = location;
		double mSpeed = (double) ((location.getSpeed() * 3600) / 1000);
		String speed = form.format(mSpeed);

		if (last == null) {
			last = curent;
		}
		mDist = mDist + curent.distanceTo(last);
		String dist = form.format(mDist);

		final Intent intent = new Intent(GPSReciverService.ACTION_FROM_SERVICE);
		intent.putExtra("altitude", Double.toString(location.getAltitude()));
		intent.putExtra("distance", dist);
		intent.putExtra("speed", speed);
		sendBroadcast(intent, ro.codepirates.fitapp.Manifest.permission.ALLOW);
		last = curent;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location loc) {
			sendToActivity(loc);
		}

		public void onProviderDisabled(String provider) {

			Toast.makeText(getBaseContext(), provider.toUpperCase() + " Disabled",
					Toast.LENGTH_SHORT).show();

		}

		public void onProviderEnabled(String provider) {

			Toast.makeText(getBaseContext(), provider.toUpperCase() + " Enabled",
					Toast.LENGTH_SHORT).show();

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

	}

}