package com.example.proyectoalicia2;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListadoCarteras extends Activity{
	LinearLayout layout1;
	EditText edit;
	BaseDatos base;
	SQLiteDatabase bd;
	int id_cartera;
	Bundle bundle;
	ArrayList<String> listado;
	private ArrayAdapter<String> listAdapter ;  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intermedia);
		final ListView list = (ListView)findViewById(R.id.list); 
		layout1=(LinearLayout)findViewById(R.id.layout1);
	    listado = new ArrayList<String>();  
		base = new BaseDatos(this,"dbase", null, 1);
		bd = base.getReadableDatabase();
		Cursor fila = bd.rawQuery("select nombre_cartera from carteras", null);
        while (fila.moveToNext()) {
            listado.add(fila.getString(0));
        } 
        bd.close();
	      
	    // Create ArrayAdapter using the planet list.  
	    listAdapter = new ArrayAdapter<String>(this, R.layout.contenido, listado);  
	    list.setAdapter(listAdapter);   
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
			bd = base.getReadableDatabase();
			String[] columns ={"id"};
			System.out.println(listado.get(position));
			
			Cursor fila = bd.query("carteras", columns, "nombre_cartera=?", new String[] { listado.get(position) }, null, null, null);
    		//Cursor fila = bd.rawQuery("Select id from carteras where nombre_cartera="+rows.get(i).getTitle(), null);NO FUNCIONA
			while (fila.moveToNext()) {
				id_cartera=fila.getInt(0);
            }
			bd.close();
			Intent i=new Intent(ListadoCarteras.this, Resultados.class);
			Bundle b=new Bundle();
			b.putInt("id",id_cartera);
			i.putExtras(b);
			startActivity(i);
			}
		});
		
	}
	
}
