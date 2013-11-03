package fr.m2ihm.journey.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Voyage;
import fr.m2ihm.journey.services.LocationTrackerService;
import fr.m2ihm.journey.settings.Settings;
import fr.m2ihm.journey.test.TestBD;


public class JourneyMainActivity extends Activity {

	public static final String PREFS_NAME = "MyPrefsFile";
	private TextView textVoyageEnCours;
	private Voyage voyageEnCours;
	MyBDAdapter myDB;
	Button traceurButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myDB = new MyBDAdapterImpl(this);
		
		loadSettings();
		TestBD.testBD3(this);
		init();
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
	}
	public void loadSettings(){
	       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	       Settings.setTraceurActive(settings.getBoolean("traceurMode", false));
	       Settings.setDelayTraceur(settings.getInt("delayTracer", 1000));
	       Settings.setDistanceTraceur(settings.getInt("distanceTracer", 0));
	       Log.v("settings","active : "+Settings.isTraceurActive());
	       Log.v("settings","delay " + Settings.getDelayTraceur());
	       Log.v("settings","distance " + Settings.getDistanceTraceur());
	}
	
	public void saveSettings(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putBoolean("traceurMode", Settings.isTraceurActive());
	    editor.putInt("delayTracer", Settings.getDelayTraceur());
	    editor.putInt("distanceTracer", Settings.getDistanceTraceur());
	    editor.commit();
	}
	
	public void enVoyageLayout() {	
		setContentView(R.layout.acceuil2);
		myDB.open();
		voyageEnCours = myDB.getVoyageCourant();
		myDB.close();
		traceurButton = (Button) findViewById(R.id.traceur);
		textVoyageEnCours = (TextView) findViewById(R.id.voyageEnCoursText);
		textVoyageEnCours.setText(voyageEnCours.getNom());
		if(Settings.isTraceurActive()){
			activeButtonTracerLayout();
			
		}else{
			desactiveButtonTracerLayout();
		}
		
	}

	public void pasDeVoyageLayout() {
		setContentView(R.layout.acceuil);
		Log.v("pasDeVoyageLayout", "pasDeVoyageLayout");
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
		nouveauVoyageDialogue.setPositiveButton("C'est parti !",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						EditText champNouveauVoyage = (EditText) alertDialogView.findViewById(R.id.champNouveauVoyage);
						String nomVoyage = champNouveauVoyage.getText().toString();
						if (!nomVoyage.equals("")) {
							myDB.open();
							myDB.ajouterVoyage(nomVoyage);
							myDB.close();
							Toast message = Toast.makeText(getApplicationContext(),"Vous �tes maintenant en voyage � : " + nomVoyage, Toast.LENGTH_LONG);
							message.setGravity(1, 0, 200);
							message.show();
						} else {
							Toast message = Toast.makeText(getApplicationContext(),"Vous n'avez pas entr� de nom de voyage. Veuillez recommencer", Toast.LENGTH_LONG);
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
		Intent monService = new Intent(JourneyMainActivity.this, LocationTrackerService.class);
		startService(monService);	
	}
	
	public void stopLocationTracerService(){
		stopService(new Intent(JourneyMainActivity.this, LocationTrackerService.class));
	}
	
	public void clickOnTraceurButton(View c){
		if(Settings.isTraceurActive()){
			desactiveButtonTracerLayout();
			stopLocationTracerService();
			Settings.setTraceurActive(false);
		}else{
			activeButtonTracerLayout();
			startLocationTracerService();
			Settings.setTraceurActive(true);
		}
		saveSettings();
	}
	public void activeButtonTracerLayout(){
		traceurButton.setText("Traceur activ�");
		traceurButton.setTextColor(Color.BLUE);
	}
	public void desactiveButtonTracerLayout(){
		traceurButton.setText("Traceur d�sactiv�");
		traceurButton.setTextColor(Color.RED);
	}
	public void actionBoutonTermineVoyage(View v) {
		pasDeVoyageLayout();
		myDB.open();
		myDB.terminerVoyage(voyageEnCours.getId());
		myDB.close();
		stopLocationTracerService();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
