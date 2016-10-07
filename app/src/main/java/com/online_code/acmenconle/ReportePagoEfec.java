package com.online_code.acmenconle;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.CPagos;
import Tasks.GetMaxMinFechaPTask;
import Tasks.GetReportePagosTask;
import Tasks.GetSeccionFromSocioTask;
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
        ActionBar  actionBar = getSupportActionBar();
        if (GetDisplaySize() < (45/10)) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            actionBar.hide();
        } else {
           // actionBar.show();
        }
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
        lblSeccion.setText(GetSeccion());


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

    public String GetSeccion () {
         String res = null;
            AsyncTask<String,String,String>asyncTaskSeccion ;
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

    public double GetDisplaySize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double) width / (double) dens;
        double hi = (double) height / (double) dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);
        Log.i("Pulgadas => ", String.valueOf(screenInches));
        return screenInches;
    }




}
