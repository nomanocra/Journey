package fr.m2ihm.journey.metier;

public class Position extends ElementMap{

	public Position(Voyage voyage, Gps positionGps, Date date) {
		super(voyage, positionGps, date, "", "", "");
		// TODO Stub du constructeur généré automatiquement
	}
	@Override
	public String getType(){
		return "Position";
	}
}
