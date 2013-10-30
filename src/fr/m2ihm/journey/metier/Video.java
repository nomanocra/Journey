package fr.m2ihm.journey.metier;

import fr.m2ihm.journey.R;

public class Video extends ElementMap{

	public Video(Voyage voyage, Gps positionGps, Date date, String nomMedia,
			String lieu, String commentaire) {
		super(voyage, positionGps, date, nomMedia, lieu, commentaire);
		// TODO Stub du constructeur généré automatiquement
	}
	@Override
	public String getType(){
		return "Video";
	}
	
	@Override
	public int getIconResource(){
		return R.drawable.ic_launcher;
	}
}