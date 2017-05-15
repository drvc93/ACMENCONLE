package com.online_code.acmenconle;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.ExploreByTouchHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.SocioEncuesta;
import Model.SocioPregunta;
import Tasks.GetEncuestasTask;
import Tasks.GetPreguntasTask;
import Utils.EncuestaAdapter;
import Utils.PreguntaAdapter;

public class EncuestaDetalle extends AppCompatActivity {

    SharedPreferences preferences;
    ListView lvEcnuestas;
    String codSocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_detalle);
        lvEcnuestas = (ListView) findViewById(R.id.lvDetEncuesta);
        setTitle("Lista de Preguntas...");

        preferences = PreferenceManager.getDefaultSharedPreferences(EncuestaDetalle.this);
        codSocio  = preferences.getString("CodSocio",null);
        CargarPreguntas(codSocio);
    }


    public  void  CargarPreguntas (String codSoc) {

        AsyncTask<String ,String ,ArrayList<SocioPregunta>> asyncTask;
        GetPreguntasTask getPreguntasTask = new GetPreguntasTask();
        ArrayList<SocioPregunta> listEncuesta;
        listEncuesta = null;



        try {
            asyncTask = getPreguntasTask.execute("4",codSoc , "1");
            listEncuesta = (ArrayList<SocioPregunta>)asyncTask.get();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listEncuesta !=null && listEncuesta.size()>0){

            PreguntaAdapter adapter = new PreguntaAdapter(EncuestaDetalle.this, R.layout.ly_encuestadetalle,listEncuesta);
            lvEcnuestas.setAdapter(adapter);

        }

    }
}
