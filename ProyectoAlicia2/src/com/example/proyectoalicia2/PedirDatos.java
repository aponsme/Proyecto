package com.example.proyectoalicia2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PedirDatos extends Activity{
	Bundle bundle;
	Spinner spinner;
	BaseDatos base;
	SQLiteDatabase bd;
	int empresa, cantidadInt;
	String fk_empresa, precio_compra, cadena, cantidad;
	float precio_compraFloat;
	ArrayList<String> precio;
	//Hashtable<String, String> pares;
	EditText edit, edit3;
	int id;
	float porcentaje;
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			System.out.println("entra");
			super.onCreate(savedInstanceState);
			setContentView(R.layout.seleccion);
			bundle=getIntent().getExtras();
			id=bundle.getInt("id");
			cadena= Integer.toString(id);
			edit=(EditText)findViewById(R.id.edit);
			edit3=(EditText)findViewById(R.id.edit3);
			final Button anadir=(Button)findViewById(R.id.anadir);
			final Button ir=(Button)findViewById(R.id.ir);
			spinner=(Spinner)findViewById(R.id.spinner);
			final ArrayList<String> listado = new ArrayList<String>();

			precio = new ArrayList<String>();
			//pares = new Hashtable<String, String>();
			base = new BaseDatos(this,"dbase", null, 1);
			bd = base.getReadableDatabase();
			Cursor c = bd.rawQuery("select id, nombre_empresa, precio_actual from empresas", null);
			if (c.moveToFirst()) 
			{
				//Recorremos el cursor hasta que no haya más registros
				do 
				{
					listado.add(c.getString(1));
					precio.add(c.getString(2));
					//pares.put(c.getString(0),c.getString(2));	
				} while(c.moveToNext());
			}
	        
	        bd.close();
			ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listado);
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(spinnerArrayAdapter);
			
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
			{    
			
				@Override
				public void onItemSelected(AdapterView<?> a, View v, int position, long id) {
				empresa=position;
				fk_empresa=listado.get(position);
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			anadir.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					precio_compra=edit.getText().toString();
					cantidad=edit3.getText().toString();
					cantidadInt=Integer.parseInt(cantidad);
					precio_compraFloat=Float.parseFloat(precio_compra);
					// TODO Auto-generated method stub
					//pares.containsKey(fk_empresa);
					bd = base.getWritableDatabase();
					ContentValues valores = new ContentValues();
					
					
//					NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//										 
//					//Agregando el icono, texto y momento para lanzar la notificación
//					int icon = R.drawable.ic_launcher;
//					CharSequence tickerText = "Notification Bar";
//					long when = System.currentTimeMillis();
//					 
//					Notification notification = new Notification(icon, tickerText, when);
//					 
//					Context context = getApplicationContext();
//					CharSequence contentTitle = "Notificación en barra";
//					CharSequence contentText = "Algunos beneficios han aumentado";
//					 
//					//Agregando sonido
//					notification.defaults |= Notification.DEFAULT_SOUND;
//					//Agregando vibración
//					notification.defaults |= Notification.DEFAULT_VIBRATE;
//					 
//					Intent notificationIntent = new Intent(Seleccion.this, Intermedia.class);
//					PendingIntent contentIntent = PendingIntent.getActivity(Seleccion.this, 0, notificationIntent, 0);
//					notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
//					 
//					mNotificationManager.notify(1, notification);
//    
					    
					String precioString=precio.get(empresa);
					porcentaje=(int) (((Float.parseFloat(precioString))/precio_compraFloat)*100)-100;
					
					float ganancia=((Float.parseFloat(precioString))*cantidadInt)-(precio_compraFloat*cantidadInt);
					
					valores.put("fk_empresa", fk_empresa);
					valores.put("fk_cartera", cadena);
					valores.put("cantidad", cantidadInt);
			        valores.put("precio_compra", precio_compraFloat);
			        //valores.put("id_precio_actual", precio.get(empresa));
			        //valores.put("porcentaje", porcentaje);
			        //valores.put("ganancia", ganancia);
			        bd.insert("operaciones", null, valores);
					bd.close();
					edit.setText("");
					edit3.setText("");
					
				}		
			});
			
			ir.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(PedirDatos.this, ListadoCarteras.class);
					
					startActivity(intent);
				}		
			});
			
		}	
		
		
		
		
		
}
