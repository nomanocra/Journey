package fr.m2ihm.journey.activites;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.GpsAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.metier.Gps;
import fr.m2ihm.journey.metier.Note;
import fr.m2ihm.journey.metier.Photo;
import fr.m2ihm.journey.metier.Son;
import fr.m2ihm.journey.metier.Video;
import fr.m2ihm.journey.metier.Voyage;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;



public class AjouterEvenementActivity extends Activity {
	Gps gps;
	EditText lieu;
	EditText commentaire;
	TextView indicateurMedia;
	String nomMedia;
	Voyage voyageCourant;
	Intent intent;
	Date date;

	boolean ajoutMedia ;
	
	ElementMap newEvent;
	MyBDAdapter myDB;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulaire_nouveau_evement);
		intent = getIntent();
		myDB = new MyBDAdapterImpl(this);
		commentaire = (EditText) findViewById(R.id.champCommentaireEvenement);
		lieu = (EditText) findViewById(R.id.champLieuEvenement);
		indicateurMedia = (TextView) findViewById(R.id.indicateurMedia);
		myDB.open();
		voyageCourant = myDB.getVoyageCourant();
		myDB.close();
		
		 intent = getIntent();
		 Log.v("onCreate - AjoutEvenement" , ""+ (intent.hasExtra("nomMedia") && intent.hasExtra("typeMedia")));
		  if(intent.hasExtra("nomMedia") && intent.hasExtra("typeMedia")){
			  initWithMedia();
		  }
		}
	
	public void init(){
		ajoutMedia = false;
	}
	
	public void initWithMedia(){
		String typeMedia = intent.getExtras().getString("typeMedia");
		ajoutMedia = true;
		  lieu.setText(intent.getExtras().getString("lieu"));
		  commentaire.setText(intent.getExtras().getString("commentaire"));
		  nomMedia = intent.getExtras().getString("nomMedia");
		  if(typeMedia.equals("photo")){
			  addPhoto();
		  }else if(typeMedia.equals("video")){
			  addVideo();
		  }
		  else if(typeMedia.equals("audio")){
			  addVideo();
		  }
	}

	
	public void saveNewEvent(View v) {
		if( commentaire.getText().toString().equals("")&& ajoutMedia==false ){
			Toast messageAcceuil = Toast
					.makeText(
							AjouterEvenementActivity.this,
							"Vous devez au moins mettre un commentaire ou ajouter un media !",
							Toast.LENGTH_LONG);
			messageAcceuil.setGravity(0, 0,0);
			messageAcceuil.show();
		}else{
		if(ajoutMedia==false){
			Log.v("saveNewEvent", voyageCourant.getNom());
			newEvent = new Note(voyageCourant, GpsAdapter.getCurrentGps(), Date.dateCourant(), lieu.getText().toString(), commentaire.getText().toString());
		}
		myDB.open();
		myDB.ajouterElementMap(newEvent);
		myDB.close();
		closeActivity(v);
		}	
	}
	
	public void closeActivity(View v){
		Intent intent = new Intent(this, JourneyMainActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void prendrePhoto(View v){
		Intent intent = new Intent(this, ActiveCameraActivity.class);
		intent.putExtra("lieu", lieu.getText().toString());
		intent.putExtra("commentaire", commentaire.getText().toString());
		startActivity(intent);
		finish();
	}
	public void prendreVideo(View v){
	}
	public void prendreSon(View v){
	}
	public void photoAddPhoto(){
		newEvent = new Photo(voyageCourant, GpsAdapter.getCurrentGps(), Date.dateCourant(), nomMedia, lieu.getText().toString(), commentaire.getText().toString());
		ajoutMedia = true;
	}
	
	public void addPhoto(){
		newEvent = new Photo(voyageCourant, GpsAdapter.getCurrentGps(), Date.dateCourant(), nomMedia, lieu.getText().toString(), commentaire.getText().toString());
		ajoutMedia = true;
		indicateurMedia.setText("Vous avez ajouté une photo");
	}
	
	public void addVideo(){
		newEvent = new Video(voyageCourant, GpsAdapter.getCurrentGps(), Date.dateCourant(), nomMedia, lieu.getText().toString(), commentaire.getText().toString());
		ajoutMedia = true;
		indicateurMedia.setText("Vous avez ajouté une video");
	}
	
	public void addSon(){
		newEvent = new Son(voyageCourant, GpsAdapter.getCurrentGps(), Date.dateCourant(), nomMedia, lieu.getText().toString(), commentaire.getText().toString());
		ajoutMedia = true;
		indicateurMedia.setText("Vous avez ajouté un son");
	}
}
