package fr.m2ihm.journey.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import fr.m2ihm.journey.metier.Date;
import fr.m2ihm.journey.metier.ElementMap;
import fr.m2ihm.journey.metier.Note;
import fr.m2ihm.journey.metier.Photo;
import fr.m2ihm.journey.metier.Position;
import fr.m2ihm.journey.metier.Son;
import fr.m2ihm.journey.metier.Video;
import fr.m2ihm.journey.metier.Voyage;
import fr.m2ihm.journey.metier.Gps;

public class MyBDAdapterImpl implements MyBDAdapter {
	public static final int DB_VERSION = 4;
	public static final String DB_NAME = "Journey.db";
	
	private SQLiteDatabase mDB;
	private MyOpenHelper mOpenHelper;
	private Calendar calendar;
	
	// -------------------------------------------------------------------------

	private static final String TABLE_VOYAGE = "table_voyage";
	public static final String COL_V_ID = "_id";
	public static final String COL_V_NOM_VOYAGE = "nomV";
	public static final String COL_V_EN_COURS = "en_cours";
	public static final String COL_V_SECONDES_DEBUT = "secondes_d";
	public static final String COL_V_MINUTES_DEBUT = "minutes_d";
	public static final String COL_V_HOUR_DEBUT = "heures_d";
	public static final String COL_V_DAY_DEBUT = "day_d";
	public static final String COL_V_MONTH_DEBUT = "month_d";
	public static final String COL_V_YEAR_DEBUT = "year_d";
	public static final String COL_V_SECONDES_FIN = "secondes_f";
	public static final String COL_V_MINUTES_FIN = "minutes_f";
	public static final String COL_V_HOUR_FIN = "heures_f";
	public static final String COL_V_DAY_FIN = "day_f";
	public static final String COL_V_MONTH_FIN = "month_f";
	public static final String COL_V_YEAR_FIN = "year_f";

	private static final String CREATE_TABLE_VOYAGE = "create table "
			+ TABLE_VOYAGE + " (" 
			+ COL_V_ID + " integer primary key autoincrement, "

			+ COL_V_NOM_VOYAGE + " text not null, " 
			+ COL_V_EN_COURS + " integer, "

			+ COL_V_SECONDES_DEBUT + " integer, " 
			+ COL_V_MINUTES_DEBUT + " integer, " 
			+ COL_V_HOUR_DEBUT + " integer, " 
			+ COL_V_DAY_DEBUT + " integer, " 
			+ COL_V_MONTH_DEBUT + " integer, "
			+ COL_V_YEAR_DEBUT + " integer, "

			+ COL_V_SECONDES_FIN + " integer, "
			+ COL_V_MINUTES_FIN + " integer, " 
			+ COL_V_HOUR_FIN + " integer, "
			+ COL_V_DAY_FIN + " integer, " 
			+ COL_V_MONTH_FIN + " integer, " 
			+ COL_V_YEAR_FIN + " integer);";
	
	// -------------------------------------------------------------------------
	private static final String TABLE_EVENEMENT = "table_evenement";
	public static final String COL_E_ID = "_id";
	public static final String COL_E_ID_VOYAGE = "idV";
	public static final String COL_E_LAT = "latitude";
	public static final String COL_E_LON = "longitude";
	public static final String COL_E_SECONDES = "secondes";
	public static final String COL_E_MINUTES = "minutes";
	public static final String COL_E_HOUR = "heures";
	public static final String COL_E_DAY = "day";
	public static final String COL_E_MONTH = "month";
	public static final String COL_E_YEAR = "year";

	public static final String COL_E_LIEU = "lieu";
	public static final String COL_E_MEDIA_TYPE = "typeM";
	public static final String COL_E_MEDIA_NOM = "nomM";
	public static final String COL_E_COMMENTAIRE = "commentaire";

	private static final String CREATE_TABLE_EVENEMENT = "create table "
			+ TABLE_EVENEMENT + " (" 
			+ COL_E_ID + " integer primary key autoincrement, "

			+ COL_E_LAT + " real, " + COL_E_LON + " real, "

			+ COL_E_SECONDES + " integer, " + COL_E_MINUTES + " integer, "
			+ COL_E_HOUR + " integer, " + COL_E_DAY + " integer, "
			+ COL_E_MONTH + " integer, " + COL_E_YEAR + " integer, "

			+ COL_E_LIEU + " text not null, " + COL_E_COMMENTAIRE
			+ " text not null, " + COL_E_MEDIA_TYPE + " integer not null, "
			+ COL_E_MEDIA_NOM + " text not null, "
			+ COL_E_ID_VOYAGE + " integer" +" REFERENCES " + TABLE_VOYAGE
			+");";


	// -------------------------------------------------------------------------

	public MyBDAdapterImpl(Context context) {
		mOpenHelper = new MyOpenHelper(context, DB_NAME, null, DB_VERSION);
	}

	public void open() {
		mDB = mOpenHelper.getWritableDatabase();
	}

	public void close() {
		mDB.close();
	}

	// -------------------------------------------------------------------------

	public ElementMap getEvenement(long id) {
		ElementMap e = null;
		Cursor cursor = mDB.query(TABLE_EVENEMENT, new String[] { COL_E_ID, // 0
				COL_E_ID_VOYAGE, // 1
				COL_E_LAT, // 2
				COL_E_LON, // 3
				COL_E_SECONDES, // 4
				COL_E_MINUTES, // 5
				COL_E_HOUR, // 6
				COL_E_DAY, // 7
				COL_E_MONTH, // 8
				COL_E_YEAR, // 9
				COL_E_COMMENTAIRE, // 10
				COL_E_MEDIA_NOM, // 11
				COL_E_LIEU, // 12
				COL_E_MEDIA_TYPE // 13
				}, COL_E_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		Voyage v = getVoyage(cursor.getInt(1));
		String typeMedia = cursor.getString(13);

		if (typeMedia.equals("Video")) {
			e = new Video(v, new Gps(cursor.getFloat(2), cursor.getFloat(3)),
					new Date(cursor.getInt(4), cursor.getInt(5), cursor
							.getInt(6), cursor.getInt(7), cursor.getInt(8),
							cursor.getInt(9)),
					cursor.getString(11), cursor.getString(12),
					cursor.getString(10));
		} else if (typeMedia.equals("Photo")) {
			e = new Photo(v, new Gps(cursor.getFloat(2), cursor.getFloat(3)),
					new Date(cursor.getInt(4), cursor.getInt(5), cursor
							.getInt(6), cursor.getInt(7), cursor.getInt(8),
							cursor.getInt(9)),
					cursor.getString(11), cursor.getString(12),
					cursor.getString(10));
		} else if (typeMedia.equals("Son")) {
			e = new Son(v, new Gps(cursor.getFloat(2), cursor.getFloat(3)),
					new Date(cursor.getInt(4), cursor.getInt(5), cursor
							.getInt(6), cursor.getInt(7), cursor.getInt(8),
							cursor.getInt(9)),
					cursor.getString(11), cursor.getString(12),
					cursor.getString(10));
		} else if (typeMedia.equals("Note")) {
			e = new Note(v, new Gps(cursor.getFloat(2), cursor.getFloat(3)),
					new Date(cursor.getInt(4), cursor.getInt(5), cursor
							.getInt(6), cursor.getInt(7), cursor.getInt(8),
							cursor.getInt(9)),
					cursor.getString(12), cursor.getString(10));
		} else if (typeMedia.equals("Position")) {
			e = new Position(v,
					new Gps(cursor.getFloat(2), cursor.getFloat(3)), new Date(
							cursor.getInt(4), cursor.getInt(5),
							cursor.getInt(6), cursor.getInt(7),
							cursor.getInt(8), cursor.getInt(9)));
		} else {
			e = new Position(v,
					new Gps(cursor.getFloat(2), cursor.getFloat(3)), new Date(
							cursor.getInt(4), cursor.getInt(5),
							cursor.getInt(6), cursor.getInt(7),
							cursor.getInt(8), cursor.getInt(9)));
		}

		e.setId(cursor.getInt(0));
		return e;
	}

	public Voyage getVoyage(long id) {
		Cursor cursor = mDB.query(TABLE_VOYAGE, new String[] { COL_V_ID, // 0
				COL_V_NOM_VOYAGE, // 1
				COL_V_EN_COURS, // 2
				COL_V_SECONDES_DEBUT, // 3
				COL_V_MINUTES_DEBUT, // 4
				COL_V_HOUR_DEBUT, // 5
				COL_V_DAY_DEBUT, // 6
				COL_V_MONTH_DEBUT, // 7
				COL_V_YEAR_DEBUT, // 8
				COL_V_SECONDES_FIN, // 9
				COL_V_MINUTES_FIN, // 10
				COL_V_HOUR_FIN, // 11
				COL_V_DAY_FIN, // 12
				COL_V_MONTH_FIN, // 13
				COL_V_YEAR_FIN }, // 14
				COL_V_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();

		Voyage v = new Voyage(cursor.getString(1), cursor.getInt(2) == 1,
				new Date(cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
						cursor.getInt(6), cursor.getInt(7), cursor.getInt(8)),
				new Date(cursor.getInt(9), cursor.getInt(10),
						cursor.getInt(11), cursor.getInt(12),
						cursor.getInt(13), cursor.getInt(14)));
		v.setId(cursor.getInt(0));
		return v;
	}

	@Override
	public ArrayList<Voyage> getAllVoyage() {
		ArrayList<Voyage> mesVoyages = new ArrayList<Voyage>();
		Cursor cursor = mDB.query(TABLE_VOYAGE, new String[] { COL_V_ID, // 0
				COL_V_NOM_VOYAGE, // 1
				COL_V_EN_COURS, // 2
				COL_V_SECONDES_DEBUT, // 3
				COL_V_MINUTES_DEBUT, // 4
				COL_V_HOUR_DEBUT, // 5
				COL_V_DAY_DEBUT, // 6
				COL_V_MONTH_DEBUT, // 7
				COL_V_YEAR_DEBUT, // 8
				COL_V_SECONDES_FIN, // 9
				COL_V_MINUTES_FIN, // 10
				COL_V_HOUR_FIN, // 11
				COL_V_DAY_FIN, // 12
				COL_V_MONTH_FIN, // 13
				COL_V_YEAR_FIN }, // 14
				null, null, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			Log.v("getAllVoyage",
					cursor.getString(1) + " : " + cursor.getInt(2)
							+ " | Time : " + cursor.getInt(5) + ":"
							+ cursor.getInt(4) + "." + cursor.getInt(3)
							+ " | Date : " + cursor.getInt(6) + "/"
							+ cursor.getInt(7) + "/" + cursor.getInt(8));
			Voyage v = new Voyage(cursor.getString(1), (cursor.getInt(2) == 1),
					new Date(cursor.getInt(3), cursor.getInt(4),
							cursor.getInt(5), cursor.getInt(6),
							cursor.getInt(7), cursor.getInt(8)), new Date(
							cursor.getInt(9), cursor.getInt(10),
							cursor.getInt(11), cursor.getInt(12),
							cursor.getInt(13), cursor.getInt(14)));
			v.setId(cursor.getInt(0));
			mesVoyages.add(v);
		}
		return mesVoyages;
	}

	public ArrayList<ElementMap> getAllMedia(int idVoyage) {
		ArrayList<ElementMap> allMedia = new ArrayList<ElementMap>();
		ElementMap e = null;
		Cursor cursor = mDB.query(TABLE_EVENEMENT, new String[] { COL_E_ID, // 0
				COL_E_ID_VOYAGE, // 1
				COL_E_LAT, // 2
				COL_E_LON, // 3
				COL_E_SECONDES, // 4
				COL_E_MINUTES, // 5
				COL_E_HOUR, // 6
				COL_E_DAY, // 7
				COL_E_MONTH, // 8
				COL_E_YEAR, // 9
				COL_E_LIEU, // 10
				COL_E_COMMENTAIRE, // 11
				COL_E_MEDIA_NOM, // 12
				COL_E_MEDIA_TYPE // 13
				}, COL_E_ID_VOYAGE + " = " + idVoyage, null, null, null, null);
		cursor.moveToFirst();
		Voyage v = getVoyage(cursor.getInt(1));
		String typeMedia = cursor.getString(13);

		for (int i = 0; i < cursor.getCount(); i++) {
			if (typeMedia.equals("Video")) {
				e = new Video(v,
						new Gps(cursor.getFloat(2), cursor.getFloat(3)),
						new Date(cursor.getInt(4), cursor.getInt(5), cursor
								.getInt(6), cursor.getInt(7), cursor.getInt(8),
								cursor.getInt(9)),
						cursor.getString(11), cursor.getString(12),
						cursor.getString(10));
				e.setId(cursor.getInt(0));
				allMedia.add(e);
			} else if (typeMedia.equals("Photo")) {
				e = new Photo(v,
						new Gps(cursor.getFloat(2), cursor.getFloat(3)),
						new Date(cursor.getInt(4), cursor.getInt(5), cursor
								.getInt(6), cursor.getInt(7), cursor.getInt(8),
								cursor.getInt(9)),
						cursor.getString(11), cursor.getString(12),
						cursor.getString(10));
				e.setId(cursor.getInt(0));
				allMedia.add(e);
			} else if (typeMedia.equals("Son")) {
				e = new Son(v, new Gps(cursor.getFloat(2), cursor.getFloat(3)),
						new Date(cursor.getInt(4), cursor.getInt(5), cursor
								.getInt(6), cursor.getInt(7), cursor.getInt(8),
								cursor.getInt(9)),
						cursor.getString(11), cursor.getString(12),
						cursor.getString(10));
				e.setId(cursor.getInt(0));
				allMedia.add(e);
			} else if (typeMedia.equals("Note")) {
				e = new Note(v,
						new Gps(cursor.getFloat(2), cursor.getFloat(3)),
						new Date(cursor.getInt(4), cursor.getInt(5), cursor
								.getInt(6), cursor.getInt(7), cursor.getInt(8),
								cursor.getInt(9)),
						cursor.getString(11), cursor.getString(10));
				e.setId(cursor.getInt(0));
				allMedia.add(e);
			} else {

			}
			/*
			 * else if(typeMedia.equals("Position")){ e = new Position( v, new
			 * Gps(cursor.getFloat(2), cursor.getFloat(3)), new
			 * Date(cursor.getInt(4), cursor.getInt(5), cursor.getInt(6),
			 * cursor.getInt(7), cursor.getInt(8), cursor.getInt(9))); }
			 */

			cursor.moveToNext();
		}
		return allMedia;
	}

	public ArrayList<Son> getAllSon(int idVoyage) {
		ArrayList<Son> allSong = new ArrayList<Son>();
		Cursor cursor = mDB.query(TABLE_EVENEMENT, new String[] { COL_E_ID, // 0
				COL_E_ID_VOYAGE, // 1
				COL_E_LAT, // 2
				COL_E_LON, // 3
				COL_E_SECONDES, // 4
				COL_E_MINUTES, // 5
				COL_E_HOUR, // 6
				COL_E_DAY, // 7
				COL_E_MONTH, // 8
				COL_E_YEAR, // 9
				COL_E_LIEU, // 10
				COL_E_COMMENTAIRE, // 11
				COL_E_MEDIA_NOM, // 12
				COL_E_MEDIA_TYPE }, COL_E_ID_VOYAGE + " = " + idVoyage
				+ " AND " + COL_E_MEDIA_TYPE + " = " + "Son", null, null, null,
				null);
		cursor.moveToFirst();

		Voyage v = getVoyage(cursor.getInt(1));
		for (int i = 0; i < cursor.getCount(); i++) {
			Son e = new Son(v, new Gps(cursor.getFloat(2), cursor.getFloat(3)),
					new Date(cursor.getInt(4), cursor.getInt(5), cursor
							.getInt(6), cursor.getInt(7), cursor.getInt(8),
							cursor.getInt(9)),
					cursor.getString(11), cursor.getString(12),
					cursor.getString(10));
			e.setId(cursor.getInt(0));
			allSong.add(e);
			cursor.moveToNext();
		}
		return allSong;
	}

	public ArrayList<ElementMap> getAllPhoto(int idVoyage) {
		ArrayList<ElementMap> allPhoto = new ArrayList<ElementMap>();
		Cursor cursor = mDB.query(TABLE_EVENEMENT, new String[] { COL_E_ID, // 0
				COL_E_ID_VOYAGE, // 1
				COL_E_LAT, // 2
				COL_E_LON, // 3
				COL_E_SECONDES, // 4
				COL_E_MINUTES, // 5
				COL_E_HOUR, // 6
				COL_E_DAY, // 7
				COL_E_MONTH, // 8
				COL_E_YEAR, // 9
				COL_E_LIEU, // 10
				COL_E_COMMENTAIRE, // 11
				COL_E_MEDIA_NOM }, // 13
				COL_E_ID_VOYAGE + " = " + idVoyage + " AND " + COL_E_MEDIA_TYPE
						+ " = " + "Photo", null, null, null, null);
		cursor.moveToFirst();
		Voyage v = getVoyage(cursor.getInt(1));
		for (int i = 0; i < cursor.getCount(); i++) {
			ElementMap e = new Photo(v, new Gps(cursor.getFloat(2),
					cursor.getFloat(3)), new Date(cursor.getInt(4),
					cursor.getInt(5), cursor.getInt(6), cursor.getInt(7),
					cursor.getInt(8), cursor.getInt(9)), cursor.getString(11),
					cursor.getString(12), cursor.getString(10));
			e.setId(cursor.getInt(0));
			allPhoto.add(e);
			cursor.moveToNext();
		}
		return allPhoto;
	}

	public ArrayList<Video> getAllVideo(int idVoyage) {
		ArrayList<Video> allVideo = new ArrayList<Video>();
		Cursor cursor = mDB.query(TABLE_EVENEMENT, new String[] { COL_E_ID, // 0
				COL_E_ID_VOYAGE, // 1
				COL_E_LAT, // 2
				COL_E_LON, // 3
				COL_E_SECONDES, // 4
				COL_E_MINUTES, // 5
				COL_E_HOUR, // 6
				COL_E_DAY, // 7
				COL_E_MONTH, // 8
				COL_E_YEAR, // 9
				COL_E_LIEU, // 10
				COL_E_COMMENTAIRE, // 11
				COL_E_MEDIA_TYPE, // 12
				COL_E_MEDIA_NOM }, // 13
				COL_E_ID_VOYAGE + " = " + idVoyage + " AND " + COL_E_MEDIA_TYPE
						+ " = " + "Video", null, null, null, null);
		cursor.moveToFirst();
		Voyage v = getVoyage(cursor.getInt(1));
		for (int i = 0; i < cursor.getCount(); i++) {
			Video e = new Video(v, new Gps(cursor.getFloat(2),
					cursor.getFloat(3)), new Date(cursor.getInt(4),
					cursor.getInt(5), cursor.getInt(6), cursor.getInt(7),
					cursor.getInt(8), cursor.getInt(9)), cursor.getString(11),
					cursor.getString(12), cursor.getString(10));
			e.setId(cursor.getInt(0));
			allVideo.add(e);
			cursor.moveToNext();
		}
		return allVideo;
	}

	public ArrayList<Position> getAllPosition(int idVoyage) {
		ArrayList<Position> allGps = new ArrayList<Position>();
		Cursor cursor = mDB.query(TABLE_EVENEMENT, new String[] { COL_E_ID, // 0
				COL_E_ID_VOYAGE, // 1
				COL_E_LAT, // 2
				COL_E_LON, // 3
				COL_E_SECONDES, // 4
				COL_E_MINUTES, // 5
				COL_E_HOUR, // 6
				COL_E_DAY, // 7
				COL_E_MONTH, // 8
				COL_E_YEAR, // 9
				COL_E_LIEU, // 10
				COL_E_COMMENTAIRE, // 11
				COL_E_MEDIA_TYPE, // 12
				COL_E_MEDIA_NOM }, // 13
				COL_E_ID_VOYAGE + " = " + idVoyage, null, null, null, null);
		cursor.moveToFirst();
		Voyage v = getVoyage(cursor.getInt(1));

		for (int i = 0; i < cursor.getCount(); i++) {
			Position position = new Position(v, new Gps(cursor.getFloat(2),
					cursor.getFloat(3)), new Date(cursor.getInt(4),
					cursor.getInt(5), cursor.getInt(6), cursor.getInt(7),
					cursor.getInt(8), cursor.getInt(9)));

			allGps.add(position);
			cursor.moveToNext();
		}
		return allGps;
	}

	public Voyage getVoyageCourant() {
		Voyage v = new Voyage("", false, new Date(0, 0, 0, 0, 0, 0), new Date(
				0, 0, 0, 0, 0, 0));
		v.setId(-1);
		Cursor cursor = mDB.query(TABLE_VOYAGE, new String[] { COL_V_ID, // 0
				COL_V_NOM_VOYAGE, // 1
				COL_V_EN_COURS, // 2
				COL_V_SECONDES_DEBUT, // 3
				COL_V_MINUTES_DEBUT, // 4
				COL_V_HOUR_DEBUT, // 5
				COL_V_DAY_DEBUT, // 6
				COL_V_MONTH_DEBUT, // 7
				COL_V_YEAR_DEBUT, // 8
				COL_V_SECONDES_FIN, // 9
				COL_V_MINUTES_FIN, // 10
				COL_V_HOUR_FIN, // 11
				COL_V_DAY_FIN, // 12
				COL_V_MONTH_FIN, // 13
				COL_V_YEAR_FIN }, // 14
				COL_V_EN_COURS + " = " + 1, null, null, null, null);
		cursor.moveToFirst();
		if (cursor.getCount() != 0) {
			v = new Voyage(cursor.getString(1), cursor.getInt(2) == 1,
					new Date(cursor.getInt(3), cursor.getInt(4),
							cursor.getInt(5), cursor.getInt(6),
							cursor.getInt(7), cursor.getInt(8)), new Date(
							cursor.getInt(9), cursor.getInt(10),
							cursor.getInt(11), cursor.getInt(12),
							cursor.getInt(13), cursor.getInt(14)));
			v.setId(cursor.getInt(0));
		}
		return v;
	}

	public long ajouterVoyage(String nom) {
		calendar = Calendar.getInstance();
		int seconde = calendar.get(Calendar.SECOND);
		int minutes = calendar.get(Calendar.MINUTE);
		int heures = calendar.get(Calendar.HOUR_OF_DAY);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int encours = 1;
		ContentValues values = new ContentValues();

		values.put(COL_V_NOM_VOYAGE, nom);
		values.put(COL_V_EN_COURS, encours);

		values.put(COL_V_SECONDES_DEBUT, seconde);
		values.put(COL_V_MINUTES_DEBUT, minutes);
		values.put(COL_V_HOUR_DEBUT, heures);
		values.put(COL_V_DAY_DEBUT, day);
		values.put(COL_V_MONTH_DEBUT, month);
		values.put(COL_V_YEAR_DEBUT, year);

		values.put(COL_V_SECONDES_FIN, 0);
		values.put(COL_V_MINUTES_FIN, 0);
		values.put(COL_V_HOUR_FIN, 0);
		values.put(COL_V_DAY_FIN, 0);
		values.put(COL_V_MONTH_FIN, 0);
		values.put(COL_V_YEAR_FIN, 0);
		return mDB.insert(TABLE_VOYAGE, null, values);
	}

	public long ajouterElementMap(ElementMap e) {
		calendar = Calendar.getInstance();
		int seconde = calendar.get(Calendar.SECOND);
		int minutes = calendar.get(Calendar.MINUTE);
		int heures = calendar.get(Calendar.HOUR_OF_DAY);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		
		int idVoyage = getVoyageCourant().getId();
		
		if(idVoyage != -1){
		ContentValues values = new ContentValues();
		values.put(COL_E_ID_VOYAGE, idVoyage);
		values.put(COL_E_LAT, e.getGps().getLatitude());
		values.put(COL_E_LON, e.getGps().getLongitude());
		values.put(COL_E_SECONDES, seconde);
		values.put(COL_E_MINUTES, minutes);
		values.put(COL_E_HOUR, heures);
		values.put(COL_E_DAY, day);
		values.put(COL_E_MONTH, month);
		values.put(COL_E_YEAR, year);

		values.put(COL_E_LIEU, e.getLieu());
		values.put(COL_E_MEDIA_TYPE, e.getType());
		values.put(COL_E_COMMENTAIRE, e.getCommentaire());
		values.put(COL_E_MEDIA_NOM, e.getNomMedia());
		
		return mDB.insert(TABLE_EVENEMENT, null, values);
		}
		return 0;
	}

	public int modifierNomVoyage(long id, String nom) {
		ContentValues values = new ContentValues();
		values.put(COL_V_NOM_VOYAGE, nom);
		return mDB.update(TABLE_VOYAGE, values, COL_V_ID + "==" + id, null);
	}

	public int terminerVoyage(long id) {
		ContentValues values = new ContentValues();
		values.put(COL_V_EN_COURS, 0);
		return mDB.update(TABLE_VOYAGE, values, COL_V_ID + "==" + id, null);
	}

	public int modifierElementMap(long id, String comment, String lieu) {
		ContentValues values = new ContentValues();
		values.put(COL_E_COMMENTAIRE, comment);
		values.put(COL_E_LIEU, lieu);
		return mDB.update(TABLE_EVENEMENT, values, COL_E_ID + "==" + id, null);
	}

	public int supprimerVoyage(long id) {
		return mDB.delete(TABLE_VOYAGE, COL_V_ID + " == " + id, null);
	}

	public int supprimerAllVoyage() {
		return mDB.delete(TABLE_VOYAGE, null, null);
	}

	public int supprimerEvenemet(long id) {
		return mDB.delete(TABLE_EVENEMENT, COL_E_ID + " == " + id, null);
	}

	// -------------------------------------------------------------------------
	// -------------------------------------------------------------------------

	private class MyOpenHelper extends SQLiteOpenHelper {
		public MyOpenHelper(Context context, String name,
				CursorFactory cursorFactory, int version) {
			super(context, name, cursorFactory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_EVENEMENT);
			db.execSQL(CREATE_TABLE_VOYAGE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENEMENT + ";");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOYAGE + ";");
			onCreate(db);
		}
	}

}
