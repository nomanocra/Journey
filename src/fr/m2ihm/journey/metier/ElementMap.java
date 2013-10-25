package fr.m2ihm.journey.metier;

import fr.m2ihm.journey.adapter.GpsAdapter;
import android.R.string;

public abstract class ElementMap {
	int id;
	Voyage voyage;
	Gps gps;
	String lieu;
	String commentaire;
	String nomMedia;
	boolean automatique;
	Date date;



	// Constructeur appel� pour les evenements entr�s par l'utilisateur.
	public ElementMap(Voyage voyage, Gps positionGps, Date date, String nomMedia, String lieu, String commentaire) {
		this.voyage = voyage;
		gps = positionGps;
		this.lieu = GpsAdapter.gpsToAdresse(gps);
		this.commentaire = commentaire;
		this.nomMedia = nomMedia;
		this.date = date;
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

	public boolean isAutomatique() {
		return automatique;
	}

	public void setAutomatique(boolean automatique) {
		this.automatique = automatique;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType(){
		return "Evenement";
	}
}