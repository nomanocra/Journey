package fr.m2ihm.journey.metier;

public class Voyage implements Comparable {
	int id;
	String nom;
	boolean encours;
	Date debut;
	Date fin;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public boolean isEncours() {
		return encours;
	}
	public void setEncours(boolean encours) {
		this.encours = encours;
	}
	public Date getDebut() {
		return debut;
	}
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	
	public Voyage(String nom, boolean encours, Date debut,
			Date fin) {
		super();
		this.nom = nom;
		this.encours = encours;
		this.debut = debut;
		this.fin = fin;
	}
	@Override
	public int compareTo(Object otherVoyage) {
		
		if(this.debut.isMoreActualThan(
				((Voyage) otherVoyage).getFin())
		){
			// So this Voyage is more actuel than the one given
			return 1;
		}
		else
		{
			return -1;
		}
	}
}
