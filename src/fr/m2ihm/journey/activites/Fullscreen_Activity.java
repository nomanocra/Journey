package fr.m2ihm.journey.activites;

import java.util.ArrayList;
import java.util.Collections;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.Photo;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Fullscreen_Activity extends Activity {

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.fullscreen);
		
		intent = getIntent();
		
		int currentVoyageId = Integer.parseInt(intent.getExtras().getString("currentVoyageId"));
		int photoPositionOnList = Integer.parseInt(intent.getExtras().getString("photoPosition"));

		// We get the list of Photos
		// We get data from DB
		MyBDAdapter bdAdapter = new MyBDAdapterImpl(this);

		bdAdapter.open();
		ArrayList<Photo> photoList = bdAdapter.getAllPhoto(currentVoyageId);
		bdAdapter.close();

		Collections.sort(photoList);
		
		// Then we fill content
		
		Photo currentPhoto = photoList.get(photoPositionOnList);
		
		ImageView centralPicture = (ImageView) findViewById(R.id.fullscreen_centralPicture);
		centralPicture.setImageURI(Uri.parse(currentPhoto.getNomMedia()));
		
		TextView lieu = (TextView) findViewById(R.id.fullscreen_lieu);
		lieu.setText(currentPhoto.getLieu());
		
		Date currentDate = currentPhoto.getDate();
		
		TextView date = (TextView) findViewById(R.id.fullscreen_date_heure);
		date.setText(currentDate.toString());
	
		TextView heure = (TextView) findViewById(R.id.fullscreen_heure);
		heure.setText(currentDate.getHour() + "h" + currentDate.getMinute());
		
		TextView commentaire = (TextView) findViewById(R.id.fullscreen_commentaire);
		commentaire.setText(currentPhoto.getCommentaire());
	}
	
	/**
	 * Handles the return button
	 * @param v
	 */
	public void returnbutton_click(View v)
	{
		Intent intent = new Intent(this, JournalActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public void onBackPressed()
	{
		Intent intent = new Intent(this, JournalActivity.class);
		startActivity(intent);
		finish();
	}
}
