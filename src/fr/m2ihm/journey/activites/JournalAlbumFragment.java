package fr.m2ihm.journey.activites;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
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

<<<<<<< HEAD
		this.context = (JournalActivity) container.getContext();

		this.gridView = (GridView) context.findViewById(R.id.gridView);
		Log.v("AlbumFragment-OnCreateView","gridView null ? " + (this.gridView == null));

		Log.v("AlbumFragment-OnCreateView","We are here");
		/*
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				Photo photo = (Photo) gridView.getItemAtPosition(position);
			   Toast.makeText(context,
				"Image Name : " + photo.getNomMedia(), Toast.LENGTH_SHORT).show();
			}
		});
		*/
=======
>>>>>>> 84db545f63a471128ed946099cc7f8e5636b5d2e
		return v;
	}

	public void fillAlbum() {

		// We get the context and the gridview
		if (this.context == null)
		{
			this.context = (JournalActivity) this.getActivity();
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
			ArrayList<Photo> listePhotos = this.getData();
			Log.v("fillAlbum", "listePhoto size : " + listePhotos.size());
			this.customGridAdapter.updateContent(this.getData());
		}
		

		this.gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				Log.v("OUIIII", "ON EST ICIIIII");
				
				JournalActivity context = (JournalActivity) v.getContext();
				
				Intent intent = new Intent(context, Fullscreen_Activity.class);
				
				// We give the current Voyage
				intent.putExtra("currentVoyageId", context.getSelectedVoyageId());
				
				// And the photo selected
				intent.putExtra("photoPosition", position);
				
				startActivity(intent);
				context.finish();
			}
		});
	}

	private ArrayList<Photo> getData() {
		// We get data from DB
		MyBDAdapter bdAdapter = new MyBDAdapterImpl(this.context);

		bdAdapter.open();
		ArrayList<Photo> photoList = bdAdapter.getAllPhoto(this.context
				.getSelectedVoyageId());
		Log.v("getData","selectedVoyageId : " + this.context
				.getSelectedVoyageId());
		bdAdapter.close();

		Collections.sort(photoList);

		Log.v("getData", "size : " + photoList.size());
		
		return (ArrayList<Photo>) photoList;
	}

}