package com.Valentin.coordenadas;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.raptor.rutasombria.R;

public class Map extends FragmentActivity {
	 
	private GoogleMap mapa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
	    mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	      mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	      mapa.setMyLocationEnabled(true);
	      mapa.getUiSettings().setZoomControlsEnabled(false);
	      mapa.getUiSettings().setCompassEnabled(true);
	  	    
	      Intent lanzador=this.getIntent();
	      String cad=lanzador.getCharSequenceExtra("A").toString(); 
	      
	      if(cad.contains(",-")){ BuscadorLatLng(cad); }
	         
	      else{ BuscarNombre(cad); }        
	        
        }
	
	public void BuscadorLatLng(String punto){
		
		String _punto = punto;
		
		String[] puntoAr=new String[2];
				puntoAr =_punto.split(",");
				
		LatLng latLng = new LatLng(Double.parseDouble(puntoAr[0]), Double.parseDouble(puntoAr[1]));
		
			mapa.addMarker(new MarkerOptions().position(latLng).title(
					"tu coordenada"));
			mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
		
	}
	
	public void BuscarNombre(String punto){
		 
	        if(Geocoder.isPresent()){
	        	
	        	Geocoder geoCoder;
		        geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
	               
		        String searchFor = punto;

	                try {
	                    List<Address> addresses = geoCoder.getFromLocationName(searchFor, 5);
	                    
	                    if (addresses==null || addresses.isEmpty()) {
	                    	
	                    	Toast.makeText(this,"ubicacion no encontrada",Toast.LENGTH_LONG).show();
	                    	
	                    }
	                    else { 
	                    	
	                    LatLng ll= new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    	
                        CameraUpdate posicionDireccionBuscador = CameraUpdateFactory.newLatLngZoom(
                        		ll, 15F);
                        mapa.addMarker(new MarkerOptions().position(ll).title(
            					ll.toString()));
                        mapa.moveCamera(posicionDireccionBuscador); }
	                    
	                    	
	                }catch(IOException e){
	                     Toast.makeText(this, "El mensaje de error es: " + e.getMessage(), Toast.LENGTH_LONG).show();
	                     e.printStackTrace();
	                     
	                     if(e.getMessage()=="Service not available"){
	                    	 Toast.makeText(this, "Reinicia tu dispositivo ", Toast.LENGTH_LONG).show();
	                    	 }
	                } 
	            }
	        else{ Toast.makeText(this, "Tu dispositivo no sopota la aplicación ", Toast.LENGTH_LONG).show(); }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
