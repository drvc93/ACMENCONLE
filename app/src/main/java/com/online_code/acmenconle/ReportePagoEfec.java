package com.online_code.acmenconle;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.CPagos;
import Tasks.GetMaxMinFechaPTask;
import Tasks.GetReportePagosTask;
import Utils.RepPagosEfeAdapater;

public class ReportePagoEfec extends AppCompatActivity {

    String codSocio,nroPuesto,nombreLargo;
    SharedPreferences preferences;
    TextView lblCabSocio , lblCabNroPuesto , lblSeccion, txtIntervFechas;
    ListView lvRep ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_pago_efec);
        setTitle("Reporte Pago Efectuados");
        preferences = PreferenceManager.getDefaultSharedPreferences(ReportePagoEfec.this);
        nroPuesto = preferences.getString("nroPuesto",null);
        codSocio = preferences.getString("CodSocio",null);
        nombreLargo = preferences.getString("UserNombreLargo",null);
        lblCabNroPuesto = (TextView) findViewById(R.id.lblCabNroPuesto);
        lblCabSocio =  (TextView)findViewById(R.id.lblCabNombreSocio);
        lblSeccion = (TextView) findViewById(R.id.lblCabSeccion);
        txtIntervFechas = (TextView)findViewById(R.id.txtCABIntervFechas);
        lvRep = (ListView) findViewById(R.id.lvRepPagoEfectuados);

        SetFechaMaxMinPago();
        SetCabecera();
        LoadListView();



    }

    public  void SetFechaMaxMinPago (){

        String fechaMax = null, fechaMin =null ;
        AsyncTask<String,String,String>asyncTaskfechaMaxTask ;
        AsyncTask<String,String,String> asyncTaskfechaMinTask;
        GetMaxMinFechaPTask getMaxMinFechaPTask = new GetMaxMinFechaPTask();

        try {
            asyncTaskfechaMaxTask = getMaxMinFechaPTask.execute("4","MAX", codSocio, nroPuesto);
            fechaMax = (String) asyncTaskfechaMaxTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        getMaxMinFechaPTask = new GetMaxMinFechaPTask();


        try {
            asyncTaskfechaMinTask = getMaxMinFechaPTask.execute("4","MIN",codSocio,nroPuesto);
            fechaMin = (String) asyncTaskfechaMinTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        txtIntervFechas.setText("Pagos efectuados entre el "+ fechaMin + " al " +fechaMax );

    }

    public  void  SetCabecera (){

        lblCabSocio.setText(nombreLargo);
        lblCabNroPuesto.setText(nroPuesto);


    }

    public void LoadListView (){

        ArrayList<CPagos> listRPagos = null;
        AsyncTask<String,String,ArrayList<CPagos>> asyncTaskPagos ;
        GetReportePagosTask getReportePagosTask = new GetReportePagosTask();

        try {
            asyncTaskPagos = getReportePagosTask.execute("1",codSocio,nroPuesto);
            listRPagos = (ArrayList<CPagos>) asyncTaskPagos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

         if (listRPagos != null && listRPagos.size()>0){

             RepPagosEfeAdapater adapater = new RepPagosEfeAdapater(ReportePagoEfec.this,R.layout.lv_item_rep_pago_efectuados,listRPagos);
             lvRep.setAdapter(adapater);
         }

    }




}
