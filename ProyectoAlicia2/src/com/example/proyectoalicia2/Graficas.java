package com.example.proyectoalicia2;

import java.io.IOException;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Element;
import org.w3c.dom.Document;



import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Graficas extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.setContentView(R.layout.grafica);
//	        Document doc=null; 
//	        try {
//	        	System.out.println("ALICIA PONS MENA");
//	        	//Document doc = Jsoup.connect("https://es.finance.yahoo.com/q?s=BBVA&ql=0");
//	        	//doc = (Document) Jsoup.connect("https://es.finance.yahoo.com/q?s=BBVA&ql=0").get();
//	        } catch (IOException ex) {
//	            System.out.println("No encuentra el host");
//	        }
	        System.out.println("ALICIA PONS MENA1");
	        //String title = ((Element) doc).select("meta[name=description]").first().attr("content");
	        System.out.println("ALICIA PONS MENA2");
	        //String keyword = ((Element) doc).select("meta[name=keywords]").first().attr("content");
	        //System.out.println(title+keyword);
	    } 
	 
}
