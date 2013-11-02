package fr.m2ihm.journey.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.metier.Gps;
import fr.m2ihm.journey.metier.Note;
import fr.m2ihm.journey.metier.Photo;
import fr.m2ihm.journey.metier.Position;
import fr.m2ihm.journey.metier.Son;
import fr.m2ihm.journey.metier.Video;
import fr.m2ihm.journey.metier.Voyage;

public interface MyBDAdapter {
	public void open();

	public void close();

	public ElementMap getEvenement(long id);

	public Voyage getVoyage(long id);

	public ArrayList<Voyage> getAllVoyages();
	
	public ArrayList<ElementMap> getAllMedia(int idVoyage);

	public ArrayList<Son> getAllSon(int idVoyage);

	public ArrayList<ElementMap> getAllPhoto(int idVoyage);

	public ArrayList<Video> getAllVideo(int idVoyage);

	public ArrayList<Position> getAllPosition(int idVoyage);

	public Voyage getVoyageCourant();

	public long ajouterVoyage(String nom);

	public long ajouterElementMap(ElementMap e);

	public int modifierNomVoyage(long id, String nom);
	
	public int terminerVoyage(long id);
	
	public int modifierElementMap(long id, String comment, String lieu);

	public int supprimerVoyage(long id);

	public int supprimerEvenemet(long id);
	
	public int supprimerAllVoyage();
}
