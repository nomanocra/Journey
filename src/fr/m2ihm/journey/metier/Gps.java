package fr.m2ihm.journey.metier;

import fr.m2ihm.journey.adapter.GpsAdapter;

public class Gps{
	float latitude;
	float longitude;
	String lieu;
	
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getLieu() {
		return lieu;
	}

	public Gps(float latitude, float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.lieu = GpsAdapter.gpsToAdresse(this);
	}
}