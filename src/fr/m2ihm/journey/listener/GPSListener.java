package fr.m2ihm.journey.listener;

import com.google.android.maps.MyLocationOverlay;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;

public class GPSListener extends Activity implements LocationListener{

	private LocationManager lManager;
    private Location location;
	
	
	public GPSListener(){
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Stub de la méthode généré automatiquement
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		setProgressBarIndeterminateVisibility(false);
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Stub de la méthode généré automatiquement
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Stub de la méthode généré automatiquement
		
	}
	
	
}
