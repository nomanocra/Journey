package fr.m2ihm.journey.activites;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.Journal_Album_GridAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.Photo;

public class JournalAlbumFragment extends Fragment {
	private GridView gridView;
	private Journal_Album_GridAdapter customGridAdapter;
	private JournalActivity context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.journal_album, null);

		return v;
	}
	
	/**
	 * This function waits the view to be created
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		this.context = (JournalActivity) this.getActivity();

		this.gridView = (GridView) context.findViewById(R.id.gridView);
		
		// If there is a Voyage selected on the list we fill the album
		
		if(context != null && gridView != null && context.isOneVoyageFocused())
		{
			this.fillAlbum();
		}
	}

	/**
	 * Fill the album with all photos taken for this Voyage
	 */
	public void fillAlbum() {

		// We get the context and the gridview
		if (this.context == null)
		{
			this.context = (JournalActivity) this.getActivity();
		}
		if (this.gridView == null)
		{
			this.gridView = (GridView) this.getActivity().findViewById(
					R.id.gridView);
		}
		
		// We initialize the adapter and fill it with our data
		if (this.customGridAdapter == null) {
			// We give the adapter an empty list
			this.customGridAdapter = new Journal_Album_GridAdapter(this.context,
					R.layout.row_grid, this.getData());
			this.gridView.setAdapter(customGridAdapter);
		}
		else
		{
			// The adapter will empty the gridView and fill it with the new pictures
			this.customGridAdapter.updateContent(this.getData());
		}
		
		this.gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				JournalActivity context = (JournalActivity) v.getContext();
				
				Intent intent = new Intent(context, Fullscreen_Activity.class);
				
				// We give the current Voyage
				intent.putExtra("currentVoyageId", "" + context.getSelectedVoyageId());
				
				// The photo selected
				intent.putExtra("photoPosition", "" + position);
				
				startActivity(intent);
				context.finish();
			}
		});
	}

	/**
	 * Get all Photos from the DB
	 * @return an ArrayList containing all the photos
	 */
	private ArrayList<Photo> getData() {
		// We get data from DB
		MyBDAdapter bdAdapter = new MyBDAdapterImpl(this.context);

		bdAdapter.open();
		ArrayList<Photo> photoList = bdAdapter.getAllPhoto(this.context
				.getSelectedVoyageId());
		bdAdapter.close();

		Collections.sort(photoList);
		
		return (ArrayList<Photo>) photoList;
	}

	/**
	 * Called when user delete a Voyage
	 */
	public void clearAlbum()
	{
		this.customGridAdapter.removeContent();
	}
}