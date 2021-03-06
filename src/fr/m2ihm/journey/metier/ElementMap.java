package fr.m2ihm.journey.metier;

import android.util.Log;

public abstract class ElementMap implements Comparable<ElementMap> {
	int id;
	Voyage voyage;
	Gps gps;
	String lieu;
	String commentaire;
	String nomMedia;
	Date date;

	// Constructeur appel� pour les evenements entr�s par l'utilisateur.
	public ElementMap(Voyage voyage, Gps positionGps, Date date,
			String nomMedia, String lieu, String commentaire) {
		this.id = -1;
		this.voyage = voyage;
		gps = positionGps;
		this.lieu = lieu;
		this.commentaire = commentaire;
		this.nomMedia = nomMedia;
		this.date = date;
	}

	// Override the constructor, to add an EM to the DB we don't need a date
	public ElementMap(Voyage voyage, Gps positionGps, String nomMedia,
			String lieu, String commentaire) {
		this.id = -1;
		this.voyage = voyage;
		gps = positionGps;
		this.lieu = lieu;
		this.commentaire = commentaire;
		this.nomMedia = nomMedia;
		this.date = new Date(0, 0, 0, 0, 0, 0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Voyage getVoyage() {
		return voyage;
	}

	public void setVoyage(Voyage voyage) {
		this.voyage = voyage;
	}

	public Gps getGps() {
		return gps;
	}

	public void setGps(Gps gps) {
		this.gps = gps;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getNomMedia() {
		return nomMedia;
	}

	public void setNomMedia(String nomMedia) {
		this.nomMedia = nomMedia;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return "Evenement";
	}

	public void description() {
		Log.v("Descritption", "-----------------Debut-----------");
		Log.v(nomMedia, "id : " + id + "\nVoyage : " + voyage.getNom()
				+ "\n Gps : " + gps.getLatitude() + " | " + gps.longitude
				+ "\n Lieu : " + lieu + "\n Commentaire : " + commentaire
				+ "\n Nom media : " + nomMedia + "\n Heure :" + date.getHour()
				+ ":" + date.getMinute() + ":" + date.getSecond() + "."
				+ "\n Date :" + date.getDay() + "/" + date.getMonth() + "/"
				+ date.getYear());
		Log.v("Descritption", "-----------------Fin-----------");
	}

	public int getIconResource() {
		return 0;
	}

	@Override
	public int compareTo(ElementMap otherElement) {

		if (this.date.isMoreActualThan(otherElement.getDate())) {
			return 1;
		}
		else {
			return -1;
		}
	}


}
