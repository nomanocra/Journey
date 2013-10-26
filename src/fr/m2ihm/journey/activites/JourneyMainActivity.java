package fr.m2ihm.journey.activites;

import java.util.ArrayList;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Voyage;
import fr.m2ihm.journey.test.TestBD;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

enum State {
	nomVoyageEnCours, PASVOYAGE
}

public class JourneyMainActivity extends Activity {

	private final static int ITEM_CONFIRME = Menu.FIRST;
	private final static int ITEM_ANNULE = Menu.FIRST + 1;
	private final static int IDENTIFIANT_BOITE_UN = 0;
	private final static int IDENTIFIANT_BOITE_DEUX = 1;

	private Button newTrip;
	private Button newEvent;
	private Button endTrip;
	private Button journal;
	private Button setting;
	private TextView voyageEnCours;
	private String nomVoyageEnCours;
	private State etat;

	MyBDAdapter myDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		TestBD.testBD(this);
	}



	public void init() {
		Log.v("INIT", "3");
		newTrip = (Button) findViewById(R.id.boutonJeParsEnVoyage);
		newEvent = (Button) findViewById(R.id.boutonAjoutEvenement);
		endTrip = (Button) findViewById(R.id.boutonJeTermineMonVoyage);
		journal = (Button) findViewById(R.id.boutonCarnetVoyage);
		setting = (Button) findViewById(R.id.boutonParametre);
		pasDeVoyageLayout();

	}

	public void enVoyageLayout() {
		setContentView(R.layout.acceuil2);
		System.out.println("3");
		voyageEnCours = (TextView) findViewById(R.id.voyageEnCours);
		voyageEnCours.setText(nomVoyageEnCours);
		System.out.println("4");
	}

	public void pasDeVoyageLayout() {
		setContentView(R.layout.acceuil);
	}

	// Gestion des boutons
	public void actionBoutonAjoutVoyage(View v) {
		nomVoyageEnCours = "Doubai";
		new AlertDialog.Builder(JourneyMainActivity.this)
		// .setTitle("Nouveau voyage")
		// .setMessage("Veuillez entrer un nom pour votre nouveau voyage")
				.setView(findViewById(R.layout.formulaire_nouveau_evement))

				/*
				 * .setPositiveButton("C'est partie !", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int whichButton) { EditText
				 * nomInput = (EditText) findViewById(R.id.champNouveauVoyage);
				 * Editable value = nomInput.getText(); }
				 * }).setNegativeButton("Annuler", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int whichButton) { // Do
				 * nothing. } })
				 */
				.show();
		enVoyageLayout();
	}

	public void actionBoutonAjoutEvenement(View v) {
		Intent intent = new Intent(this, AjouterEvenementActivity.class);
		startActivity(intent);
		finish();
	}

	public void actionBoutonCarnetVoyage(View v) {
		Intent intent = new Intent(this, JournalActivity.class);
		startActivity(intent);
		finish();
	}

	public void actionBoutonSettings(View v) {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
		finish();
	}

	public void actionBoutonTermineVoyage(View v) {
		pasDeVoyageLayout();
		nomVoyageEnCours = "";
	}

	@Override
	public Dialog onCreateDialog(int identifiant) {
		Dialog box = null;
		// En fonction de l'identifiant de la boîte qu'on veut créer
		switch (identifiant) {
		case IDENTIFIANT_BOITE_UN:
			box = new Dialog(this);
			box.setTitle("Je viens tout juste de naître.");
			box.setContentView(R.layout.formulaire_nouveau_voyage);
			break;

		case IDENTIFIANT_BOITE_DEUX:
			box = new Dialog(this);
			box.setTitle("ET MOI ALORS ???");
			break;
		}
		return box;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
