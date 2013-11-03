package fr.m2ihm.journey.metier;

import fr.m2ihm.journey.R;

public class Photo extends ElementMap{

	public Photo(Voyage voyage, Gps positionGps, Date date, String nomMedia,
			String lieu, String commentaire) {
		super(voyage, positionGps, date, nomMedia, lieu, commentaire);
		// TODO Stub du constructeur généré automatiquement
	}
	
	public Photo(Voyage voyage, Gps positionGps, String nomMedia,
			String lieu, String commentaire) {
		super(voyage, positionGps, nomMedia, lieu, commentaire);
		// TODO Stub du constructeur généré automatiquement
	}
	
	@Override
	public String getType(){
		return "Photo";
	}
	
	@Override
	public int getIconResource(){
		return R.drawable.icon_photo;
	}
}
