package com.example.proyectoalicia2;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Resultados extends ListActivity {
	TextView t1, t2, t3;
	BaseDatos base;
	SQLiteDatabase bd;
	SQLiteDatabase bd2;
	float porcentaje;
	Bundle bundle;
	String nombre;
	List<Valores> vals;
	Button borrar;
	int aux;
	int cant;
	int id_cartera;
	String id;
	Valores val;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.consulta);
		borrar = (Button) findViewById(R.id.buttonCancelar);
		base = new BaseDatos(this, "dbase", null, 1);
		val = null;
		vals = new ArrayList<Valores>();
		
		if(getIntent().getExtras()!=null)
		{
			bundle = getIntent().getExtras();
			id_cartera = bundle.getInt("id");
			id = Integer.toString(id_cartera);
			System.out.println("ESTA DENTRO DEL IF DEL BUNDLE");
			seleccionaOperaciones();
		}
		setListAdapter(new ResultadoArrayAdapter(this, vals));
		
		borrar.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				eliminar();
				Intent i = new Intent(Resultados.this, Resultados.class);
				Bundle b=new Bundle();
				b.putInt("id",id_cartera);
				i.putExtras(b);
				startActivity(i);
				finish();
			}
		});
	}
	
	
//	public void seleccionaOperaciones()
//	{
//		
//		bd = base.getReadableDatabase();
//		
//		String[] columns = { "id_empresa", "precio_compra","id_precio_actual", "porcentaje","ganancia" };
//		Cursor fila = bd.query("operaciones", columns, "id_cartera=?",new String[] { id }, null, null, null);
//		while (fila.moveToNext()) {
//			val = new Valores();
//			val.setEmpresa(fila.getString(0));
//			val.setActual(fila.getString(2));
//			val.setCompra(fila.getString(1));
//			val.setPorcentaje(fila.getString(3) + "%");
//			val.setGanancia(fila.getString(4) + "€");
//			vals.add(val);
//		}
//		bd.close();
//	}
	
	/**
	 * 
	 * @return -> Devuelve un arrayList con las empresas de la tabla operaciones referenciadas por cartera
	 */
	public void seleccionaOperaciones()
	{
		System.out.println("ENTRA EN SELECCIONA OPERACIONES");
		bd = base.getReadableDatabase();
		//OBTENEMOS DE TABLA OPERACIONES LOS SIGUIENTES CAMPOS
		String[] columns = { "fk_empresa", "precio_compra", "cantidad"};
		
		//Cogemos todas las empresas de la cartera seleccionada
		Cursor fila = bd.query("operaciones", columns, "fk_cartera=?",new String[] { id }, null, null, null);
		
		//Leo los registros de la tabla operaciones
		while (fila.moveToNext()) {
			System.out.println("ENTRA EN EL WHILE DE SELECCIONAOPERACIONES");
			val = new Valores();
			val.setEmpresa(fila.getString(0));
			val.setCompra(fila.getString(1));
			String cantidad=fila.getString(2);
			int cantidadInt=Integer.parseInt(cantidad);
			//val.setPorcentaje(fila.getString(3) + "%");
			//val.setGanancia(fila.getString(4) + "€");
			
			
			System.out.println("ASIGNA VALORES A VAL");
			
			bd2 = base.getReadableDatabase();
			System.out.println("PASA LA BASE DATOS 2");
			System.out.println(val.getEmpresa()); 
			//OBTENEMOS DE TABLA OPERACIONES LOS SIGUIENTES CAMPOS
			String[] columns1 = {"precio_actual"};
			
			//Cogemos todas las empresas de la cartera seleccionada
			Cursor fila2 = bd2.query("empresas", columns1, "nombre_empresa=?",new String[] { val.getEmpresa() }, null, null, null);
			//Leo los registros de la tabla empresas
			while (fila2.moveToNext()) {
				float bolsa=fila2.getFloat(0);
				
				//val.getCompra() lo paso a float
				String bolsaaString=String.valueOf(fila2.getFloat(0));
				porcentaje=(bolsa/Float.parseFloat(val.getCompra())*100)-100;
				String redondeoeurosporcentaje=String.format("%.2f", porcentaje);
				String porcentajeaString=String.valueOf(redondeoeurosporcentaje);
				float ganancia=(bolsa*cantidadInt)-(Float.parseFloat(val.getCompra())*cantidadInt);
				String redondeoeurosganancia=String.format("%.2f", ganancia);
				String gananciaaString=String.valueOf(redondeoeurosganancia);;
				//Muestra en la lista la lectura
				val.setActual(bolsaaString);
				val.setPorcentaje(porcentajeaString+ "%");
				val.setGanancia(gananciaaString);
				vals.add(val);
//				ContentValues valores = new ContentValues();
//				valores.put("nombre","usunuevo");
//				valores.put("nombre","usunuevo");
//				valores.put("nombre","usunuevo");
//				valores.put("nombre","usunuevo");
//				 
//				//Actualizamos el registro en la base de datos
//				bd.update("operaciones", valores, "fk_empresa=val.setEmpresa(fila.getString(0)", null);
				
				
			}
			bd2.close();
		}
		bd.close();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public void eliminar() {
		bd = base.getReadableDatabase();

		for (int i = 0; i < vals.size(); i++) {
        	if(vals.get(i).isChecked()==true)
        	{
				String[] columns ={"id"};
				Cursor fila = bd.query("operaciones", columns, "fk_empresa=?", new String[] {vals.get(i).getEmpresa() }, null, null, null);
        		//Cursor fila = bd.rawQuery("Select id from carteras where nombre_cartera="+rows.get(i).getTitle(), null);NO FUNCIONA
                while (fila.moveToNext()) 
                {
                	aux=fila.getInt(0);
                }
                cant = bd.delete("operaciones", "id="+aux , null);	
        	}
        	
        }
		bd.close();
		System.out.println("TOAST");
	      if (cant >= 1)
	      Toast.makeText(this, "Se borró la cartera con éxito",Toast.LENGTH_SHORT).show();
	      else
	      Toast.makeText(this, "Se produjo un error, vuelva a intentarlo",Toast.LENGTH_SHORT).show();
	}
	
}
