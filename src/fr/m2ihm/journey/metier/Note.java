package fr.m2ihm.journey.metier;

import fr.m2ihm.journey.R;

public class Note extends ElementMap{

	public Note(Voyage voyage, Gps positionGps, Date date,
			String lieu, String commentaire) {
		super(voyage, positionGps, date, "No media", lieu, commentaire);
		// TODO Stub du constructeur généré automatiquement
	}
	
	public Note(Voyage voyage, Gps positionGps,
			String lieu, String commentaire) {
		super(voyage, positionGps, "No media", lieu, commentaire);
		// TODO Stub du constructeur généré automatiquement
	}
	
	@Override
	public String getType(){
		return "Note";
	}
	
	@Override
	public int getIconResource(){
		return R.drawable.icon_note;
	}
}
