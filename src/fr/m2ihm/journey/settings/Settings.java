package fr.m2ihm.journey.settings;

public class Settings {
	static public int delayTraceur = 30000;
	static public boolean traceurActive = false;
	
	
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
	

	
}