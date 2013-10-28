package fr.m2ihm.journey.activites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.m2ihm.journey.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.journal_de_voyage);
		Button currentButton = (Button) findViewById(R.id.Journal_map_button);
		currentButton.setEnabled(false);
		HashMap<String, String> map;
		ListView maListViewPerso = (ListView) findViewById(R.id.journal_journeys_list);
		List<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		map = new HashMap<String, String>();
		map.put("Nom_Voyage","Acapulco");
		map.put("Date", "11/03/2012");
		listItem.add(map);
		map = new HashMap<String, String>();
		map.put("Nom_Voyage","Paris");
		map.put("Date", "11/08/2012");
		listItem.add(map);
		map = new HashMap<String, String>();
		map.put("Nom_Voyage","Paris");
		map.put("Date", "11/08/2012");
		listItem.add(map);
		map = new HashMap<String, String>();
		map.put("Nom_Voyage","Paris");
		map.put("Date", "11/08/2012");
		listItem.add(map);
		map = new HashMap<String, String>();
		map.put("Nom_Voyage","Paris");
		map.put("Date", "11/08/2012");
		listItem.add(map);
		
		map = new HashMap<String, String>();
		map.put("Nom_Voyage","Paris");
		map.put("Date", "11/08/2012");
		listItem.add(map);
		
		map = new HashMap<String, String>();
		map.put("Nom_Voyage","Paris");
		map.put("Date", "11/08/2012");
		listItem.add(map);
		SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.journal_liste_voyages,
	               new String[] {"Nom_Voyage", "Date"}, new int[] {R.id.nom_voyage, R.id.date});
		maListViewPerso.setAdapter(mSchedule);
		// Add the map
		Fragment fg = new JournalMapFragment();
		getFragmentManager().beginTransaction().add(R.id.journal_content, fg).commit();
		Log.v("JOURNAL", "13");
		currentFragmentName = OurFragments.map;
		Log.v("JOURNAL", "14");
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
