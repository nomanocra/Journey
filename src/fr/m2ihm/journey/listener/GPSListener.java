package fr.m2ihm.journey.listener;

import java.io.IOException;
import java.util.List;












import com.google.android.maps.MyLocationOverlay;

import fr.m2ihm.journey.activites.JourneyMainActivity;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.Gps;
import fr.m2ihm.journey.metier.Position;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class GPSListener implements LocationListener {

	private LocationManager lManager;
    private Location location;
    
	private MyBDAdapter myDB;

	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
		Log.v("Mise à jour de la Localisation", "Lat : " + location.getLatitude() + "| Lon : " + location.getLongitude());
		savePosition();
	}
	
	private void savePosition(){
		myDB.open();
		Position maPosition = new Position(myDB.getVoyageCourant(), new Gps(location.getLatitude(), location.getLongitude()), Date.dateCourant());
		myDB.ajouterElementMap(maPosition);
		myDB.close();
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		Log.v("Géolocalisation", "La source a été activé.");
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		/*
        switch (status) {
        case LocationProvider.AVAILABLE:
			Toast message = Toast.makeText(JourneyMainActivity.class,"Network location available again", Toast.LENGTH_SHORT);
			message.setGravity(1, 400, -400);
			message.show();
            break;
        case LocationProvider.OUT_OF_SERVICE:
			Toast message = Toast.makeText(,"Network location out of service", Toast.LENGTH_SHORT);
			message.setGravity(1, 400, -400);
			message.show();
            textView.setText(tvTxt + "Network location out of service\n");
            break;
        case LocationProvider.TEMPORARILY_UNAVAILABLE:
			Toast message = Toast.makeText(getApplicationContext(),"Network location temporarily unavailable", Toast.LENGTH_SHORT);
			message.setGravity(1, 400, -400);
			message.show();

            break;
        }		
        */
	}
	
}
	