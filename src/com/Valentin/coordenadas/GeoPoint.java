package com.Valentin.coordenadas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.raptor.rutasombria.R;

public class GeoPoint extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geo_point);
	}
	
	public void irCoordenadas(View v)
    {
    	Intent inten=new Intent(this,Coordenadas.class);
    	this.startActivity(inten);
    	Toast.makeText(this, "Presiona el icono de la app para ver los Geopoints", Toast.LENGTH_LONG).show();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.geo_point, menu);
		return true;
	}

}
