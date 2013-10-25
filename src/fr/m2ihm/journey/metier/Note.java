package fr.m2ihm.journey.metier;

public class Note extends ElementMap{

	public Note(Voyage voyage, Gps positionGps, Date date,
			String lieu, String commentaire) {
		super(voyage, positionGps, date, "", lieu, commentaire);
		// TODO Stub du constructeur généré automatiquement
	}
	@Override
	public String getType(){
		return "Note";
	}
}
