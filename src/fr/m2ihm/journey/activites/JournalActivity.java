package fr.m2ihm.journey.activites;

import java.util.Collections;
import java.util.List;

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
import android.widget.Toast;

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
	
	private ListeVoyagesAdapter lvAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.journal_de_voyage);

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
		
		/* Then we add the map fragment */

		this.mapFragment = new JournalMapFragment();
		getFragmentManager().beginTransaction()
				.add(R.id.journal_content_container, this.mapFragment).commit();

		currentFragmentName = OurFragments.map;

	}

	/**
	 * Initialize the list by filling it and handling click events
	 */
	private void listInitialization() {
		/* We fill the list */

		// TestListeVoyages tlv = new TestListeVoyages();

		MyBDAdapter bdAdapter = new MyBDAdapterImpl(this);

		bdAdapter.open();
		List<Voyage> listeVoyages = bdAdapter.getAllVoyages();
		bdAdapter.close();
		
		/* We invert it - older at the end */
		Collections.reverse(listeVoyages);

		this.lvAdapter = new ListeVoyagesAdapter(this.getBaseContext(),
				listeVoyages);
		final ListeVoyagesAdapter lvAdapter = this.lvAdapter;

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
					context.getListFragment().fillList();
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

				//##################
				// Delete management
				//##################
				
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
											context.mapFragment.clearMap();
											break;

										case album:
											// TODO
											context.albumFragment.clearAlbum();
											break;

										case list:
											//context.albumFragment.clearAlbum();
											break;

										case stats:
											// TODO
											break;

										default:
											// throw new Exception("Switch case error");
										}
										
						                   Toast messageAcceuil = Toast
						       					.makeText(
						       							JournalActivity.this,
						       							"Voyage supprimé !",
						       							Toast.LENGTH_LONG);
						       				messageAcceuil.setGravity(0, 0, 0);
						       				messageAcceuil.show();
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

	/**
	 * Handles the "album" tab click
	 * @param v
	 */
	public void albumButton_Click(View v) {
		
		JournalAlbumFragment fg = new JournalAlbumFragment();

		// We store the fragment to adapt the behaviour when changing the
		// current Voyage
		this.albumFragment = fg;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.journal_content_container, fg);
		transaction.addToBackStack(null);
		transaction.commit();

		// Buttons renderer
		this.activateExCurrentButton();

		Button currentButton = (Button) findViewById(R.id.Journal_album_button);
		currentButton.setEnabled(false);

		currentFragmentName = OurFragments.album;
	}
	
	/**
	 * Handles the map "tab" click
	 * @param v
	 */
	public void mapButton_Click(View v) {
		JournalMapFragment fg = new JournalMapFragment();

		// We store the fragment to adapt the behaviour when changing the
		// current Voyage
		this.mapFragment = fg;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.journal_content_container, fg);
		transaction.addToBackStack(null);
		transaction.commit();

		// Buttons renderer
		this.activateExCurrentButton();

		Button currentButton = (Button) findViewById(R.id.Journal_map_button);
		currentButton.setEnabled(false);

		currentFragmentName = OurFragments.map;
	}

	/**
	 * Handles the map "tab" click
	 * @param v
	 */
	public void listButton_Click(View v) {
		
		/**
		 * TODO
		 */
		/*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Fonction indisponible sur la version d'essai")
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
				}
                   
               });

        builder.show();
		*/
		
		JournalListFragment fg = new JournalListFragment();

		// We store the fragment to adapt the behaviour when changing the
		// current Voyage
		this.listFragment = fg;

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.journal_content_container, fg);
		transaction.addToBackStack(null);
		transaction.commit();

		// Buttons renderer
		this.activateExCurrentButton();

		Button currentButton = (Button) findViewById(R.id.Journal_list_button);
		currentButton.setEnabled(false);

		currentFragmentName = OurFragments.list;

	}

	/**
	 * Handles the map "tab" click
	 * @param v
	 */
	public void statsButton_Click(View v) {
		
		/**
		 * TODO
		 */
		
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Fonction indisponible sur la version d'essai")
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
				}
                   
               });

        builder.show();
		
//		JournalStatsFragment fg = new JournalStatsFragment();
//
//		// We store the fragment to adapt the behaviour when changing the
//		// current Voyage
//		this.statsFragment = fg;
//
//		FragmentTransaction transaction = getFragmentManager()
//				.beginTransaction();
//
//		transaction.replace(R.id.journal_content_container, fg);
//		transaction.addToBackStack(null);
//		transaction.commit();
//
//		// Buttons renderer
//		this.activateExCurrentButton();
//
//		Button currentButton = (Button) findViewById(R.id.Journal_stats_button);
//		currentButton.setEnabled(false);
//
//		currentFragmentName = OurFragments.stats;
	}
	
	/**
	 * Handles the "return" button click
	 * @param v
	 */
	public void returnButton_Click(View v) {
		Intent intent = new Intent(this, JourneyMainActivity.class);
		startActivity(intent);
		finish();
		this.getFragmentManager().beginTransaction().remove(this.mapFragment).commit();
	}
	
	/**
	 * Handles when the default back button of Android is clicked
	 */
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, JourneyMainActivity.class);
		startActivity(intent);
		finish();
		this.getFragmentManager().beginTransaction().remove(this.mapFragment).commit();
	}

	// ###################################
	// END HANDLERS
	// ###################################

	/**
	 * Manage tabs activation/deactivation when we change the current Fragment
	 */
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
