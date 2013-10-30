package fr.m2ihm.journey.services;

import fr.m2ihm.journey.listener.GPSListener;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class LocationTrackerService extends Service {
	private LocationManager locationMgr = null;
	int delay;
	private LocationListener myLocationListener = new GPSListener();

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		delay = 6000;
		locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				delay, 0, myLocationListener);
		locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, delay,
				0, myLocationListener);
		super.onCreate();
	}

	public void updateParametre(int delay) {
		this.delay = delay;
		locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				delay, 0, myLocationListener);
		locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, delay,
				0, myLocationListener);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		locationMgr.removeUpdates(myLocationListener);
	}
}