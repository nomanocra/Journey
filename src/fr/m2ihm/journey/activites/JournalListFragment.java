package fr.m2ihm.journey.activites;

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
	@Override
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
		this.context = (JournalActivity) container.getContext();
       
		View v = inflater.inflate(R.layout.journal_list_elements,null);
        
        
        
        
        return v;
    }
	public void initListeElement(){
		bdAdapter = new MyBDAdapterImpl(this.getActivity());
		ListView listView = (ListView) context.findViewById(R.id.listElementVoyage);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setAdapter(leAdapter);
		idVoyageSelected = context.getSelectedVoyageId();
		bdAdapter = new MyBDAdapterImpl(this.getActivity());
		bdAdapter.open();
		listeElement = bdAdapter.getAllMedia(idVoyageSelected);
		bdAdapter.close();
		leAdapter = new ListeElementVoyageAdapter(context, listeElement);
		
		
	}
	
	public void fillList() {
		if (this.context == null)
		{
			this.context = (JournalActivity) this.getActivity();
		}

		initListeElement();
	}
}
