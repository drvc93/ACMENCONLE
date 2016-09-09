package com.online_code.acmenconle;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InfoMercado extends AppCompatActivity {

    Button btnInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_mercado);
        btnInfo = (Button) findViewById(R.id.btnMasInfo);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowInfoDialog();
            }
        });
    }

    public void ShowInfoDialog() {

        final Dialog dialog = new Dialog(InfoMercado.this);
        dialog.setContentView(R.layout.dialog_info_mercado);
        dialog.setTitle("Información");
        Button btnAceptar = (Button) dialog.findViewById(R.id.btnOK);

        dialog.show();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}
