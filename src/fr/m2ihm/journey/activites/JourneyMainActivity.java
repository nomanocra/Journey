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
import android.text.InputType;
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
	Init, PasVoyage, VoyageEnCours, NouveauVoayge
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
		// TestBD.testBD(this);
	}

	public void init() {
		MyBDAdapterImpl myDB = new MyBDAdapterImpl(this);
		newTrip = (Button) findViewById(R.id.boutonJeParsEnVoyage);
		newEvent = (Button) findViewById(R.id.boutonAjoutEvenement);
		endTrip = (Button) findViewById(R.id.boutonJeTermineMonVoyage);
		journal = (Button) findViewById(R.id.boutonCarnetVoyage);
		setting = (Button) findViewById(R.id.boutonParametre);
		pasDeVoyageLayout();
		TextView textAcceuil = new TextView(getApplicationContext());
		
		textAcceuil.setTextSize(25);
		textAcceuil.setBackgroundColor(Color.BLACK);
		textAcceuil.setTextColor(Color.WHITE);
		textAcceuil.setSingleLine();
		textAcceuil.setText("Cliquez sur \"je pars en voyage\" pour creer un nouvel album de voyage");
		 
		Toast messageAcceuil = Toast.makeText(JourneyMainActivity.this,"Cliquez sur \"je pars en voyage\" pour creer un nouvel album de voyage", Toast.LENGTH_LONG);
		 messageAcceuil.setGravity(0, 0, 200);
		// messageAcceuil.setView(textAcceuil);
		 messageAcceuil.show();
	}

	public void enVoyageLayout() {
		setContentView(R.layout.acceuil2);
		voyageEnCours = (TextView) findViewById(R.id.voyageEnCours);
		voyageEnCours.setText(nomVoyageEnCours);
	}

	public void pasDeVoyageLayout() {
		setContentView(R.layout.acceuil);
	}

	// Gestion des boutons
	public void actionBoutonAjoutVoyage(View v) {
		nomVoyageEnCours = "Doubai";
		
		final EditText champNouveauVoyage = new EditText(JourneyMainActivity.this);
		//champNouveauVoyage.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		champNouveauVoyage.setBackgroundColor(Color.WHITE);
		champNouveauVoyage.setTextSize(35);
		AlertDialog.Builder nouveauVoyageDialogue = new AlertDialog.Builder(
				JourneyMainActivity.this);
		nouveauVoyageDialogue.setView(champNouveauVoyage);
		nouveauVoyageDialogue.setTitle("Nouveau voyage");
		nouveauVoyageDialogue .setMessage("Veuillez entrer un nom pour votre nouveau voyage");
		
		nouveauVoyageDialogue.setPositiveButton("C'est partie !",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String nomVoyage = champNouveauVoyage.getText().toString();
						if(nomVoyage != ""){
							myDB.ajouterVoyage(nomVoyage);
						}else{
							 Toast.makeText(getApplicationContext(), "Vous n'avez pas entré de nom de voyage", Toast.LENGTH_LONG);
						}
					}
				});
		nouveauVoyageDialogue.setNegativeButton("Annuler",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		nouveauVoyageDialogue.show();

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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
