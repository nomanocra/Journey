package fr.m2ihm.journey.test;

import java.util.ArrayList;
import java.util.List;

import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.metier.Gps;
import fr.m2ihm.journey.metier.Photo;
import fr.m2ihm.journey.metier.Position;
import fr.m2ihm.journey.metier.Voyage;

public class TestMarqueurs {

	public List<ElementMap> elementList;
	public List<ElementMap> positionList;
	
	public TestMarqueurs()
	{
        
        elementList = new ArrayList<ElementMap>();
        positionList = new ArrayList<ElementMap>();
        
        Voyage currentVoyage = new Voyage("Mayorque", true, new Date(0,0,0,0,0,0), Date.dateCourant());
        
        /*****************************
         * Filling of the element list
         ****************************/
        
        elementList.add(
        		new Photo(currentVoyage, new Gps(0.1, 0.4), new Date(0,0,1,0,0,0), "res/drawable/ic_launcher", "4 chemin de la route, Mayorque", "Oui")
        		);
        
        elementList.add(
        		new Photo(currentVoyage, new Gps(10, 0.4), new Date(0,0,5,0,0,0), "res/drawable/ic_launcher", "Après le chemin de la route", "Nice")
        		);
        
        elementList.add(
        		new Photo(currentVoyage, new Gps(0.1, 5), new Date(0,0,10,0,0,0), "res/drawable/ic_launcher", "Après le après le chemin de la route", "Cool")
        		);

        /*****************************
         * Filling of the position list
         ****************************/
        
        positionList.add(
        		new Position(currentVoyage, new Gps(0.1, 0.4), new Date(0,0,0,0,0,0))
        		);
        
        positionList.add(
        		new Position(currentVoyage, new Gps(0.1, 0.4), new Date(0,0,1,0,0,0))
        		);
        
        positionList.add(
        		new Position(currentVoyage, new Gps(10, 0.4), new Date(0,0,5,0,0,0))
        		);
        
        positionList.add(
        		new Position(currentVoyage, new Gps(0.1, 5),  new Date(0,0,10,0,0,0))
        		);
        
        positionList.add(
        		new Position(currentVoyage, new Gps(5, 10),  new Date(0,0,10,0,0,0))
        		);
        
	}	
}
