package com.example.proyectoalicia2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {

	public BaseDatos(Context context, String name, CursorFactory factory, int version) {
		super(context, "dbase", factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE IF NOT EXISTS carteras (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nombre_cartera  VARCHAR(30));");

		db.execSQL("CREATE TABLE IF NOT EXISTS empresas (" + "id INTEGER PRIMARY KEY,"
				+ "nombre_empresa VARCHAR(30),"
				+ "precio_actual DECIMAL(7));");
		
//		 db.execSQL("CREATE TABLE IF NOT EXISTS operaciones (id INTEGER PRIMARY KEY AUTOINCREMENT," +
//		 "fk_empresa INTEGER PRIMARY KEY," +
//		 "fk_cartera INTEGER PRIMARY KEY," +
//		 "cantidad INT(7)," +
//		 "precio_compra DECIMAL(7),"+
//		 "FOREIGN KEY (fk_empresa) REFERENCES empresas(id)," + 
//		 "FOREIGN KEY (fk_cartera) REFERENCES carteras (id));");
		
		 db.execSQL("CREATE TABLE IF NOT EXISTS operaciones (" +
				 "fk_empresa INTEGER NOT NULL," +
				 "fk_cartera INTEGER NOT NULL," +
				 "cantidad INT(7)," +
				 "precio_compra DECIMAL(7),"+
				 "PRIMARY KEY (fk_empresa,fk_cartera)," +
				 "FOREIGN KEY (fk_empresa) REFERENCES empresas(id)," + 
				 "FOREIGN KEY (fk_cartera) REFERENCES carteras (id));");
		 
	       
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS carteras");
		db.execSQL("DROP TABLE IF EXISTS empresas");
		db.execSQL("DROP TABLE IF EXISTS operaciones");
		onCreate(db);
	}

}
