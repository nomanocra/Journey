package fr.m2ihm.journey.activites;

import java.util.Collections;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.ListeVoyagesAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Voyage;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class JournalActivity extends Activity {

	public enum OurFragments {
		map, album, list, stats;
	}

	private OurFragments currentFragmentName;
	private JournalMapFragment mapFragment;
	private JournalAlbumFragment albumFragment;
	private JournalListFragment listFragment;
	private JournalStatsFragment statsFragment;
	
	private int selectedVoyageId;
	private boolean listChecked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.journal_de_voyage);

		this.listChecked = false;
		
		/* We deactivate the map button */

		Button currentButton = (Button) findViewById(R.id.Journal_map_button);
		currentButton.setEnabled(false);

		/* We initialize the list */

		this.listInitialization();

		/*
		 * No Voyage is selected by default, so settings and delete buttons have
		 * to be disabled
		 */
		ImageButton deleteButton = (ImageButton) findViewById(R.id.journal_close_button);
		deleteButton.setEnabled(false);

		ImageButton settingsButton = (ImageButton) findViewById(R.id.journal_settings_button);
		settingsButton.setEnabled(false);

		/* Then we add the map fragment */

		this.mapFragment = new JournalMapFragment();
		getFragmentManager().beginTransaction()
				.add(R.id.journal_content, this.mapFragment).commit();
		Log.v("JOURNAL", "13");
		currentFragmentName = OurFragments.map;
		Log.v("JOURNAL", "14");
	}

	// public void listeVoyages_Click(View v)
	// {
	// v.setBackgroundColor(Color.WHITE);
	// }

	private void listInitialization() {
		/* We fill the list */

		// TestListeVoyages tlv = new TestListeVoyages();

		MyBDAdapter bdAdapter = new MyBDAdapterImpl(this);

		bdAdapter.open();
		List<Voyage> listeVoyages = bdAdapter.getAllVoyages();
		bdAdapter.close();
		
		/* We invert it - older at the end */
		Collections.reverse(listeVoyages);

		final ListeVoyagesAdapter lvAdapter = new ListeVoyagesAdapter(this.getBaseContext(),
				listeVoyages);

		/**
		 * FOCUS MANAGEMENT
		 */

		ListView listView = (ListView) findViewById(R.id.journal_journeys_list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setSelector(R.drawable.journal_liste_voyages_row_selector);
		listView.setAdapter(lvAdapter);

		OnItemClickListener clickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				/* We unfocus the last focused item (if it exists) */
				ListView listView = (ListView) findViewById(R.id.journal_journeys_list);
				// System.out.println("PRENDS CA !!!! ::" + view.getId());

				JournalActivity context = (JournalActivity) listView
						.getContext();

				context.selectedVoyageId = view.getId();
				context.listChecked = true;

				/* We indicate that the new focused element has changed */
				Log.v("CLICK_LISTENER", "ID Clicked : " + view.getId());

				/* Then we change content */
				switch (context.getCurrentFragmentName()) {
				case map:
					context.getMapFragment().fillMap();
					break;

				case album:
					context.getAlbumFragment().fillAlbum();
					break;

				case list:
					// TODO
					break;

				case stats:
					// TODO
					break;

				default:
					// throw new Exception("Switch case error");
				}

				/* We activate all buttons needed */
				ImageButton deleteButton = (ImageButton) findViewById(R.id.journal_close_button);
				deleteButton.setEnabled(true);

				ImageButton settingsButton = (ImageButton) findViewById(R.id.journal_settings_button);
				settingsButton.setEnabled(true);

				deleteButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {

						final JournalActivity context = (JournalActivity) v.getContext();
						final ListeVoyagesAdapter lvAdapterAgain = lvAdapter;
						
						// Use the Builder class for convenient dialog construction
				        AlertDialog.Builder builder = new AlertDialog.Builder(context);
				        builder.setMessage("Voulez-vous vraiment supprimer ce voyage ?")
				               .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
				                   public void onClick(DialogInterface dialog, int id) {				                	   
										
										// Delete from DB
										MyBDAdapterImpl myDB = new MyBDAdapterImpl(context);
										myDB.open();
										myDB.supprimerVoyage(context.selectedVoyageId);
										myDB.close();

										// Delete from view

										lvAdapterAgain
												.removeCurrentFromView(
														(ListView) findViewById(R.id.journal_journeys_list),
														context.selectedVoyageId);
										
										/* Then we change content */
										switch (context.getCurrentFragmentName()) {
										case map:
											GoogleMap googleMap = ((MapFragment) getFragmentManager()
													.findFragmentById(R.id.map)).getMap();
											googleMap.clear();
											break;

										case album:
											// TODO
											break;

										case list:
											// TODO
											break;

										case stats:
											// TODO
											break;

										default:
											// throw new Exception("Switch case error");
										}
				                   }
				               })
				               .setNegativeButton("Non", new DialogInterface.OnClickListener() {
				                   public void onClick(DialogInterface dialog, int id) {
				                       // User cancelled the dialog
				                   }
				               });
				        builder.show();
						
						
					}
				});

			}
		};

		listView.setOnItemClickListener(clickListener);

		/**
		 * END OF FOCUS MANAGEMENT
		 */

	}

	// ###################################
	// GETTERS
	// ###################################

	public OurFragments getCurrentFragmentName() {
		return this.currentFragmentName;
	}

	public JournalMapFragment getMapFragment() {
		return this.mapFragment;
	}

	public JournalAlbumFragment getAlbumFragment() {
		return this.albumFragment;
	}

	public JournalStatsFragment getStatsFragment() {
		return this.statsFragment;
	}

	public JournalListFragment getListFragment() {
		return this.listFragment;
	}
	
	public int getSelectedVoyageId()
	{
		return this.selectedVoyageId;
	}

	// ###################################
	// END GETTERS
	// ###################################

	// ###################################
	// HANDLERS
	// ###################################

	public void albumButton_Click(View v) {

		JournalAlbumFragment fg = new JournalAlbumFragment();

		// We store the fragment to adapt the behaviour when changing the
		// current Voyage
		this.albumFragment = fg;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.journal_content, fg);
		transaction.addToBackStack(null);
		transaction.commit();

		// Buttons renderer
		this.activateExCurrentButton();

		Button currentButton = (Button) findViewById(R.id.Journal_album_button);
		currentButton.setEnabled(false);

		currentFragmentName = OurFragments.album;
		
		if(this.listChecked)
		{
			fg.fillAlbum();
		}
	}

	public void mapButton_Click(View v) {
		JournalMapFragment fg = new JournalMapFragment();

		// We store the fragment to adapt the behaviour when changing the
		// current Voyage
		this.mapFragment = fg;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.journal_content, fg);
		transaction.addToBackStack(null);
		transaction.commit();

		// Buttons renderer
		Button currentButton = (Button) findViewById(R.id.Journal_album_button);
		currentButton.setEnabled(true);

		currentButton = (Button) findViewById(R.id.Journal_map_button);
		currentButton.setEnabled(false);

		currentFragmentName = OurFragments.map;
	}

	public void listButton_Click(View v) {
		JournalListFragment fg = new JournalListFragment();

		// We store the fragment to adapt the behaviour when changing the
		// current Voyage
		this.listFragment = fg;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.journal_content, fg);
		transaction.addToBackStack(null);
		transaction.commit();

		// Buttons renderer
		this.activateExCurrentButton();

		Button currentButton = (Button) findViewById(R.id.Journal_list_button);
		currentButton.setEnabled(false);

		currentFragmentName = OurFragments.list;
	}

	public void statsButton_Click(View v) {
		JournalStatsFragment fg = new JournalStatsFragment();

		// We store the fragment to adapt the behaviour when changing the
		// current Voyage
		this.statsFragment = fg;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.journal_content, fg);
		transaction.addToBackStack(null);
		transaction.commit();

		// Buttons renderer
		this.activateExCurrentButton();

		Button currentButton = (Button) findViewById(R.id.Journal_stats_button);
		currentButton.setEnabled(false);

		currentFragmentName = OurFragments.stats;
	}
	
	public void returnButton_Click(View v) {
		Intent intent = new Intent(this, JourneyMainActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, JourneyMainActivity.class);
		startActivity(intent);
		finish();
	}

	// ###################################
	// END HANDLERS
	// ###################################

	private void activateExCurrentButton() {
		Button currentButton;

		switch (currentFragmentName) {
		case map:
			currentButton = (Button) findViewById(R.id.Journal_map_button);
			currentButton.setEnabled(true);
			break;

		case album:
			currentButton = (Button) findViewById(R.id.Journal_album_button);
			currentButton.setEnabled(true);
			break;

		case list:
			currentButton = (Button) findViewById(R.id.Journal_list_button);
			currentButton.setEnabled(true);
			break;

		case stats:
			currentButton = (Button) findViewById(R.id.Journal_stats_button);
			currentButton.setEnabled(true);
			break;

		default:
			// throw new Exception("Switch case error");
		}
	}

}
