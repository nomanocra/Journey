package fr.m2ihm.journey.activites;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.metier.Gps;
import fr.m2ihm.journey.metier.Voyage;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;



public class AjouterEvenementActivity extends Activity {
	ElementMap e;
	Gps gps;
	String lieu;
	String commentaire;
	String nomMedia;
	Voyage voyageCourant;
	Intent intent;
	Date date;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("luuul");
		setContentView(R.layout.formulaire_nouveau_evement);
		System.out.println("luuul");
		intent = getIntent();
		System.out.println("luuul");
		}


	public void save() {
		Intent intent = new Intent(this, JourneyMainActivity.class);
		startActivity(intent);
		finish();
	}
}
