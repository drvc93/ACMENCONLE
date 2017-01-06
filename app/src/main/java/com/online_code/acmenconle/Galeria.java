package com.online_code.acmenconle;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Galeria extends AppCompatActivity {

    WebView  webGaleria  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        webGaleria = (WebView)findViewById(R.id.webGaleria);
        setTitle("Galeria");
        webGaleria.setWebViewClient(new WebViewClient());
         webGaleria.getSettings().setJavaScriptEnabled(true);
         webGaleria.loadUrl("http://acmenconle.com/Imgs.aspx");
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            ActionBar  actionBar = getSupportActionBar();
            actionBar.hide();
            // Do something for lollipop and above versions
        } else{
            // do something for phones running an SDK before lollipop
        }

    }
}
