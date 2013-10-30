package fr.m2ihm.journey.activites;

import java.util.ArrayList;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.GpsAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.metier.Note;
import fr.m2ihm.journey.metier.Photo;
import fr.m2ihm.journey.metier.Voyage;
import fr.m2ihm.journey.services.LocationTrackerService;
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
import android.widget.ToggleButton;

enum State {
	Init, PasVoyage, VoyageEnCours, NouveauVoayge
}

public class JourneyMainActivity extends Activity {

	private Button newTrip;
	private Button newEvent;
	private Button endTrip;
	private Button journal;
	private Button setting;
	private ToggleButton tracerManagerButton;
	private TextView textVoyageEnCours;
	private Voyage voyageEnCours;
	private State etat;

	MyBDAdapter myDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myDB = new MyBDAdapterImpl(this);
		newTrip = (Button) findViewById(R.id.boutonJeParsEnVoyage);
		newEvent = (Button) findViewById(R.id.boutonAjoutEvenement);
		endTrip = (Button) findViewById(R.id.boutonJeTermineMonVoyage);
		journal = (Button) findViewById(R.id.boutonCarnetVoyage);
		setting = (Button) findViewById(R.id.boutonParametre);
		tracerManagerButton = (ToggleButton) findViewById(R.id.activationTracker);
		TestBD.testBD2(this);
		init();
		//TestBD.testBD(this);
		
		
	}

	public void init() {
		myDB.open();
		voyageEnCours = myDB.getVoyageCourant();
		myDB.close();
		if (voyageEnCours.getId() == -1) {
			pasDeVoyageLayout();
			Toast messageAcceuil = Toast
					.makeText(
							JourneyMainActivity.this,
							"Cliquez sur \"je pars en voyage\" pour creer un nouvel album de voyage",
							Toast.LENGTH_LONG);
			messageAcceuil.setGravity(0, 0, 200);
			messageAcceuil.show();
		} else {
			enVoyageLayout();
		}

		/*
		 * TextView textAcceuil = new TextView(getApplicationContext());
		 * 
		 * textAcceuil.setTextSize(25);
		 * textAcceuil.setBackgroundColor(Color.BLACK);
		 * textAcceuil.setTextColor(Color.WHITE); textAcceuil.setSingleLine();
		 * textAcceuil.setText(
		 * "Cliquez sur \"je pars en voyage\" pour creer un nouvel album de voyage"
		 * );
		 */

	}

	public void enVoyageLayout() {
		
		setContentView(R.layout.acceuil2);
		myDB.open();
		voyageEnCours = myDB.getVoyageCourant();
		Log.i("enVoyageLayout", ""+voyageEnCours.getId());
		myDB.close();
		textVoyageEnCours = (TextView) findViewById(R.id.voyageEnCoursText);
		textVoyageEnCours.setText(voyageEnCours.getNom());
	}

	public void pasDeVoyageLayout() {
		setContentView(R.layout.acceuil);
		Log.i("pasDeVoyageLayout", "pasDeVoyageLayout");
	}

	// Gestion des boutons
	public void actionBoutonAjoutVoyage(View v) {
		// champNouveauVoyage.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

		LayoutInflater factory = LayoutInflater.from(this);
		final View alertDialogView = factory.inflate(
				R.layout.formulaire_nouveau_voyage, null);
		AlertDialog.Builder nouveauVoyageDialogue = new AlertDialog.Builder(
				JourneyMainActivity.this);
		nouveauVoyageDialogue.setView(alertDialogView);
		nouveauVoyageDialogue.setTitle("Nouveau voyage");
		nouveauVoyageDialogue.setPositiveButton("C'est partie !",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						EditText champNouveauVoyage = (EditText) alertDialogView.findViewById(R.id.champNouveauVoyage);
						String nomVoyage = champNouveauVoyage.getText().toString();
						if (!nomVoyage.equals("")) {
							myDB.open();
							myDB.ajouterVoyage(nomVoyage);
							myDB.close();
							Toast message = Toast.makeText(getApplicationContext(),"Vous êtes maintenant en voyage à : " + nomVoyage, Toast.LENGTH_LONG);
							message.setGravity(1, 0, 200);
							message.show();
						} else {
							Toast message = Toast.makeText(getApplicationContext(),"Vous n'avez pas entré de nom de voyage. Veuillez recommencer", Toast.LENGTH_LONG);
							message.setGravity(1, 0, 200);
							message.show();
						}
						enVoyageLayout();
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
	public void startLocationTracerService(){
		startService(new Intent(JourneyMainActivity.this, LocationTrackerService.class));
	}
	
	public void stopLocationTracerService(){
		stopService(new Intent(JourneyMainActivity.this, LocationTrackerService.class));
		
	}
	
	public void tracerManagement(){
		
		if(tracerManagerButton.isChecked()){
			Log.i("tracerManagement", "Traceur activé");
			startLocationTracerService();
		}else{
			Log.i("pasDeVoyageLayout", "Traceur désactivé");
			stopLocationTracerService();
		}	
	}
	
	public void actionBoutonTermineVoyage(View v) {
		pasDeVoyageLayout();
		myDB.open();
		myDB.terminerVoyage(voyageEnCours.getId());
		myDB.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
