package com.online_code.acmenconle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ListaBancos extends AppCompatActivity {


    String VIGILANCIA = "1" ,TRABAJOS = "2" , OTROS = "3";
    String BCP = "1" ,BBVA = "2", INTBK ="3";
    LinearLayout  lyBBVA , lyBCP,lyINTBK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_bancos);
        setTitle("Realizar pago");

        lyBBVA = (LinearLayout)findViewById(R.id.layoutpagoBBVA);
        lyBCP = (LinearLayout) findViewById(R.id.layoutpagoBCP);
        lyINTBK = (LinearLayout)findViewById(R.id.layoutpagoINTBK);


        lyBBVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoAcitivityPago(BBVA,VIGILANCIA);
            }
        });

        lyBCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoAcitivityPago(BCP,TRABAJOS);
            }
        });

        lyINTBK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoAcitivityPago(INTBK,OTROS);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_bancos, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Volver) {
        super.onBackPressed();


        }


        return true;
    }


    public   void  GoAcitivityPago (String Banco, String concepto ){

        Intent intent = new Intent(ListaBancos.this , FormularioPago.class);
        intent.putExtra("prmCodBanco",Banco);
        intent.putExtra("prmConcepto",concepto);
        startActivity(intent);




    }
}
