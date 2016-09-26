package com.online_code.acmenconle;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
    EditText txtFecha, txtMensaje, txtMontoPago;
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
        txtMontoPago = (EditText) findViewById(R.id.txtMontoPago);
        txtMontoPago.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        LoadSpinerBancos();
        LoadSpinerConcepto();

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelecFecha();

            }
        });
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
                Banco b =  listBancos.get(i);
                dataBancos.add(b.getCodBanco() + "  - "+b.getNombreLargo());
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
                ConceptoPago c = listConceptos.get(i);
                dataConceptos.add(c.getCodConcepto() + "  - " + c.getDescripcion());

            }

            ArrayAdapter<String> adapterConceptos   = new ArrayAdapter<String>(FormularioPago.this,android.R.layout.simple_spinner_dropdown_item,dataConceptos);
            adapterConceptos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spConcepto.setPrompt("Seleccionar concepto pago");
            spConcepto.setAdapter(adapterConceptos);
        }



    }

    public void SelecFecha() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(FormularioPago.this).setView(view)
                .setTitle("Seleccionar Fecha")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    // @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();
                        String mes = String.format("%02d", month);

                        String dia = String.format("%02d", day);
                        txtFecha.setText(dia + "/" + mes + "/" + String.valueOf(year));

                           // FinicioGlobal = String.valueOf(year) + "-" + mes + "-" + dia;
                          //  FFinGlobal = String.valueOf(year) + "-" + mes + "-" + dia;
                         //   Log.i("Fecha global FIN => ", FFinGlobal);

                        dialog.cancel();

                    }

                }).show();
    }
}
