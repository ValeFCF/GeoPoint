package com.Valentin.dbgeopoints;

import java.util.ArrayList;
import java.util.List;

import com.Valentin.dbgeopoints.GeoPointDBHelper.TablaPoints;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GeoPointDataSource {
	
	private SQLiteDatabase db;
    private GeoPointDBHelper dbHelper;
    
    private String[] columnas = { TablaPoints.COLUMNA_ID,
            TablaPoints.COLUMNA_POINT };
    
    public GeoPointDataSource(Context context) {
        dbHelper = new GeoPointDBHelper(context);
    }
 
    public void open() {
        db = dbHelper.getWritableDatabase();
    }
 
    public void close() {
        dbHelper.close();
    }
    
    public void crearPoint(String point) {
        ContentValues values = new ContentValues();
        values.put(TablaPoints.COLUMNA_POINT, point);
        db.insert(TablaPoints.TABLA_POINTS, null, values);
    }
    
    public List<Point> getAllPoints() {
        List<Point> listaPoints = new ArrayList<Point>();
 
        Cursor cursor = db.query(TablaPoints.TABLA_POINTS, columnas, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Point nuevoPoint = cursorToPoint(cursor);
            listaPoints.add(nuevoPoint);
            cursor.moveToNext();
        }
 
        cursor.close();
        return listaPoints;
    }
    
    public void borrarPoint(Point point) {
        long id = point.getId();
        db.delete(TablaPoints.TABLA_POINTS, TablaPoints.COLUMNA_ID + " = " + id,
                null);
    }
    
    private Point cursorToPoint(Cursor cursor) {
        Point point = new Point();
        point.setId(cursor.getLong(0));
        point.setTexto(cursor.getString(1));
        return point;
    }
    
  
    
}
