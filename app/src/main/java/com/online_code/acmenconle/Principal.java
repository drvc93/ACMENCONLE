package com.online_code.acmenconle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Principal extends AppCompatActivity {
    ImageView  imgUbicacion ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setTitle("Bienvenido");
        imgUbicacion = (ImageView) findViewById(R.id.imgUbicacion);

        imgUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(Principal.this, Ubicacion.class);
                startActivity(intent);
            }
        });
    }


}
