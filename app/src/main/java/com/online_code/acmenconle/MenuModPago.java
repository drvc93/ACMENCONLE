package com.online_code.acmenconle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuModPago extends AppCompatActivity {

    Button btnFormPago , btnReportPago;
    int REP_PAGOS_EFECTUADOS = 0;

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
                Intent intent = new Intent(MenuModPago.this , ListaBancos.class);
                startActivity(intent);
            }
        });

        btnReportPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecTipoReporte();
            }
        });

    }


    public void SelecTipoReporte() {


        final CharSequence[] items = { "Reporte de pagos efectuados" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuModPago.this);
        builder.setTitle("SELECCIONE TIPO DE REPORTE");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                // will toast your selection
               goReport(item);
                dialog.dismiss();

            }
        }).show();
    }

    public void   goReport (int Rep){

        if (Rep == REP_PAGOS_EFECTUADOS){

            Intent  intent = new Intent(MenuModPago.this , ReportePagoEfec.class);
            startActivity(intent);
        }

    }
}
