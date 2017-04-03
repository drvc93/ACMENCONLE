package com.online_code.acmenconle;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.SocioEncuesta;
import Tasks.GetEncuestasTask;
import Utils.EncuestaAdapter;

public class ListaEncuestas extends AppCompatActivity {
    SharedPreferences preferences;
    ListView lvEcnuestas;
    String codSocio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_encuestas);

        lvEcnuestas = (ListView) findViewById(R.id.LVlistaencuestas);
        setTitle("Lista de Encuestas...");

        preferences = PreferenceManager.getDefaultSharedPreferences(ListaEncuestas.this);
        codSocio  = preferences.getString("CodSocio",null);

        CargarEncuestas(codSocio);

    }


    public  void  CargarEncuestas (String codSoc){

        AsyncTask<String ,String ,ArrayList<SocioEncuesta>> asyncTask;
        GetEncuestasTask  getEncuestasTask = new GetEncuestasTask();
        ArrayList<SocioEncuesta> listEncuesta;
        listEncuesta = null;



        try {
            asyncTask = getEncuestasTask.execute("3",codSoc);
            listEncuesta = (ArrayList<SocioEncuesta>)asyncTask.get();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listEncuesta !=null && listEncuesta.size()>0){

            EncuestaAdapter adapter = new EncuestaAdapter(ListaEncuestas.this, R.layout.lv_encuestacab,listEncuesta);
            lvEcnuestas.setAdapter(adapter);

        }
    }
}
