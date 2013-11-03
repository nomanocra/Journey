package fr.m2ihm.journey.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import fr.m2ihm.journey.listener.GPSListener;
import fr.m2ihm.journey.settings.Settings;

public class LocationTrackerService extends Service {
	
	
	private LocationManager locationMgr = null;
	private LocationListener onLocationChange = new GPSListener(this); 

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v("LOCATIONTRACERSERVICES", "startLocationService");
		locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, Settings.getDelayTraceur(),
				Settings.getDistanceTraceur(), onLocationChange);
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		locationMgr.removeUpdates(onLocationChange);
		Log.v("LOCATIONTRACERSERVICES", "stopLocationService");
	}

}