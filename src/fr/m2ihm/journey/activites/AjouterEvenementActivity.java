package fr.m2ihm.journey.activites;

import java.io.File;
import java.text.SimpleDateFormat;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class AjouterEvenementActivity extends Activity {
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

	private Uri fileUri;

	Gps gps;
	EditText lieu;
	EditText commentaire;
	TextView indicateurMedia;
	String nomMedia;
	Voyage voyageCourant;
	Intent intent;
	Date date;

	boolean ajoutMedia;

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
		if (intent.hasExtra("nomMedia") && intent.hasExtra("typeMedia")) {
			initWithMedia();
		}
	}

	public void init() {
		ajoutMedia = false;
		nomMedia = "";
	}

	public void initWithMedia() {
		String typeMedia = intent.getExtras().getString("typeMedia");
		ajoutMedia = true;
		lieu.setText(intent.getExtras().getString("lieu"));
		commentaire.setText(intent.getExtras().getString("commentaire"));
		nomMedia = intent.getExtras().getString("nomMedia");
		if (typeMedia.equals("photo")) {
			addPhoto();
		} else if (typeMedia.equals("video")) {
			addVideo();
		} else if (typeMedia.equals("audio")) {
			addVideo();
		}
		Log.v("photoAddPhoto AjouterEvenement", "" + nomMedia);
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
				newEvent = new Note(voyageCourant, GpsAdapter.getCurrentGps(),
						Date.dateCourant(), lieu.getText().toString(),
						commentaire.getText().toString());
			}
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

	public void prendrePhoto(View v) {
		// create Intent to take a picture and return control to the calling
		// application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);// start the image capture Intent
	}
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else if(type == MEDIA_TYPE_VIDEO) {
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "VID_"+ timeStamp + ".mp4");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}
	public void prendreVideo(View v) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO); // create a file to
															// save the video
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
															// name

		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high
		// start the Video Capture Intent
		startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
	}

	public void prendreSon(View v) {
	}

	public void photoAddPhoto() {
		newEvent = new Photo(voyageCourant, GpsAdapter.getCurrentGps(),
				Date.dateCourant(), nomMedia, lieu.getText().toString(),
				commentaire.getText().toString());
		ajoutMedia = true;
	}

	public void addPhoto() {
		newEvent = new Photo(voyageCourant, GpsAdapter.getCurrentGps(),
				Date.dateCourant(), nomMedia, lieu.getText().toString(),
				commentaire.getText().toString());
		ajoutMedia = true;
		indicateurMedia.setText("Vous avez ajouté une photo");
	}

	public void addVideo() {
		newEvent = new Video(voyageCourant, GpsAdapter.getCurrentGps(),
				Date.dateCourant(), nomMedia, lieu.getText().toString(),
				commentaire.getText().toString());
		ajoutMedia = true;
		indicateurMedia.setText("Vous avez ajouté une video");
	}

	public void addSon() {
		newEvent = new Son(voyageCourant, GpsAdapter.getCurrentGps(),
				Date.dateCourant(), nomMedia, lieu.getText().toString(),
				commentaire.getText().toString());
		ajoutMedia = true;
		indicateurMedia.setText("Vous avez ajouté un son");
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Toast.makeText(this, "Image saved to:\n" +
	                     data.getData(), Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }

	    if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Video captured and saved to fileUri specified in the Intent
	            Toast.makeText(this, "Video saved to:\n" +
	                     data.getData(), Toast.LENGTH_LONG).show();
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the video capture
	        } else {
	            // Video capture failed, advise user
	        }
	    }
	}
	
}
