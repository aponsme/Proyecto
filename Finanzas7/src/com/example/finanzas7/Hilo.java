package com.example.finanzas7;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner.Result;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

public class Hilo extends AsyncTask<Void, String, Void>{
	private String saludo;
	private TextView r;
	private Context mContext;
	Base base;
	SQLiteDatabase db;
	
	protected Void doInBackground(Void... params)
	{
		
		try
		{
			while(true)
			{
				
				int id=(int) (Math.random()*14);
				base=new Base(mContext,"dbase", null, 1);
				db = base.getReadableDatabase();
		        Cursor fila = db.rawQuery("select nombre_empresa, precio_actual from empresas where id="+id, null);
		        if (fila.moveToFirst()) {
		        	saludo=fila.getString(0)+" - "+fila.getString(1);
		        	publishProgress(saludo);
					Thread.sleep(3000);
		        } 
		        db.close();
			}
			
		}catch(Exception e)
		{
			
			
		}
		return null;
	}
	
	protected void onProgressUpdate(String... values)
	{
		r.setText(saludo);
	}
	
	public void getText(TextView a)
	{
		this.r=a;
	}

	public Hilo(Context context) {
        mContext = context;
    } 
}
