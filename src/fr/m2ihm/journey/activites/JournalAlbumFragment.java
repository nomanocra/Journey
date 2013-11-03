package fr.m2ihm.journey.activites;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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

		this.context = (JournalActivity) container.getContext();

		this.gridView = (GridView) context.findViewById(R.id.gridView);
		Log.v("AlbumFragment-OnCreateView","gridView null ? " + (this.gridView == null));

		Log.v("AlbumFragment-OnCreateView","We are here");
		
		return v;
	}

	public void fillAlbum() {

		Log.v("AlbumFragment-fillAlbum","We are here");
		Log.v("AlbumFragment-fillAlbum","gridView null ? " + (this.gridView == null));
		
		this.customGridAdapter = new Journal_Album_GridAdapter(this.context,
				R.layout.row_grid, getData());
		Log.v("AlbumFragment-fillAlbum","customGridAdapter null ? " + (this.customGridAdapter == null));
		this.gridView.setAdapter(customGridAdapter);

		this.gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				//TODO => FULLSCREEN
			}

		});
	}

	private ArrayList<Photo> getData() {
		// We get data from DB
		MyBDAdapter bdAdapter = new MyBDAdapterImpl(this.context);

		bdAdapter.open();
		ArrayList<Photo> photoList = bdAdapter.getAllPhoto(this.context.getSelectedVoyageId());
		bdAdapter.close();
		
		Collections.sort(photoList);
		
		return (ArrayList<Photo>) photoList;
	}

}