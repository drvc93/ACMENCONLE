package com.online_code.acmenconle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import Tasks.GetSeccionesTask;
import Tasks.RegistrarSocioSeccionTask;
import Tasks.RegistrarSocioTask;
import Utils.Constantes;

public class SelecSeccion extends AppCompatActivity {

    String TipoReg,DNI,Nombres,ApePat,ApeMat,Puesto,Celular,TipoUsuario,Correo,UserReg;

    ArrayList<Seccion> listSeccion ;
    ListView lvSeccion ;
    Button  btnRegistrarUsuario ;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_seccion);
        setTitle("Seleccionar secciones :");
        preferences = PreferenceManager.getDefaultSharedPreferences(SelecSeccion.this);
        btnRegistrarUsuario =(Button) findViewById(R.id.btnRegUsuario);
        UserReg = preferences.getString("UserDni",null);
        lvSeccion = (ListView)findViewById(R.id.LVSecciones);
        TipoReg = getIntent().getStringExtra("TipoReg");
        DNI =  getIntent().getStringExtra("dni");
        Nombres =  getIntent().getStringExtra("nombre");
        ApePat =  getIntent().getStringExtra("apePat");
        ApeMat = getIntent().getStringExtra("apeMat");
        Puesto = getIntent().getStringExtra("puesto");
        Celular  = getIntent().getStringExtra("celular");
        TipoUsuario = getIntent().getStringExtra("tipoUs");
        TipoUsuario = TipoUsuario.substring(0,3);
        Correo = getIntent().getStringExtra("correo");

        LoadListView();

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlerSave();
            }
        });

    }

    public  void LoadListView (){
        AsyncTask<String,String,ArrayList<Seccion>> listAsyncTask;
        GetSeccionesTask getSeccionesTask = new GetSeccionesTask();


        try {
            listAsyncTask = getSeccionesTask.execute("1","");
            listSeccion = listAsyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if ( listSeccion!= null && listSeccion.size()>0){
            ArrayList<String> arrayString = GetArrayForAdapter();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelecSeccion.this,android.R.layout.simple_list_item_multiple_choice,arrayString);
            lvSeccion.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            lvSeccion.setAdapter(adapter);



        }

    }


    public  ArrayList<String> GetArrayForAdapter (){

        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < listSeccion.size(); i++) {

            Seccion  s = listSeccion.get(i);
            res.add(s.getDescripcion());
        }

        return  res;
    }

    public   void  RegistrarUsuario (){
        SparseBooleanArray sparseBooleanArray = lvSeccion.getCheckedItemPositions();
        String resultRegSocio="0";
        AsyncTask<String,String,String> asyncTaskRegSocio;
        RegistrarSocioTask registrarSocioTask= new RegistrarSocioTask();
        int cont = 0;

        try {
            asyncTaskRegSocio = registrarSocioTask.execute(TipoReg,"",DNI,Nombres,ApePat,ApeMat,Puesto,Celular,TipoUsuario,UserReg,Correo);
            resultRegSocio = (String)asyncTaskRegSocio.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (Integer.valueOf(resultRegSocio)>0){

            AsyncTask<String,String,String> asyncTaskRegSeccionSocio ;
            for (int i = 0; i < lvSeccion.getCount() ; i++) {
                String resultSecSocio ="";
                if (sparseBooleanArray.get(i)==true){

                    RegistrarSocioSeccionTask registrarSocioSeccionTask = new RegistrarSocioSeccionTask();

                    try {
                        asyncTaskRegSeccionSocio = registrarSocioSeccionTask.execute(resultRegSocio,listSeccion.get(i).getCodigo());
                        resultSecSocio = (String) asyncTaskRegSeccionSocio.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    if (resultSecSocio.equals("OK")){
                        cont = cont+1;
                    }
                }



            }




        }

        if (cont>0){
            CreateCustomToast("Se registro correctamente ", Constantes.icon_succes,Constantes.layout_success);
            Intent iintent = new Intent(SelecSeccion.this,MenuPrincipal.class);
            startActivity(iintent);
        }
        else {

            CreateCustomToast("No se pudo registrar correctamente" , Constantes.icon_error,Constantes.layout_error);

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
                               RegistrarUsuario();

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
