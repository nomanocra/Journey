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
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.test.TestMarqueurs;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JournalMapFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.journal_map, null);

		this.initializeMap();

		return v;
	}

	public void initializeMap() {
		// We get the map ref
		GoogleMap googleMap = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		// Get DB data from the current (or last) trip, if it exists

		// Test
		TestMarqueurs tm = new TestMarqueurs();
		List<ElementMap> elementList = tm.elementList;
		List<ElementMap> positionList = tm.positionList;

		// Then we print markers
		MarkerOptions marker;
		ElementMap currentElement;
		for (int i = 0; i < elementList.size(); i++) {
			currentElement = elementList.get(i);
			marker = new MarkerOptions().position(
					new LatLng(currentElement.getGps().getLatitude(),
							currentElement.getGps().getLongitude())).title(
									currentElement.getLieu());
			
			marker.icon(BitmapDescriptorFactory.fromResource(currentElement.getIconResource()));
			
			googleMap.addMarker(marker);
		}

		// And then we draw the waypoints
		List<LatLng> latlngList = new ArrayList<LatLng>();
		for (int i = 0; i < positionList.size(); i++) {
			latlngList.add(
					new LatLng(
							positionList.get(i).getGps().getLatitude(),
							positionList.get(i).getGps().getLongitude()
					)
			);
		}
		
		PolylineOptions line = new PolylineOptions()
				.addAll(latlngList)
				.width(5).color(Color.RED);

		googleMap.addPolyline(line);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		this.initializeMap();
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
