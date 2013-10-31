package fr.m2ihm.journey.metier;

import fr.m2ihm.journey.R;

public class Son extends ElementMap{

	public Son(Voyage voyage, Gps positionGps, Date date, String nomMedia,
			String lieu, String commentaire) {
		super(voyage, positionGps, date, nomMedia, lieu, commentaire);
		// TODO Stub du constructeur généré automatiquement
	}
	@Override
	public String getType(){
		return "Son";
	}
	
	@Override
	public int getIconResource(){
		return R.drawable.ic_launcher;
	}
}
