package com.example.proyectoserpisfinances;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class InsertarCarteras extends ListActivity {
	List<Row> rows;
	BaseDatos base;
	SQLiteDatabase bd;
	EditText edit;
	int cant;
	int aux;

	@SuppressWarnings("null")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		edit = (EditText) findViewById(R.id.edit);
		final Button anadir = (Button) findViewById(R.id.anadir);
		final Button borrar = (Button) findViewById(R.id.buttonCancelar);
		rows = new ArrayList<Row>();
		Row row = null;
		base = new BaseDatos(this, "dbase", null, 1);
		bd = base.getReadableDatabase();
		Cursor fila = bd.rawQuery("select nombre_cartera from carteras", null);

		while (fila.moveToNext()) {
			row = new Row();
			row.setTitle(fila.getString(0));
			rows.add(row);
		}

		bd.close();
		setListAdapter(new CustomArrayAdapter(this, rows));
		anadir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alta();
				Intent i = new Intent(InsertarCarteras.this, InsertarCarteras.class);
				startActivity(i);
				finish();
			}
		});
		borrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				baja();
				Intent i = new Intent(InsertarCarteras.this, InsertarCarteras.class);
				startActivity(i);
				finish();
			}
		});
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Intent i = new Intent(InsertarCarteras.this, PedirDatos.class);
				bd = base.getReadableDatabase();
				String[] columns = { "id" };
				Cursor fila = bd.query("carteras", columns, "nombre_cartera=?",
						new String[] { rows.get(position).getTitle() }, null,
						null, null);

				while (fila.moveToNext()) {
					aux = fila.getInt(0);
				}
				bd.close();
				Bundle b = new Bundle();
				b.putInt("id", aux);
				i.putExtras(b);
				startActivity(i);
			}
		});

	}

	public void alta() {
		bd = base.getWritableDatabase();
		String nombre = edit.getText().toString();
		ContentValues registro = new ContentValues();
		registro.put("nombre_cartera", nombre);
		bd.insert("carteras", null, registro);
		bd.close();
		edit.setText("");
		Toast.makeText(this, "Cartera insertada con éxito", Toast.LENGTH_LONG).show();
	}

	public void baja() {
		base = new BaseDatos(this, "dbase", null, 1);
		bd = base.getReadableDatabase();

		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).isChecked() == true) {
				String[] columns = { "id" };
				Cursor fila = bd.query("carteras", columns, "nombre_cartera=?",
						new String[] { rows.get(i).getTitle() }, null, null,
						null);
				// Cursor fila =
				// bd.rawQuery("Select id from carteras where nombre_cartera="+rows.get(i).getTitle(),
				// null);NO FUNCIONA
				while (fila.moveToNext()) {
					aux = fila.getInt(0);
				}
				cant = bd.delete("carteras", "id=" + aux, null);
			}

		}
		bd.close();
		if (cant >= 1)
			Toast.makeText(this, "Se borró la cartera con éxito",
					Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "Se produjo un error, vuelva a intentarlo",
					Toast.LENGTH_SHORT).show();

	}
}
