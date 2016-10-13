package com.online_code.acmenconle;

import android.content.SharedPreferences;
;


import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import Model.SaldoConcepto;
import Tasks.GetSaldoPorConceptosTask;
import Tasks.GetSeccionFromSocioTask;

public class RepPagoConsolid extends AppCompatActivity {

    TextView lblRCCabNombresSoc ,lblNroPues ,lblSeccion;
    TextView lblTextSaldoD;
    TextView lblSVigilancia ,lblSAgua ,  lblServicios, lblTotal;
    SharedPreferences preferences;
    String nroPuesto , codSocio,nombreLargo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_pago_consolid);
        setTitle("Estado de cueenta - Consolidado");
        preferences = PreferenceManager.getDefaultSharedPreferences(RepPagoConsolid.this);
        nroPuesto = preferences.getString("nroPuesto",null);
        codSocio = preferences.getString("CodSocio",null);
        nombreLargo = preferences.getString("UserNombreLargo",null);
        /*Inicializando controles*/

        lblRCCabNombresSoc = (TextView)findViewById(R.id.lblRCNombreSocio);
        lblNroPues = (TextView)findViewById(R.id.lblRCNroPuesto);
        lblSeccion = (TextView)findViewById(R.id.lblRCSeccion);
        lblTextSaldoD = (TextView)findViewById(R.id.lblRCTextSaldoD);
        lblSVigilancia = (TextView) findViewById(R.id.lblRCVigilancia);
        lblSAgua = (TextView)findViewById(R.id.lblRCAgua);
        lblServicios = (TextView)findViewById(R.id.lblRCServicios);
        lblTotal  = (TextView)findViewById(R.id.lblRCTotal);

     /* FIN*/

       /*Asginando Valores */
        lblRCCabNombresSoc.setText(nombreLargo);
        lblNroPues.setText(nroPuesto);
        lblSeccion.setText(GetSeccion());
        lblTextSaldoD.setText("Saldo deudor hasta la fecha  " + ObtenerFechaHoy() );
        GetCabeceraSaldos();
        /* FIN*/

    }


    public  String ObtenerFechaHoy (){


        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(Calendar.getInstance().getTime());
   return  date;
    }
    public String GetSeccion () {
        String res = null;
        AsyncTask<String,String,String> asyncTaskSeccion ;
        GetSeccionFromSocioTask getSeccionFromSocioTask = new GetSeccionFromSocioTask();

        try {
            asyncTaskSeccion = getSeccionFromSocioTask.execute("1",codSocio,nroPuesto);
            res  = (String) asyncTaskSeccion.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (res== null && res.equals("")){

            res = "no se encontro seccion";
        }

        return  res;
    }

    public  void   GetCabeceraSaldos (){
        ArrayList<SaldoConcepto>   listSaldos = null;
        GetSaldoPorConceptosTask getSaldoPorConceptosTask = new GetSaldoPorConceptosTask();
        AsyncTask<String,String,ArrayList<SaldoConcepto>> asyncTaskSaldos  ;

        double saldoT = 0;

        try {
            asyncTaskSaldos = getSaldoPorConceptosTask.execute("2",codSocio, nroPuesto );
            listSaldos = (ArrayList<SaldoConcepto>) asyncTaskSaldos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if (listSaldos!= null  && listSaldos.size()>0){
            for (int i = 0; i <listSaldos.size() ; i++)
            {
                 SaldoConcepto s = listSaldos.get(i);
                if (s.getCodConcepto().equals("1")){

                    lblSVigilancia.setText("Cuota Vigilancia................. "+ s.getSaldoxConcepto());

                }
                else if (s.getCodConcepto().equals("2")){

                    lblSAgua.setText("Cuota Agua........................ "+s.getSaldoxConcepto());

                }

                else  if ( s.getCodConcepto().equals("3") ){

                    lblServicios.setText("Cuota servicios................... "+ s.getSaldoxConcepto());
                }

                saldoT = saldoT + Double.valueOf(s.getSaldoxConcepto());

            }

            lblTotal.setText("TOTAL ............................ "+ String.format("%.2f",saldoT));

        }


    }



}
