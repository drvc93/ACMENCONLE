package com.online_code.acmenconle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuModPago extends AppCompatActivity {

    Button btnFormPago , btnReportPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mod_pago);
        setTitle("Menu Pago");
        btnFormPago = (Button) findViewById(R.id.btnFormPago);
        btnReportPago = (Button) findViewById(R.id.btnReportPago);


        btnFormPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuModPago.this , FormularioPago.class);
                startActivity(intent);
            }
        });

    }
}
