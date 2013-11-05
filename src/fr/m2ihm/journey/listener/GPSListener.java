package fr.m2ihm.journey.listener;

import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.Gps;
import fr.m2ihm.journey.metier.Position;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class GPSListener implements LocationListener {

    private Location location;
    private Context context;
	private MyBDAdapter myDB;

	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
		savePosition();
	}
	
	public GPSListener(Context c){
		context = c;
	}
	
	public Gps getGps(){
		return new Gps(location.getLatitude(),location.getLongitude());
	}
	
	private void savePosition(){
		myDB = new MyBDAdapterImpl(context);
		myDB.open();
		Position maPosition = new Position(myDB.getVoyageCourant(), new Gps(location.getLatitude(), location.getLongitude()), Date.dateCourant());
		myDB.ajouterElementMap(maPosition);
		myDB.close();
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		Log.v("Géolocalisation", "La source a été désactivé.");
	}
	@Override
	public void onProviderEnabled(String provider) {
		Log.v("Géolocalisation", "La source a été activé.");
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}
	
}
	