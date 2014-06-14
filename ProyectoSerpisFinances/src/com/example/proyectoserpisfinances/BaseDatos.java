package com.example.proyectoserpisfinances;

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
		
		 db.execSQL("CREATE TABLE IF NOT EXISTS operaciones (id INTEGER PRIMARY KEY AUTOINCREMENT," +
		 "id_empresa VARCHAR(30)," +
		 "id_cartera INT(6)," +
		 "cantidad INT(7)," +
		 "precio_compra DECIMAL(7)," +
		 "id_precio_actual DECIMAL(7)," +
		 "porcentaje DECIMAL(7)," + 
		 "ganancia DECIMAL(7));");
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
