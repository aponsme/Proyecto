package com.example.proyectoalicia2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

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
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView random;
	BaseDatos base;
	Cursor cursor;
	SQLiteDatabase sQLiteDatabase;
	private Hilo hilo;
	double rate;
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		random = (TextView) findViewById(R.id.random);
		final Button botoncarteras = (Button) findViewById(R.id.botoncarteras);
		final Button botonver = (Button) findViewById(R.id.botonver);
		final Button botonnoticias = (Button) findViewById(R.id.botonnoticias);
		final Button botongraficos = (Button) findViewById(R.id.botongraficos);
		final Button botonmaps = (Button) findViewById(R.id.botonmaps);

		Animation slideUpIn = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim1);

		base = new BaseDatos(this, "dbase", null, 1);
		sQLiteDatabase = base.getReadableDatabase();
		Cursor c = sQLiteDatabase.rawQuery("select count(*) from empresas",
				null);
		c.moveToFirst();
		int count = c.getInt(0);
		if (count == 0) {
			yahoo();
			c.close();
			sQLiteDatabase.close();

		} else {
			eliminar();
			yahoo();

		}
		random.startAnimation(slideUpIn);
		hilo = new Hilo(this);
		hilo.getText(random);
		hilo.execute();

		botoncarteras.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,InsertarCarteras.class);
				startActivity(intent);
			}
		});
		botonver.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ListadoCarteras.class);
				startActivity(intent);
			}
		});
		botonnoticias.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Noticias.class);
				startActivity(intent);
			}
		});

		botongraficos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Graficas.class);
				startActivity(intent);
			}
		});

		botonmaps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, Maps.class);
				startActivity(intent);
			}
		});

	}

	// Elimina los datos de la tabla empresas
	public void eliminar() {
		sQLiteDatabase = base.getWritableDatabase();
		sQLiteDatabase.execSQL("DELETE FROM empresas");
		sQLiteDatabase.close();
	}

	// Conecta con el servidor para obtener los datos de la bolsa
	public void yahoo() {
		
		HttpClient comunicacion = new DefaultHttpClient();
		HttpGet peticion = new HttpGet("https://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20yql.query.multi%20WHERE%20queries%3D'%0A%20%20%20%20select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22BBVA%22%2C%22BKT%22%2C%22BME%22%2C%22DIA%22%2C%22ENG%22%2C%22GAM%22%2C%22GAS%22%2C%22GRF%22%2C%22IAG%22%2C%22MTS%22%2C%22REE%22%2C%22SAN%22%2C%22TEF%22%2C%22VIS%22)%3B%0A%20%20%20%20select%20*%20from%20yahoo.finance.xchange%20where%20pair%3D%22USDEUR%22%0A'%3B%0A&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
		peticion.setHeader("content-type", "application/json");
		try {

			HttpResponse respuesta = comunicacion.execute(peticion);
			String respuestaCad = EntityUtils.toString(respuesta.getEntity());
			JSONObject respuestaJSON = new JSONObject(respuestaCad);
			JSONObject resultJSONObject = respuestaJSON.getJSONObject("query").getJSONObject("results");
			JSONArray resultJSON = resultJSONObject.getJSONArray("results");
			//ratestring es el valor actualizado del precio del euro en dólares
			String ratestring = resultJSON.getJSONObject(1).getJSONObject("rate").getString("Rate"); 
			rate = Double.parseDouble(ratestring);
			JSONArray quote;
			ArrayList<String> listaSimbolos = new ArrayList<String>();
			ArrayList<String> listaPrecio = new ArrayList<String>();

			if (resultJSON.length() > 0) {

				quote = resultJSON.getJSONObject(0).getJSONArray("quote");

				for (int i = 0; i < quote.length(); i++) {
					//guardo en un arraylist los símbolos de las empresas
					listaSimbolos.add(quote.getJSONObject(i).getString("symbol"));
				}

				for (int i = 0; i < quote.length(); i++) {
					//guardo en un arraylist el precio actual de la acción de cada empresa
					String lastTradePriceOnly = quote.getJSONObject(i).getString("LastTradePriceOnly");
					double lastTradePriceOnlyInt = Double.parseDouble(lastTradePriceOnly);
					double euros = rate * lastTradePriceOnlyInt;
					String redondeoeuros=String.format("%.2f", rate * lastTradePriceOnlyInt);
					//String eurosString = Double.toString(euros);
					listaPrecio.add(redondeoeuros);
				}
			}

			Iterator<String> simboloIterator = listaSimbolos.iterator();
			Iterator<String> precioIterator = listaPrecio.iterator();

			sQLiteDatabase = base.getWritableDatabase();
			ContentValues valores = new ContentValues();
			id = 0;
			while (simboloIterator.hasNext()) {
				valores.put("id", id);
				valores.put("nombre_empresa", simboloIterator.next());
				valores.put("precio_actual", precioIterator.next());
				sQLiteDatabase.insert("empresas", null, valores);
				id++;
			}
			sQLiteDatabase.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
