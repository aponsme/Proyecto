package com.example.finanzas7;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import android.app.Activity;
import android.content.ContentValues;
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

public class Seleccion extends Activity{
	Bundle bundle;
	Spinner spinner;
	Base base;
	SQLiteDatabase bd;
	int empresa;
	String id_empresa;
	String precio_compra;
	float precio_compraint;
	ArrayList<String> precio;
	String cadena;
	String cantidad;
	int cantidadint;
	Hashtable<String, String> pares;
	EditText edit3;
	EditText edit;
	int id;
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.seleccion);
			bundle=getIntent().getExtras();
			id=bundle.getInt("id");
			cadena= Integer.toString(id);
			final TextView t=(TextView)findViewById(R.id.text0);
			edit=(EditText)findViewById(R.id.edit);
			edit3=(EditText)findViewById(R.id.edit3);
			final Button anadir=(Button)findViewById(R.id.anadir);
			final Button ir=(Button)findViewById(R.id.ir);
			spinner=(Spinner)findViewById(R.id.spinner);
			final ArrayList<String> listado = new ArrayList<String>();
			
			precio = new ArrayList<String>();
			
			
			
			pares = new Hashtable<String, String>();
			
			base = new Base(this,"dbase", null, 1);
			
			bd = base.getReadableDatabase();
			
			Cursor c = bd.rawQuery("select id,nombre_empresa, precio_actual from empresas", null);
			if (c.moveToFirst()) 
			{
				//Recorremos el cursor hasta que no haya más registros
				do 
				{
					listado.add(c.getString(1));
					precio.add(c.getString(2));
					pares.put(c.getString(0),c.getString(2));	
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
				id_empresa=listado.get(position);
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
					cantidadint=Integer.parseInt(cantidad);
					
					precio_compraint=Integer.parseInt(precio_compra);
					// TODO Auto-generated method stub
					
					pares.containsKey(id_empresa);
					
					bd = base.getWritableDatabase();
					ContentValues valores = new ContentValues();
					
					
					
					String ben=precio.get(empresa);
					float beneficio=(int) (((Double.parseDouble(ben))/precio_compraint)*100);
						
					valores.put("id_empresa", id_empresa);
					valores.put("id_cartera", cadena);
					valores.put("cantidad_accion", cantidadint);
			        valores.put("precio_compra_accion", precio_compraint);
			        valores.put("f_precio_actual_accion", precio.get(empresa));
			        valores.put("beneficio", beneficio);
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
					Intent intent=new Intent(Seleccion.this, Intermedia.class);
					Bundle b=new Bundle();
					b.putInt("id_cartera",id);
					intent.putExtras(b);
					startActivity(intent);
				}		
			});
			
		}	
		
		
		
		
		
}
