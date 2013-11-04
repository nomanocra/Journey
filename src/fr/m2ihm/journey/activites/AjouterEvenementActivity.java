package fr.m2ihm.journey.activites;

import java.io.File;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
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

public class AjouterEvenementActivity extends Activity {
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	String filePath;
	private Uri fileUri;

	EditText lieu;
	EditText commentaire;
	TextView indicateurMedia;
	Voyage voyageCourant;
	Intent intent;
	Date date;
	Gps positionElement;
	boolean ajoutMedia;
	Button ajouterElementMap;
	ProgressBar progressBar;

	ElementMap newEvent;
	MyBDAdapter myDB;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulaire_nouveau_evement);
		//this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
		intent = getIntent();
		myDB = new MyBDAdapterImpl(this);
		commentaire = (EditText) findViewById(R.id.champCommentaireEvenement);
		lieu = (EditText) findViewById(R.id.champLieuEvenement);
		indicateurMedia = (TextView) findViewById(R.id.indicateurMedia);
		ajouterElementMap = (Button) findViewById(R.id.retourMenuPrincipal);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		myDB.open();
		voyageCourant = myDB.getVoyageCourant();
		myDB.close();
		positionElement = new Gps(0, 0);
		ajouterElementMap.setEnabled(false);
		
		progressBar.animate();
		LocationManager objgps = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener objlistener = new Myobjlistener(this);
		objgps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000000, 1000000,
				objlistener);
	}

	public void saveNewEvent(View v) {
		if (commentaire.getText().toString().equals("") && ajoutMedia == false) {
			Toast messageAcceuil = Toast
					.makeText(
							AjouterEvenementActivity.this,
							"Vous devez au moins mettre un commentaire ou ajouter un media !",
							Toast.LENGTH_LONG);
			messageAcceuil.setGravity(0, 0, 0);
			messageAcceuil.show();
		} else {
			if (ajoutMedia == false) {
				newEvent = new Note(voyageCourant, positionElement,
						Date.dateCourant(), lieu.getText().toString(),
						commentaire.getText().toString());
			}
			newEvent.setGps(positionElement);
			myDB.open();
			myDB.ajouterElementMap(newEvent);
			myDB.close();
			closeActivity(v);
		}
	}

	public void closeActivity(View v) {
		Intent intent = new Intent(this, JourneyMainActivity.class);
		startActivity(intent);
		finish();
	}

	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static String getOutputMediaFilePath(int type) {
		File media = getOutputMediaFile(type);
		String path = media.getAbsolutePath();
		return path;
	}

	
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
				"Camera");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new java.util.Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir + File.separator + "IMG_"
					+ timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir + File.separator + "VID_"
					+ timeStamp + ".mp4");
		} else {
			return null;
		}
		return mediaFile;

	}
	public void prendreSon(View v) {
	}

	public void addPhoto() {

		newEvent = new Photo(voyageCourant, positionElement,
				Date.dateCourant(), filePath, lieu.getText().toString(),
				commentaire.getText().toString());
		ajoutMedia = true;
		indicateurMedia.setText("Vous avez ajouté une photo");
	}

	public void addVideo() {

		newEvent = new Video(voyageCourant, positionElement,
				Date.dateCourant(), filePath, lieu.getText().toString(),
				commentaire.getText().toString());
		ajoutMedia = true;
		indicateurMedia.setText("Vous avez ajouté une video");
	}

	public void addSon() {
		newEvent = new Son(voyageCourant, GpsAdapter.getCurrentGps(),
				Date.dateCourant(), filePath, lieu.getText().toString(),
				commentaire.getText().toString());
		ajoutMedia = true;
		indicateurMedia.setText("Vous avez ajouté un son");
	}

	public void prendreVideo(View v) {
		// create Intent to take a picture and return control to the calling
		// application
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO); // create a file to
															// save the video
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
															// name

		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video
															// image quality to
															// high

		// start the Video Capture Intent
		startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

		filePath = getOutputMediaFilePath(MEDIA_TYPE_VIDEO);// Intent
	}

	public void prendrePhoto(View v) {
		// create Intent to take a picture and return control to the calling
		// application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to
															// save the image
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
															// name

		// start the image capture Intent
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		filePath = getOutputMediaFilePath(MEDIA_TYPE_IMAGE);// Intent
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				addPhoto();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}

		if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Video captured and saved to fileUri specified in the Intent
				addVideo();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the video capture
			} else {
				// Video capture failed, advise user
			}
		}
	}

	private class Myobjlistener implements LocationListener {
		private Context c;

		public Myobjlistener(Context c) {
			this.c = c;
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}

		public void onLocationChanged(Location location) {

			// affichage des valeurs dans la les zone de saisie
			
			positionElement = new Gps(location.getLatitude(),
					location.getLongitude());
			lieu.setText(GpsAdapter.gpsToAdresse(positionElement, c));
			lieu.setTextColor(Color.BLACK);
			progressBar.setVisibility(View.INVISIBLE);
			ajouterElementMap.setEnabled(true);	
		}
	}
	public void onBackPressed() {
		Intent intent = new Intent(this, JourneyMainActivity.class);
		startActivity(intent);
		finish();
		}
}
