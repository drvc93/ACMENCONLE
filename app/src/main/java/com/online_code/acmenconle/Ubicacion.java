package com.online_code.acmenconle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Ubicacion extends AppCompatActivity {

    WebView WEBUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);
        setTitle("Ubicación");
        WEBUbicacion = (WebView)findViewById(R.id.webUbicacion);
        WEBUbicacion.setWebViewClient(new WebViewClient());
        WEBUbicacion.getSettings().setJavaScriptEnabled(true);
        WEBUbicacion.loadUrl("https://www.google.com.pe/maps/place/Av+Jos%C3%A9+Gabriel+Condorcanqui+555,+Distrito+de+La+Esperanza/@-8.0850023,-79.0437922,21z/data=!4m5!3m4!1s0x91ad3dc2cf5a9513:0xf44b23c740e2a5f1!8m2!3d-8.0849469!4d-79.0436715");

    }
}
