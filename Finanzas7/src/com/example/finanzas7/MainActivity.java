package com.example.finanzas7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView random;
	Base base;
	Cursor fila;
	SQLiteDatabase db;
	private Hilo r;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		random = (TextView) findViewById(R.id.random);
		final Button botoncarteras = (Button) findViewById(R.id.botoncarteras);
		final Button botonnoticias = (Button) findViewById(R.id.botonnoticias);
		final Button botongraficos = (Button) findViewById(R.id.botongraficos);
		Animation slideUpIn = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim1);
		Animation slideIn = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim2);
		Animation rotate = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim5);
		random.startAnimation(rotate);
		busqueda();//Llamada al metodo que obtiene los datos de yahoo y los inserta en la base de datos.
		//consulta();//Consulta de los valores de la base de datos de manera aleatoria.
		
		r=new Hilo(this);
		r.getText(random);
		r.execute();
		
		botoncarteras.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(MainActivity.this,ListaCartera.class);
				startActivity(intent);   
			}
		});
		
		botonnoticias.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(MainActivity.this,Noticias.class);
				startActivity(intent);   
			}
		});
		
		botongraficos.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(MainActivity.this,Graficas.class);
				startActivity(intent);   
			}
		});
		
		
		
	}

	public void busqueda() {
		String devuelve = "";
		HttpClient comunicacion = new DefaultHttpClient();

		HttpGet peticion = new HttpGet(
				"https://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20yql.query.multi%20WHERE%20queries%3D'%0A%20%20%20%20select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22BBVA%22%2C%22BKT%22%2C%22BME%22%2C%22DIA%22%2C%22ENG%22%2C%22GAM%22%2C%22GAS%22%2C%22GRF%22%2C%22IAG%22%2C%22MTS%22%2C%22REE%22%2C%22SAN%22%2C%22TEF%22%2C%22VIS%22)%3B%0A%20%20%20%20select%20*%20from%20yahoo.finance.xchange%20where%20pair%3D%22USDEUR%22%0A'%3B%0A&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
		peticion.setHeader("content-type", "application/json");

		try {

			HttpResponse respuesta = comunicacion.execute(peticion);
			String respuestaCad = EntityUtils.toString(respuesta.getEntity());
			JSONObject respuestaJSON = new JSONObject(respuestaCad);
			System.out.println(respuestaJSON);
			JSONObject resultJSONObject = respuestaJSON.getJSONObject("query")
					.getJSONObject("results");
			JSONArray resultJSON = resultJSONObject.getJSONArray("results");
			
			JSONArray valor;
			JSONArray empresa;
			ArrayList<String> listaValor = new ArrayList<String>();
			ArrayList<String> listaEmpresa = new ArrayList<String>();

			if (resultJSON.length() > 0) {

				valor = resultJSON.getJSONObject(0).getJSONArray("quote");
				empresa = resultJSON.getJSONObject(0).getJSONArray("quote");

				for (int i = 0; i < valor.length(); i++) {
					listaValor.add(valor.getJSONObject(i).getString("symbol"));
				}

				for (int i = 0; i < empresa.length(); i++) {
					listaEmpresa.add(empresa.getJSONObject(i).getString("LastTradePriceOnly"));
				}
			}

			String elemento1 = null;
			String elemento2 = null;

			Iterator<String> valorIterator = listaValor.iterator();
			Iterator<String> empresaIterator = listaEmpresa.iterator();
			Base base = new Base(this,"dbase", null, 1);
			SQLiteDatabase db = base.getWritableDatabase();
			ContentValues valores = new ContentValues();
			int count=0;
			while (valorIterator.hasNext()) {
				//valores.put("id", count);
		        valores.put("nombre_empresa", valorIterator.next());
		        valores.put("precio_actual", empresaIterator.next());
		        db.insert("empresas", null, valores);
		        count++;	
			}
			db.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

    
	//Hacemos una consulta del nombre de la empresa y del precio actual a través del id. El id lo genero aleatoriamente con valores entre 0 y 14, de manera
	//que obtendremos distintos nombres de empresa y precios según el id que salga.
//	public void consulta()
//	{
//		int id=(int) (Math.random()*14);
//		Base base = new Base(this,"dbase", null, 1);
//		SQLiteDatabase db = base.getReadableDatabase();
//        Cursor fila = db.rawQuery("select nombre_empresa, precio_actual from empresas where id="+id, null);
//        if (fila.moveToFirst()) {
//        	random.setText(fila.getString(0)+" - "+fila.getString(1));
//        } else
//            Toast.makeText(this, "No existe ese valor en la base de datos",
//                    Toast.LENGTH_LONG).show();
//        db.close();
//		
//	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	    

}
