package fr.m2ihm.journey.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
		init();
	}

	public void init() {
		myDB.open();
		voyageEnCours = myDB.getVoyageCourant();
		myDB.close();
		//On regarde si il y a un voyage en cours pour pouvoir adapter le layout de la page d'acceuil
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
	
	//Permet de de r�cup�rer les settings du local storage
	public void loadSettings(){
	       SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	       Settings.setTraceurActive(settings.getBoolean("traceurMode", false));
	       Settings.setDelayTraceur(settings.getInt("delayTracer", 1000));
	       Settings.setDistanceTraceur(settings.getInt("distanceTracer", 0));
	}
	
	//Permet de sauvegarder les settings en local storage
	public void saveSettings(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putBoolean("traceurMode", Settings.isTraceurActive());
	    editor.putInt("delayTracer", Settings.getDelayTraceur());
	    editor.putInt("distanceTracer", Settings.getDistanceTraceur());
		editor.commit();

	}
	
	//Layout si il y a un voyage en cours
	public void enVoyageLayout() {	
		setContentView(R.layout.acceuil2);
		myDB.open();
		voyageEnCours = myDB.getVoyageCourant();
		myDB.close();
		traceurButton = (Button) findViewById(R.id.traceur);
		textVoyageEnCours = (TextView) findViewById(R.id.voyageEnCoursText);
		textVoyageEnCours.setText(voyageEnCours.getNom());
		if(Settings.isTraceurActive()){ //On regarde si le service qui trace le gps est actif pour adapt� l'interface
			activeButtonTracerLayout();
		}else{
			desactiveButtonTracerLayout();
		}
	}

	//layout si il n'y a pas de voyage
	public void pasDeVoyageLayout() {
		setContentView(R.layout.acceuil);
	}


	// Quand on clique sur ajouter un nouveau voyage
	public void actionBoutonAjoutVoyage(View v) {
		// champNouveauVoyage.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

		LayoutInflater factory = LayoutInflater.from(this);
		final View alertDialogView = factory.inflate(
				R.layout.formulaire_nouveau_voyage, null);
		//On ouvre un fenetre de diaglogue pour demander le nom du voyage
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
		
		myDB.open();
		int nbVoyage = myDB.getAllVoyages().size(); 
		myDB.close();
		if (nbVoyage == 0){
			// Si il n'y a pas encore de voyage on ne peut pas ouvrir le carnet de voyage
			Toast messageAcceuil = Toast
					.makeText(
							JourneyMainActivity.this,
							"Vous n'avez pas encore de voyage.",
							Toast.LENGTH_LONG);
			messageAcceuil.setGravity(0, 0, 200);
			messageAcceuil.show();
		}else{
		Intent intent = new Intent(this, JournalActivity.class);
		startActivity(intent);
		finish();
		}
	}

	public void actionBoutonSettings(View v) {
		LayoutInflater factory = LayoutInflater.from(this);
		final View alertDialogView = factory.inflate(
				R.layout.settings_gps, null);
		AlertDialog.Builder nouveauVoyageDialogue = new AlertDialog.Builder(
				JourneyMainActivity.this);
		nouveauVoyageDialogue.setView(alertDialogView);
		nouveauVoyageDialogue.setTitle("Settings");
		nouveauVoyageDialogue.setPositiveButton("Enregistrer",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						EditText champDelai = (EditText)  alertDialogView.findViewById(R.id.delaiEditText); 
						EditText champDistance = (EditText)  alertDialogView.findViewById(R.id.distanceEditText);
						String delai = champDelai.getText().toString();
						String distance = champDistance.getText().toString();
						//Si l'utilisateur n'entre pas de valeur dans les champs, on set des valeurs pas defaut.
						if (delai.equals("")) {
							delai = "5";
						} 
						if (distance.equals("")){
							distance = "5";
						}
						Settings.setDelayTraceur(Integer.parseInt(delai) * 1000); // *1000 pour avoir le delai en secondes
						Settings.setDelayTraceur(Integer.parseInt(distance));
						saveSettings();
					}
				});
		nouveauVoyageDialogue.setNegativeButton("Annuler",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		nouveauVoyageDialogue.show();
	}
	
	public void startLocationTracerService(){
		Intent monService = new Intent(JourneyMainActivity.this, LocationTrackerService.class);
		startService(monService);	
	}
	
	public void stopLocationTracerService(){
		stopService(new Intent(JourneyMainActivity.this, LocationTrackerService.class));
	}
	
	//Fonction quand on appuie sur le bouton qui g�re l'activation et d�sactivation du service de localisation gps
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
	
	//Quand on appuie sur terminer voyage
	public void actionBoutonTermineVoyage(View v) {
		pasDeVoyageLayout();
		myDB.open();
		myDB.terminerVoyage(voyageEnCours.getId()); // on sauvegarde dans la BD
		myDB.close();
		stopLocationTracerService(); // On arr�te le service de localisation gps
		Settings.setTraceurActive(false); // On change les setting pour pr�cis� que il n'y a plus de localisation
		saveSettings();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
