package fr.m2ihm.journey.activites;

import java.util.ArrayList;
import java.util.Collections;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Photo;
import fr.m2ihm.journey.metier.Voyage;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class Fullscreen_Activity extends Activity {

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullscreen);

		Log.v("FullSCreen - On Create", "currentVoyageID ? "
				+ savedInstanceState.get("currentVoyageId"));
		Log.v("FullSCreen - On Create", "currentVoyageID ? "
				+ savedInstanceState.get("photoPosition"));
		
		int currentVoyageId = (Integer) savedInstanceState.get("currentVoyageId");
		int photoPositionOnList = (Integer) savedInstanceState.get("photoPosition");

		// We get the list of Photos
		// We get data from DB
		MyBDAdapter bdAdapter = new MyBDAdapterImpl(this);

		bdAdapter.open();
		ArrayList<Photo> photoList = bdAdapter.getAllPhoto(currentVoyageId);
		bdAdapter.close();

		Collections.sort(photoList);
		
		ImageView centralPicture = (ImageView) findViewById(R.id.fullscreen_centralPicture);
		centralPicture.setImageURI(Uri.parse(photoList.get(photoPositionOnList).getNomMedia()));

		// Bundle bundle = this.getIntent().getExtras();
	}
}
