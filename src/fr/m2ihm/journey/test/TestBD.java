package fr.m2ihm.journey.test;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.metier.Gps;
import fr.m2ihm.journey.metier.Note;
import fr.m2ihm.journey.metier.Photo;
import fr.m2ihm.journey.metier.Video;
import fr.m2ihm.journey.metier.Voyage;

public class TestBD {
	public static void testBD(Context context) {
		MyBDAdapterImpl myDB = new MyBDAdapterImpl(context);
		/*
		 * Toast.makeText(this,
		 * "Cliquez sur \"je pars en voyage\" pour creer un nouvel album de voyage voyage"
		 * , Toast.LENGTH_LONG).show(); onCreateDialog(1);
		 */
		myDB.open();
		myDB.supprimerAllVoyage();
		myDB.close();
		myDB.open();
		myDB.ajouterVoyage("Voyage 1");
		myDB.close();
		myDB.open();
		Log.v("initDataBase", "Voyage 1");
		Voyage courant = myDB.getVoyageCourant();
		Log.v("initDataBase", "nom : "  +courant.getNom());
		myDB.close();
		
		myDB.open();
		ArrayList<Voyage> lv = myDB.getAllVoyage();
		for (int i = 0; i < lv.size(); i++) {
			Log.v("initDataBase", "id : " + lv.get(i).getId());
			Log.v("initDataBase", "Courant : " + lv.get(i).isEncours());
			Log.v("initDataBase", "Nom :" + lv.get(i).getNom());
		}
		myDB.close();
		/*
		myDB.open();
		myDB.terminerVoyage(myDB.getVoyageCourant().getId());
		lv = myDB.getAllVoyage();
		for (int i = 0; i < lv.size(); i++) {
			Log.v("initDataBase terminer", "id : " + lv.get(i).getId());
			Log.v("initDataBase terminer", "Courant : " + lv.get(i).isEncours());
			Log.v("initDataBase terminer", "Nom :" + lv.get(i).getNom());
		}
		myDB.close();
		*/
		myDB.open();
		ElementMap v = new Video(courant, new Gps(10, 35.5), new Date(0,0,0,0,0,0) , "Video test", "ENAC", "Ce commentaire est inutile");
		ElementMap p = new Photo(courant, new Gps(10, 35.5), new Date(0,0,0,0,0,0), "Photo test", "ENAC", "Ce commentaire est inutile");
		ElementMap n = new Note(courant, new Gps(10, 35.5), new Date(0,0,0,0,0,0),"ENAC", "Ce commentaire est inutile");

		myDB.ajouterElementMap(v);
		myDB.ajouterElementMap(p);
		myDB.ajouterElementMap(n);

		myDB.close();
		
		myDB.open();
		ArrayList<ElementMap> listElementMap = myDB.getAllMedia(courant.getId());
		Log.v("listeMapElement", "" + listElementMap.size());
		for (ElementMap e : listElementMap){
			e.description();
		}
		myDB.close();

	}
}
