package fr.m2ihm.journey.metier;

public class Position extends ElementMap{

	public Position(Voyage voyage, Gps positionGps, Date date) {
		super(voyage, positionGps, date, "No media", "", "No comment");
		// TODO Stub du constructeur g�n�r� automatiquement
	}
	
	public Position(Voyage voyage, Gps positionGps) {
		super(voyage, positionGps, "No media", "", "No comment");
		// TODO Stub du constructeur g�n�r� automatiquement
	}
	
	@Override
	public String getType(){
		return "Position";
	}
}
