package fr.m2ihm.journey.activites;

import fr.m2ihm.journey.R;
import fr.m2ihm.journey.metier.Voyage;
import android.app.Activity;
import android.os.Bundle;

public class Fullscreen_Activity extends Activity {
	
	private Voyage currentVoyage;
	
	public Fullscreen_Activity(Voyage currentVoyage)
	{
		this.currentVoyage = currentVoyage;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullscreen);
	}
}
