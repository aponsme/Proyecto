package com.example.proyectoserpisfinances;

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
	
	protected Void doInBackground(Void... params)
	{
		
		try
		{
			while(true)
			{
				int id=(int) (Math.random()*14);
				base=new BaseDatos(context,"dbase", null, 1);
				sQLiteDataBase = base.getReadableDatabase();
		        Cursor fila = sQLiteDataBase.rawQuery("select nombre_empresa, precio_actual from empresas where id="+id, null);
		        if (fila.moveToFirst()) {
		        	aleatorio=fila.getString(0)+" - "+fila.getString(1);
		        	publishProgress(aleatorio);
					Thread.sleep(3000);
		        } 
		        sQLiteDataBase.close();
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
