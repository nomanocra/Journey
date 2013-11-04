package fr.m2ihm.journey.adapter;

import java.util.List;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.metier.Voyage;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListeVoyagesAdapter extends ArrayAdapter<Voyage> {

	private final Context context;
	private final List<Voyage> values;

	public ListeVoyagesAdapter(Context context, List<Voyage> values) {
		super(context, R.layout.journal_liste_voyages, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView;
		Voyage currentVoyage = values.get(position);

		/* On verifie si le voyage est en cours */
		if (currentVoyage.isEncours()) {
			/* On affiche le layout "en cours" */
			rowView = inflater.inflate(R.layout.journal_liste_voyages_en_cours,
					parent, false);
		} else {
			rowView = inflater.inflate(R.layout.journal_liste_voyages, parent,
					false);

			/* We set dates */

			TextView startDate = (TextView) rowView
					.findViewById(R.id.journal_liste_voyages_formatteddate);
			startDate.setText(currentVoyage.getDebut().toString() + " <-> "
					+ currentVoyage.getFin().toString());
		}

		/* We set the title */
		TextView titre = (TextView) rowView
				.findViewById(R.id.journal_liste_voyages_nom_voyage);
		titre.setText(currentVoyage.getNom().toString());

		// We store the Voyage's ID
		rowView.setId(currentVoyage.getId());

		return rowView;
	}
	
	public void removeCurrentFromView(ListView listView, int voyageID) {

		int listIDToRemove = 0;
		boolean found = false;

		for (int i = 0; i < this.values.size(); i++) {
			if (this.values.get(i).getId() == voyageID) {
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
