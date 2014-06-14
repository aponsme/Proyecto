package com.example.proyectoalicia2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner.Result;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class Hilo extends AsyncTask<Void, String, Void>{
	private String aleatorio;
	
	private TextView hilo;
	private Context context;
	BaseDatos base;
	SQLiteDatabase sQLiteDataBase;
	ArrayList<String>  lista;
	protected Void doInBackground(Void... params)
	{
		lista=new ArrayList<String>();
		try
		{
			while(true)
			{
				base=new BaseDatos(context,"dbase", null, 1);
				sQLiteDataBase = base.getReadableDatabase();
		        Cursor fila = sQLiteDataBase.rawQuery("select nombre_empresa, precio_actual from empresas", null);
		        while (fila.moveToNext()) {
		        	lista.add(fila.getString(0)+" - "+fila.getString(1));
		        } 
		        sQLiteDataBase.close();
		        
		        Collections.shuffle(lista);
		        aleatorio= lista.get(0);
	        	publishProgress(aleatorio);
				Thread.sleep(3000);  
			}
			
		}catch(Exception e)
		{
			
			
		}
		return null;
	}
	
	protected void onProgressUpdate(String... values)
	{
		hilo.setText(aleatorio+" €");
	}
	
	public void getText(TextView a)
	{
		this.hilo=a;
	}

	public Hilo(Context context) {
		this.context = context;
    } 
}
