package fr.m2ihm.journey.metier;

import java.util.Calendar;

public class Date {
	int seconde;
	int minute;
	int heure;
	int day;
	int month;
	int year;
	
	
	public Date(int seconde, int minute, int heure, int day, int month, int year) {
		super();
		this.seconde = seconde;
		this.minute = minute;
		this.heure = heure;
		this.day = day;
		this.month = month;
		this.year = year;
	}


	static Date dateCourant(){
		Calendar calendar = Calendar.getInstance();
		int seconde = calendar.get(Calendar.SECOND);
		int minute = calendar.get(Calendar.MINUTE);
		int heure = calendar.get(Calendar.HOUR_OF_DAY);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		return new Date(seconde, minute, heure, day, month, year);
	}
}