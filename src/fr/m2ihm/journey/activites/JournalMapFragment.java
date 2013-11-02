package fr.m2ihm.journey.activites;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.adapter.MyBDAdapter;
import fr.m2ihm.journey.adapter.MyBDAdapterImpl;
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.metier.Position;
import fr.m2ihm.journey.metier.Voyage;
import fr.m2ihm.journey.test.TestMarqueurs;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JournalMapFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.journal_map, null);

		// this.initializeMap();

		return v;
	}

	public void fillMap(Context context, int voyageFocusedId) {

		// We get the map ref
		GoogleMap googleMap = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		// We empty it first
		googleMap.clear();

		// We get data from DB
		MyBDAdapter bdAdapter = new MyBDAdapterImpl(context);

		bdAdapter.open();
		List<Position> positionList = bdAdapter.getAllPosition(voyageFocusedId);
		Log.v("Position List", "POSITION LIST SIZE : " + positionList.size());
		List<ElementMap> elementList = bdAdapter.getAllMedia(voyageFocusedId);
		Log.v("ELEMENT List", "ELEMENT LIST SIZE : " + elementList.size());
		bdAdapter.close();

		// Then we print markers
		MarkerOptions marker;
		ElementMap currentElement;
		for (int i = 0; i < elementList.size(); i++) {
			currentElement = elementList.get(i);
			marker = new MarkerOptions().position(
					new LatLng(currentElement.getGps().getLatitude(),
							currentElement.getGps().getLongitude())).title(
					currentElement.getLieu() + " - " + currentElement.getDate().toString());
			
			marker.icon(BitmapDescriptorFactory.fromResource(currentElement
					.getIconResource()));
			
			marker.snippet(currentElement.getCommentaire());

			googleMap.addMarker(marker);
		}

		// And then we draw the waypoints
		List<LatLng> latlngList = new ArrayList<LatLng>();
		for (int i = 0; i < positionList.size(); i++) {
			latlngList
					.add(new LatLng(positionList.get(i).getGps().getLatitude(),
							positionList.get(i).getGps().getLongitude()));
		}

		PolylineOptions line = new PolylineOptions().addAll(latlngList)
				.width(5).color(Color.RED);

		googleMap.addPolyline(line);
		
		// Then we put a flag on the first and the last waypoint
		
		marker = new MarkerOptions().position(
				new LatLng(positionList.get(0).getGps().getLatitude(),
						positionList.get(0).getGps().getLongitude()));
		marker.title("D�part");
		googleMap.addMarker(marker);
		
		// DEPARTURE FLAG
//		marker.icon(BitmapDescriptorFactory.fromResource(currentElement
//				.getIconResource()));
		
		marker = new MarkerOptions().position(
				new LatLng(positionList.get(positionList.size()-1).getGps().getLatitude(),
						positionList.get(positionList.size()-1).getGps().getLongitude()));
		marker.title("Arriv�e");
		googleMap.addMarker(marker);
		
		// FINISH FLAG
//		marker.icon(BitmapDescriptorFactory.fromResource(currentElement
//				.getIconResource()));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		MapFragment f = (MapFragment) getFragmentManager().findFragmentById(
				R.id.map);
		if (f != null)
			getFragmentManager().beginTransaction().remove(f).commit();
	}
}
