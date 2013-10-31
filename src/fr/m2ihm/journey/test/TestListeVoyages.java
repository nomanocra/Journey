package fr.m2ihm.journey.test;

import java.util.List;
import java.util.ArrayList;

import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.Voyage;

public class TestListeVoyages {

	public List<Voyage> listeVoyages = new ArrayList<Voyage>();

	public TestListeVoyages() {
		listeVoyages.add(new Voyage("Singapore", false, new Date(0, 0, 0, 10, 2,
				2001), new Date(0, 0, 0, 5, 3, 2001)));

		listeVoyages.add(new Voyage("Russia", false, new Date(0, 0, 0, 5, 3,
				2001), new Date(0, 0, 0, 15, 8, 2004)));

		listeVoyages.add(new Voyage("USA !", true, new Date(0, 0, 0, 10, 10, 2012), Date.dateCourant()));

		listeVoyages.add(new Voyage("Paris", false, new Date(0, 0, 0, 14, 10, 2006), new Date(0, 0, 0, 17, 12, 2007)));

		listeVoyages.add(new Voyage("Montauban...", false, new Date(0, 0, 0, 1, 7, 2012), new Date(0, 0, 0, 10, 10, 2012)));

	}

}
