package com.example.finanzas7;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Consulta extends ListActivity{
	TextView t1,t2,t3; 
	Base base;
	SQLiteDatabase bd;
	int porcentaje;
	Bundle bundle;
	String nombre;
	List <Valores> vals;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.setContentView(R.layout.consulta);
	        bundle=getIntent().getExtras();
			int id1=bundle.getInt("id");
			String id=Integer.toString(id1);
			System.out.println("EL ID DE LA CARTERA SELECCIONADA ES "+id);
			vals = new ArrayList<Valores>();
			Valores val = null;
			base = new Base(this,"dbase", null, 1);
			bd = base.getReadableDatabase();
			String[] columns ={"id_empresa","precio_compra_accion","f_precio_actual_accion","beneficio"};
			Cursor fila = bd.query("operaciones", columns, "id_cartera=?", new String[] { id }, null, null, null);
			while (fila.moveToNext()) {
				val = new Valores();
	        	val.setEmpresa(fila.getString(0));
	        	val.setActual(fila.getString(2)); 
	        	val.setCompra(fila.getString(1));
	        	val.setBeneficio(fila.getString(3));
	        	vals.add(val); 
	        } 
	        bd.close();
			setListAdapter(new ListaArrayAdapter(this, vals));
			
	    }
	
}
