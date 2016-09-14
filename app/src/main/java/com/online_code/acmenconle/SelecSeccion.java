package com.online_code.acmenconle;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.Seccion;
import Tasks.GetSeccionesTask;

public class SelecSeccion extends AppCompatActivity {

    String TipoReg,DNI,Nombres,ApePat,ApeMat,Puesto,Celular,TipoUsuario,Correo;

    ArrayList<Seccion> listSeccion ;
    ListView lvSeccion ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_seccion);
        setTitle("Seleccionar secciones :");
        lvSeccion = (ListView)findViewById(R.id.LVSecciones);
        TipoReg = getIntent().getStringExtra("TipoReg");
        DNI =  getIntent().getStringExtra("dni");
        Nombres =  getIntent().getStringExtra("nombre");
        ApePat =  getIntent().getStringExtra("apePat");
        ApeMat = getIntent().getStringExtra("ApeMat");
        Puesto = getIntent().getStringExtra("puesto");
        Celular  = getIntent().getStringExtra("celular");
        TipoUsuario = getIntent().getStringExtra("tipoUs");
        Correo = getIntent().getStringExtra("correo");

        LoadListView();


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
}
