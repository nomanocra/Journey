package fr.m2ihm.journey.activites;

import java.util.Collections;
import java.util.List;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.ListeElementVoyageAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.ElementMap;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class JournalListFragment extends Fragment {

	private ListeElementVoyageAdapter leAdapter;
	private List<ElementMap> listeElement;
	MyBDAdapter bdAdapter;
	private JournalActivity context;
	private int idVoyageSelected;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.context = (JournalActivity) container.getContext();

		View v = inflater.inflate(R.layout.journal_list_elements, null);

		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		this.context = (JournalActivity) this.getActivity();
		this.listView = (ListView) context.findViewById(R.id.listElementVoyage);
		
		// If there is a Voyage selected on the left hand list, we fill the list
		
		if(this.context != null && this.listView != null && this.context.isOneVoyageFocused())
		{
			this.fillList();
		}
	}

	/**
	 * Fill the list with all medias got from the DB
	 */
	public void fillList() {
		if (this.context == null) {
			this.context = (JournalActivity) this.getActivity();
		}

		if (bdAdapter == null) {
			bdAdapter = new MyBDAdapterImpl(this.getActivity());
		}

		if (listView == null) {
			listView = (ListView) context.findViewById(R.id.listElementVoyage);
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			listView.setAdapter(leAdapter);
		}

		idVoyageSelected = context.getSelectedVoyageId();

		// We get data from DB and give them to the Adapter
		bdAdapter.open();
		listeElement = bdAdapter.getAllMedia(idVoyageSelected);
		bdAdapter.close();

		Collections.sort(listeElement);

		if (leAdapter == null) {
			leAdapter = new ListeElementVoyageAdapter(context, listeElement);
			listView.setAdapter(leAdapter);
		} else {
			leAdapter.updateContent(listeElement);
		}
	}
	
	public void clearList()
	{
		this.leAdapter.clearContent();
	}
}
