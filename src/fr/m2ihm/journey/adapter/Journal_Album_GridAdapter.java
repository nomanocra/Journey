package fr.m2ihm.journey.adapter;

import java.util.ArrayList;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.metier.Photo;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Journal_Album_GridAdapter extends ArrayAdapter<Photo> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<Photo> data = new ArrayList<Photo>();

	public Journal_Album_GridAdapter(Context context, int layoutResourceId,
			ArrayList<Photo> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount()
	{
		return this.data.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;

		Log.v("GridAdapter - getView", "Position : " + position);
		Log.v("GridAdapter - getView", "Size : " + data.size());
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.imageTitle = (TextView) row.findViewById(R.id.album_text);
			holder.image = (ImageView) row.findViewById(R.id.album_image);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		Photo photo = data.get(position);
		holder.imageTitle.setText(photo.getLieu());
		// TODO CHANGE THIS
//		holder.image.setImageURI(Uri.parse(photo.getNomMedia()));
		holder.image.setImageResource(R.drawable.ic_launcher);
		
		return row;
	}

	static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}
	
	public void updateContent(ArrayList<Photo> listePhoto)
	{
		data = listePhoto;
		this.notifyDataSetChanged();
	}
}
