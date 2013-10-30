package fr.m2ihm.journey.metier;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import fr.m2ihm.journey.adapter.GpsAdapter;

public class Gps{
	double latitude;
	double longitude;
	String lieu;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getLieu() {
		return lieu;
	}

	public Gps(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.lieu = "lieu inconnue";
	}
	

}