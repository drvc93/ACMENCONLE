package com.online_code.acmenconle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuModUsuarios extends AppCompatActivity {

    Button btnRegistrarUs , btnListUsuario ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mod_usuarios);
        setTitle("Módulo Usuarios");
        btnRegistrarUs = (Button)findViewById(R.id.btnRegUsuario);
        btnListUsuario = (Button) findViewById(R.id.btnListarUsuario);

        btnRegistrarUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MenuModUsuarios.this , RegistrarUsuario.class);
                intent.putExtra("TipoReg","NEW");
                startActivity(intent);
            }
        });

        btnListUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuModUsuarios.this, ListarUsuarios.class);
                startActivity(intent);
            }
        });
    }
}
