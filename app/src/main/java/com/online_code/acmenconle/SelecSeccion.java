package com.online_code.acmenconle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.Seccion;
import Model.SocioPuesto;
import Tasks.GetSeccionesTask;
import Tasks.GetSocioPuestoTask;
import Tasks.RegistrarSocioSeccionTask;
import Tasks.RegistrarSocioTask;
import Utils.Constantes;
import Utils.PuestoSeccionAdapater;

public class SelecSeccion extends AppCompatActivity {



    ListView lvSocioPuesto ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_seccion);
        lvSocioPuesto = (ListView) findViewById(R.id.LVSecciones);


        LoadListview("1");



    }


    public  void  LoadListview (String codSocio){


        ArrayList<SocioPuesto>  lisSocioP = null;
        AsyncTask<String,String,ArrayList<SocioPuesto>> asyncTaskSocio;
        GetSocioPuestoTask getSocioPuestoTask = new GetSocioPuestoTask();
        asyncTaskSocio = getSocioPuestoTask.execute("4",codSocio);
        try {
            lisSocioP = (ArrayList<SocioPuesto>) asyncTaskSocio.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (lisSocioP!= null && lisSocioP.size()>0){

            PuestoSeccionAdapater  adapater = new PuestoSeccionAdapater(SelecSeccion.this,R.layout.lv_puesto_seccion,lisSocioP);
            lvSocioPuesto.setAdapter(adapater);
        }



    }








    public void AlerSave() {
        new AlertDialog.Builder(SelecSeccion.this)
                .setTitle("Advertencia")
                .setMessage("¿Esta seguro que desea guardar los datos?")
              //  .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {


                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();

    }

    public void CreateCustomToast(String msj, int icon, int backgroundLayout) {

        LayoutInflater infator = getLayoutInflater();
        View layout = infator.inflate(R.layout.toast_alarm_success, (ViewGroup) findViewById(R.id.toastlayout));
        TextView toastText = (TextView) layout.findViewById(R.id.txtDisplayToast);
        ImageView imgIcon = (ImageView) layout.findViewById(R.id.imgToastSucc);
        LinearLayout parentLayout = (LinearLayout) layout.findViewById(R.id.toastlayout);
        imgIcon.setImageResource(icon);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            parentLayout.setBackgroundDrawable(getResources().getDrawable(backgroundLayout));
        } else {
            parentLayout.setBackground(getResources().getDrawable(backgroundLayout));
        }


        toastText.setText(msj);
        Toast toast = new Toast(SelecSeccion.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }


}
