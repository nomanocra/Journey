package fr.m2ihm.journey.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import fr.m2ihm.journey.R;
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.metier.Photo;
import fr.m2ihm.journey.metier.Voyage;

public class ListeElementVoyageAdapter extends ArrayAdapter<ElementMap>{

	private final Context context;
	private final List<ElementMap> values;
	
	
	public ListeElementVoyageAdapter(Context context, List<ElementMap> values) {
		super(context,R.layout.journal_list_elements ,values);
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
	rowView = inflater.inflate(R.layout.journal_list_row,
			parent, false);
	TextView lieu = (TextView) rowView.findViewById(R.id.lieuListe);
	TextView date = (TextView) rowView.findViewById(R.id.dateListe);
	TextView heure = (TextView) rowView.findViewById(R.id.heureListe);
	ImageView image = (ImageView) rowView.findViewById(R.id.liste_element_image);
	
	lieu.setText(currentElement.getLieu().toString());
	date.setText(currentElement.getDate().toString());
	heure.setText(currentElement.getDate().toStringHeure());
	image.setImageResource(currentElement.getIconResource());
	rowView.setId(currentElement.getId());
	return rowView;
	}
	
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
	
}
