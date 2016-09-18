package com.online_code.acmenconle;

import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.Banco;
import Model.ConceptoPago;
import Tasks.GetBancosTask;
import Tasks.GetConceptosPagoTask;

public class FormularioPago extends AppCompatActivity {

    ArrayList<Banco> listBancos;
    ArrayList<ConceptoPago> listConceptos;
    Spinner spBanco, spConcepto;
    EditText txtFecha, txtMensaje;
    Button btnRegPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pago);
        setTitle("Formulario de pago");
        spBanco = (Spinner) findViewById(R.id.spBanco);
        spConcepto = (Spinner) findViewById(R.id.spConcepto);

        txtFecha = (EditText) findViewById(R.id.txtFechaPago);
        txtMensaje = (EditText) findViewById(R.id.txtMensajePago);
        btnRegPago = (Button) findViewById(R.id.btnRegPago);

        LoadSpinerBancos();
        LoadSpinerConcepto();
    }

    public void LoadSpinerBancos() {

        AsyncTask<String, String, ArrayList<Banco>> asyncTaskBancos;
        GetBancosTask getBancosTask = new GetBancosTask();


        try {
            asyncTaskBancos = getBancosTask.execute("1", "0");
            listBancos = (ArrayList<Banco>) asyncTaskBancos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listBancos != null && listBancos.size() > 0) {
            ArrayList<String> dataBancos = new ArrayList<String>();
            for (int i = 0; i < listBancos.size(); i++) {

                dataBancos.add(listBancos.get(i).getNombreLargo());
            }

            ArrayAdapter<String> adaparteBanco = new ArrayAdapter<String>(FormularioPago.this, android.R.layout.simple_spinner_dropdown_item, dataBancos);
            adaparteBanco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spBanco.setPrompt("Seleccionar Banco");
            spBanco.setAdapter(adaparteBanco);


        }


    }


    public  void  LoadSpinerConcepto (){
        AsyncTask<String,String,ArrayList<ConceptoPago>>  asyncConceptos ;
        GetConceptosPagoTask getConceptostask = new GetConceptosPagoTask();


        try {
            asyncConceptos = getConceptostask.execute("1","0");
            listConceptos = (ArrayList<ConceptoPago>) asyncConceptos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listConceptos != null && listConceptos.size()>0){

            ArrayList<String> dataConceptos  = new ArrayList<String>();
            for (int i = 0; i <listConceptos.size() ; i++) {

                dataConceptos.add(listConceptos.get(i).getDescripcion());

            }

            ArrayAdapter<String> adapterConceptos   = new ArrayAdapter<String>(FormularioPago.this,android.R.layout.simple_spinner_dropdown_item,dataConceptos);
            adapterConceptos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spConcepto.setPrompt("Seleccionar concepto pago");
            spConcepto.setAdapter(adapterConceptos);
        }



    }
}
