package fr.m2ihm.journey.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import fr.m2ihm.journey.metier.Gps;

public class GpsAdapter {

	
	public static String gpsToAdresse(Gps location, Context context) {
		String myAdresse = "Adresse non trouv�e";
		// Le geocoder permet de r�cup�rer ou chercher des adresses
		// gr�ce � un mot cl� ou une position
		Geocoder geo = new Geocoder(context);
		try {
			// Ici on r�cup�re la premiere adresse trouv� gr�ce � la position
			// que l'on a r�cup�r�
			List<Address> adresses = geo.getFromLocation(
					location.getLatitude(), location.getLongitude(), 1);

			if (adresses != null && adresses.size() == 1) {
				Address adresse = adresses.get(0);
				// Si le geocoder a trouver une adresse, alors on l'a met dans
				// myAdresse
				myAdresse = String.format("%s, %s %s",
						adresse.getAddressLine(0), adresse.getPostalCode(),
						adresse.getLocality());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myAdresse;
	}

	
	
	
	
	
	
	
	
	
	
	
	public static String gpsToAdresse(Gps location) {
		return "Fonction pas encore imp�ment�";
	}

	public static Gps adresseToGps(String adresse) {
		// TODO Stub de la m�thode g�n�r� automatiquement
		return new Gps(0, 0);
	}

	public static Gps getCurrentGps() {
		// TODO Stub de la m�thode g�n�r� automatiquement
		return new Gps(0, 0);
	}

}
