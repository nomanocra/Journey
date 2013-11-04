package fr.m2ihm.journey.metier;

import java.util.Calendar;

public class Date {
	int second;
	int minute;
	int hour;
	int day;
	int month;
	int year;
	
	
	public Date(int second, int minute, int hour, int day, int month, int year) {
		super();
		this.second = second;
		this.minute = minute;
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.year = year;
	}


	public int getSecond() {
		return second;
	}


	public void setSecond(int second) {
		this.second = second;
	}


	public int getMinute() {
		return minute;
	}


	public void setMinute(int minute) {
		this.minute = minute;
	}


	public int getHour() {
		return this.hour;
	}


	public void setHour(int hour) {
		this.hour = hour;
	}


	public int getDay() {
		return day;
	}


	public void setDay(int day) {
		this.day = day;
	}


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public static Date dateCourant(){
		Calendar calendar = Calendar.getInstance();
		int second = calendar.get(Calendar.SECOND);
		int minute = calendar.get(Calendar.MINUTE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		return new Date(second, minute, hour, day, month, year);
	}
	
	@Override
	public String toString(){
		return this.day + "/" + this.month + "/" + this.year;
	}
	public String toStringHeure(){
		return this.hour + ":" + this.minute;
	}
	/**
	 * Compare two dates, give if the current is more actual than the one given
	 */
	public boolean isMoreActualThan(Date d)
	{
		if (this.year > d.year) return true;
		else if (this.year < d.year ) return false;
		// Same year
		else if ( this.month > d.month ) return true;
		else if ( this.month < d.month ) return false;
		// Same month
		else if ( this.day > d.day ) return true;
		else if ( this.day < d.day ) return false;
		// Same day
		else if ( this.hour > d.hour ) return true;
		else if ( this.hour < d.hour ) return false;
		// Same hour
		else if ( this.minute > d.minute ) return true;
		else if ( this.minute < d.minute ) return false;
		// Same minute
		else if ( this.second > d.second ) return true;
		else if ( this.second < d.second ) return false;
		// If they have the same second, I send false (for the Voyage comparison)
		return true;
	}

}
