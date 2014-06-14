package com.example.proyectoalicia2;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
 
public class Noticias extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        this.setContentView(R.layout.web);
 
        WebView myWebView = (WebView) this.findViewById(R.id.webView);
        myWebView.loadUrl("http://www.infobolsa.es/");
 
    }
 
}