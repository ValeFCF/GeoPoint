package com.Valentin.coordenadas;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.Valentin.dbgeopoints.GeoPointDataSource;
import com.Valentin.dbgeopoints.Point;
import com.raptor.rutasombria.R;

public class Coordenadas extends Activity implements OnItemClickListener{
	
	//Navigation Drawer
	private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
   
    //dbgpoints
    private int requestCode = 1;
    private ListView lvPoints;
    private GeoPointDataSource dataSource;
    
    
	EditText Nombre, Lat, Lng;
	String latitud,longitud ,LatLng;
	String temp;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coordenadas);
		

		Nombre =(EditText) findViewById(R.id.editText1);
		Lat = (EditText) findViewById(R.id.editText2);
		Lng = (EditText) findViewById(R.id.editText3);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) ;

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        
        
       /////////////////////////////////////////////////////////
                //////GeoPointDataSource ////////////////
       // //////////////////////////////////////////////////////
        dataSource = new GeoPointDataSource(this);
        dataSource.open();
        
     // Instanciamos los elementos
        lvPoints = (ListView) findViewById(R.id.left_drawer);
 
        // Cargamos la lista de notas disponibles
        List<Point> listaPoints = dataSource.getAllPoints();
        ArrayAdapter<Point> adapter = new ArrayAdapter<Point>(this,
                android.R.layout.simple_list_item_1, listaPoints);
		
     // Establecemos el adapter
        lvPoints.setAdapter(adapter);
 
        // Establecemos un Listener para el evento de pulsación
        lvPoints.setOnItemClickListener(this);
		
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
    
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////dbgpoints methods///////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@Override
    public void onItemClick(final AdapterView<?> adapterView, View view,
            final int position, long id) {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
            .setTitle("Points")
            .setMessage("¿Qué deseas hacer?")
            .setPositiveButton("Borrar",
                    new DialogInterface.OnClickListener() {
 
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            Point point = (Point) adapterView
                                    .getItemAtPosition(position);
                            dataSource.borrarPoint(point);
                             
                            // Refrescamos la lista
                            refrescarLista();
                        }
                })
 
            .setNegativeButton("Ver",
                    new DialogInterface.OnClickListener() {
            	
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					Point point = (Point) adapterView
                            .getItemAtPosition(position);
					
					temp=point.toString();
                    
                    Consultar(temp);
					
					
				}
                        
                });
        builder.show();
    }
	
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        // TODO Auto-generated method stub
	        super.onActivityResult(requestCode, resultCode, data);
	        if (requestCode == this.requestCode && resultCode == RESULT_OK) {
	            // Actualizar el Adapter
	            dataSource.open();
	            refrescarLista();
	        }
	    }
	 
	private void refrescarLista() {
	        List<Point> listaPoints = dataSource.getAllPoints();
	        ArrayAdapter<Point> adapter = new ArrayAdapter<Point>(this,
	            android.R.layout.simple_list_item_1, listaPoints);
	        lvPoints.setAdapter(adapter);
	    }
	 
	    @Override
	protected void onPause() {
	        // TODO Auto-generated method stub
	        dataSource.close();
	        super.onPause();
	    }
	 
	    @Override
	protected void onResume() {
	        // TODO Auto-generated method stub
	        dataSource.open();
	        super.onResume();
	    }
	
	public void GuardarNombre(View v) {
		String textoPoint = Nombre.getText().toString();
		 
        if (textoPoint.length() != 0) {
            dataSource.crearPoint(textoPoint);
            setResult(RESULT_OK);
            Toast.makeText(getApplicationContext(),
                    "Se guardo: " + textoPoint, Toast.LENGTH_SHORT)
                    .show();
           
         // Refrescamos la lista
            refrescarLista();
            
         //Borramos campo
            Nombre.setText("");
            
            
        } else {
            Toast.makeText(getApplicationContext(),
                    "No ha introducido texto en Nombre o Descrpción", Toast.LENGTH_SHORT)
                    .show();
        }
    }
	
	public void GuardarLatLng(View v) {
		
		latitud = Lat.getText().toString();
		longitud=Lng.getText().toString();
		
        if (latitud.length() != 0 && longitud.length() != 0) {
        	
        	LatLng = latitud + "," + longitud;
    		dataSource.crearPoint(LatLng);
            setResult(RESULT_OK);
            Toast.makeText(getApplicationContext(),
                    "Se guardo: " + LatLng, Toast.LENGTH_SHORT)
                    .show();
            
         // Refrescamos la lista
            refrescarLista();
            
         //Borramos campos
            Lat.setText("");
            Lng.setText("");
            
            
        } else {
            Toast.makeText(getApplicationContext(),
                    "No ha introducido texto en Latitud o Longitud", Toast.LENGTH_SHORT)
                    .show();
        }
    }
	
	public void Consultar(String point) {
		
		String _point = point;
		
		Intent inten=new Intent(this,Map.class);
		inten.putExtra("A", _point);
		this.startActivity(inten);
      
            }
 
	public void Salir(View v){
		Intent oIntent;
    	oIntent = new Intent();
        oIntent.setAction(Intent.ACTION_MAIN);
        oIntent.addCategory(Intent.CATEGORY_HOME);
        oIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(oIntent); 
        android.os.Process.killProcess(android.os.Process.myPid());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.coordenadas, menu);
		return true;
	}

} 