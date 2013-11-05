package fr.m2ihm.journey.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import fr.m2ihm.journey.R;
import fr.m2ihm.journey.metier.ElementMap;

public class ListeElementVoyageAdapter extends ArrayAdapter<ElementMap> {

	private final Context context;
	private List<ElementMap> values;

	public ListeElementVoyageAdapter(Context context, List<ElementMap> values) {
		super(context, R.layout.journal_list_elements, values);
		// TODO Stub du constructeur généré automatiquement
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView;
		ElementMap currentElement = values.get(position);
		
		rowView = inflater.inflate(R.layout.journal_list_row, parent, false);

		TextView lieu = (TextView) rowView.findViewById(R.id.lieuListe);
		TextView date = (TextView) rowView.findViewById(R.id.dateListe);
		TextView heure = (TextView) rowView.findViewById(R.id.heureListe);
		ImageView image = (ImageView) rowView
				.findViewById(R.id.liste_element_image);

		lieu.setText(currentElement.getLieu().toString());
		date.setText(currentElement.getDate().toString());
		heure.setText(currentElement.getDate().toStringHeure());
		image.setImageResource(currentElement.getIconResource());
		rowView.setId(currentElement.getId());
		
		return rowView;
	}

	/**
	 * Called to remove an element from the list
	 * @param listView
	 * @param elementID
	 */
	public void removeCurrentFromView(ListView listView, int elementID) {

		int listIDToRemove = 0;
		boolean found = false;

		for (int i = 0; i < this.values.size(); i++) {
			if (this.values.get(i).getId() == elementID) {
				found = true;
				listIDToRemove = i;
				break;
			}
		}

		if (found) {
			this.values.remove(listIDToRemove);
			this.notifyDataSetChanged();
		}
	}

	@Override
	public int getCount()
	{
		return this.values.size();
	}
	
	/**
	 * Called when you select another Voyage on the left list
	 * @param listeElement
	 */
	public void updateContent(List<ElementMap> listeElement) {
		this.values = listeElement;
		this.notifyDataSetChanged();
	}
	
	/**
	 * Called when a Voyage is destroyed : clean the view
	 */
	public void clearContent()
	{
		this.values = new ArrayList<ElementMap>();
		this.notifyDataSetChanged();
	}

}
