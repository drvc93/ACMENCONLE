package com.online_code.acmenconle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;

public class Principal extends AppCompatActivity {
    ImageView  imgUbicacion,imgSocios ;
    ImageView imgHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setTitle("Bienvenido");
        imgUbicacion = (ImageView) findViewById(R.id.imgUbicacion);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgSocios = (ImageView) findViewById(R.id.imgSocios);

        imgSocios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this,Login.class);
                startActivity(intent);
            }
        });

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
