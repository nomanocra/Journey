package fr.m2ihm.journey.metier;

public class Photo extends ElementMap{

	public Photo(Voyage voyage, Gps positionGps, Date date, String nomMedia,
			String lieu, String commentaire) {
		super(voyage, positionGps, date, nomMedia, lieu, commentaire);
		// TODO Stub du constructeur g�n�r� automatiquement
	}
	@Override
	public String getType(){
		return "Photo";
	}
}