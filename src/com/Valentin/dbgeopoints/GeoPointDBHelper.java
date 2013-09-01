package com.Valentin.dbgeopoints;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GeoPointDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "GEOPOINT";
    private static final int DATABASE_VERSION = 1;
	
	public static class TablaPoints{
        public static String TABLA_POINTS = "geo_point";
        public static String COLUMNA_ID = "_id";
        public static String COLUMNA_POINT = "point";  }
	
	private static final String DATABASE_CREATE = "create table "
            + TablaPoints.TABLA_POINTS + "(" + TablaPoints.COLUMNA_ID
            + " integer primary key autoincrement, " + TablaPoints.COLUMNA_POINT
            + " text not null);";
    
	public GeoPointDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("delete table if exists " + TablaPoints.TABLA_POINTS);
        onCreate(db);
		
	}

}
