package com.online_code.acmenconle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Principal extends AppCompatActivity {
    ImageView  imgUbicacion ;
    ImageView imgHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setTitle("Bienvenido");
        imgUbicacion = (ImageView) findViewById(R.id.imgUbicacion);
        imgHome = (ImageView) findViewById(R.id.imgHome);

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Principal.this, InfoMercado.class);
                startActivity(i);
            }
        });

        imgUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(Principal.this, Ubicacion.class);
                startActivity(intent);
            }
        });
    }


}
