package fr.m2ihm.journey.services;

import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.listener.GPSListener;
import fr.m2ihm.journey.settings.Settings;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocationTrackerService extends Service {
	
	private int delay;
	private int distance;
	
	private LocationManager locationMgr = null;
	private LocationListener onLocationChange = new GPSListener(this); 

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		delay = 3000;
		distance = 0;
		Log.v("getAllVoyage", "creation du service");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//delay = Settings.getDelayTraceur();
		Log.v("onStartCommand",""+ delay);
		locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		/*
		locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				delay, 0, onLocationChange);
				*/
		locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, delay,
				distance, onLocationChange);
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		locationMgr.removeUpdates(onLocationChange);
	}

	/*
	private LocationManager locationMgr = null;
	private LocationListener myLocationListener = new GPSListener();
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public void updateParametre(int delay) {
		locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				delay, 0, myLocationListener);
		locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, delay,
				0, myLocationListener);
		Log.v("updateParametre", "Service actif");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		updateParametre(Settings.getDelayTraceur());
		return START_REDELIVER_INTENT;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		locationMgr.removeUpdates(myLocationListener);
	}
	*/
}