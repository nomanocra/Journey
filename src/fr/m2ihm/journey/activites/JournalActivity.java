package fr.m2ihm.journey.activites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.ListeVoyagesAdapter;
import fr.m2ihm.journey.metier.Voyage;
import fr.m2ihm.journey.test.TestListeVoyages;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class JournalActivity extends Activity {

	private enum OurFragments {
		  map,
		  album,
		  list,
		  stats;  
		}
	
	private OurFragments currentFragmentName;
	private View lastViewFocusedOnList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.journal_de_voyage);
		
		/* We deactivate the map button */
		
		Button currentButton = (Button) findViewById(R.id.Journal_map_button);
		currentButton.setEnabled(false);
		
		/* We initialize the list */
		
		this.listInitialization();
		
		/* Then we add the map fragment */
		
		Fragment fg = new JournalMapFragment();
		getFragmentManager().beginTransaction().add(R.id.journal_content, fg).commit();
		Log.v("JOURNAL", "13");
		currentFragmentName = OurFragments.map;
		Log.v("JOURNAL", "14");
	}
	
//	public void listeVoyages_Click(View v)
//	{
//		v.setBackgroundColor(Color.WHITE);
//	}
	
	private void listInitialization()
	{
		/* We fill the list */
		
		TestListeVoyages tlv = new TestListeVoyages();
		
		/* We sort the list */
		Collections.sort(tlv.listeVoyages);

		/* Then we transform the list in table (while inverting it to print purposes) */
		
		Voyage[] values = new Voyage[tlv.listeVoyages.size()];
		for(int i = 0; i < tlv.listeVoyages.size() ; i++)
		{
			values[i] = tlv.listeVoyages.get(tlv.listeVoyages.size() - i - 1);
		}
		
		ListeVoyagesAdapter lvAdapter = new ListeVoyagesAdapter(this.getBaseContext(), values);
		
		/**
		 * FOCUS MANAGEMENT
		 */
		
		ListView listView = (ListView) findViewById(R.id.journal_journeys_list);
		listView.setAdapter(lvAdapter);

		OnItemClickListener clickListener = new OnItemClickListener() {
			@Override
			 public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
				
				/* We unfocus the last focused item (if it exists) */
	
				
				/* We set the new Color */
				view.setBackgroundColor(Color.GRAY);
				
				/* TODO We get all data from DB for the new Voyage */
				
				/* We delete old markers */
				
				/* We redraw all associated markers */
			 }
		};
		
		listView.setOnItemClickListener(clickListener);
		
		/**
		 * END OF FOCUS MANAGEMENT
		 */
	}
	
	public void albumButton_Click(View v) {
		
		Fragment fg = new JournalAlbumFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		transaction.replace(R.id.journal_content, fg);
		transaction.addToBackStack(null);
		transaction.commit();
		
		// Buttons renderer
		this.activateExCurrentButton();
		
		Button currentButton = (Button) findViewById(R.id.Journal_album_button);
		currentButton.setEnabled(false);
		
		currentFragmentName = OurFragments.album;
	}
	
	public void mapButton_Click(View v) {
		Fragment fg = new JournalMapFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
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
		Fragment fg = new JournalListFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
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
		Fragment fg = new JournalStatsFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		transaction.replace(R.id.journal_content, fg);
		transaction.addToBackStack(null);
		transaction.commit();
		
		// Buttons renderer
		this.activateExCurrentButton();
		
		Button currentButton = (Button) findViewById(R.id.Journal_stats_button);
		currentButton.setEnabled(false);
		
		currentFragmentName = OurFragments.stats;
	}
	
	private void activateExCurrentButton()
	{
		Button currentButton;
		
		switch (currentFragmentName)
		{
		case map :
			currentButton = (Button) findViewById(R.id.Journal_map_button);
			currentButton.setEnabled(true);
			break;
			
		case album :
			currentButton = (Button) findViewById(R.id.Journal_album_button);
			currentButton.setEnabled(true);
			break;
		
		case list :
			currentButton = (Button) findViewById(R.id.Journal_list_button);
			currentButton.setEnabled(true);
			break;
			
		case stats:
			currentButton = (Button) findViewById(R.id.Journal_stats_button);
			currentButton.setEnabled(true);
			break;
			
		default :
//			throw new Exception("Switch case error");
		}
	}
	
}
