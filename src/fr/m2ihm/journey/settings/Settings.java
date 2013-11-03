package fr.m2ihm.journey.settings;

import android.content.SharedPreferences;

public class Settings {
	static public int delayTraceur;
	static public int distanceTraceur;
	static public boolean traceurActive;
	
	public static int getDelayTraceur() {
		return delayTraceur;
	}
	public static void setDelayTraceur(int delayTraceur) {
		Settings.delayTraceur = delayTraceur;
	}
	public static boolean isTraceurActive() {
		return traceurActive;
	}
	public static void setTraceurActive(boolean traceurActive) {
		Settings.traceurActive = traceurActive;
	}
	public static int getDistanceTraceur() {
		return distanceTraceur;
	}
	public static void setDistanceTraceur(int distanceTraceur) {
		Settings.distanceTraceur = distanceTraceur;
	}


}
