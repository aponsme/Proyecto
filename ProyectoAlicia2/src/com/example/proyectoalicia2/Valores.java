package com.example.proyectoalicia2;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

public class Valores {
	BaseDatos base;
	SQLiteDatabase bd;
	private String empresa;
	private String actual;  
	private String compra;
	private String porcentaje;
	private String ganancia;
	private boolean checked; 
	
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String arg) {
		this.empresa = arg;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String arg) { 
		this.actual = arg;
	}

	public String getCompra() {
		return compra;
	}

	public void setCompra(String arg) {
		this.compra = arg;
	}


	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String arg) {
		this.porcentaje = arg;
	}
	public String getGanancia() {
		return ganancia;
	}

	public void setGanancia(String arg) {
		this.ganancia = arg;
	}
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	
//	/*-------------------------------------*/
//	public float calcGanancia(){
//
//		Resultados res = new Resultados();
//		ArrayList<Valores> empresas = res.seleccionaOperaciones();
//		/*
//		 * Consulata a la bd tabla operaciones para obtener los datos
//		 * Calcular el porcentaje y la gancia y asignarselo al objeto
//		 * 
//		 */
//		
//		for ( Valores a : empresas){
//			ganancia=((Float.parseFloat(precioString))*cantidadInt)-(precio_compraFloat*cantidadInt);
//		}
//		
//	
//	}
//
//	
//	public float calcPorcentaje(){
//		porcentaje=(int) (((Float.parseFloat(precioString))/precio_compraFloat)*100)-100;
//	}

}