package com.online_code.acmenconle;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.Evento;
import Tasks.GetListaEventosTask;
import Utils.EventosAdapter;

public class ListaEventos extends AppCompatActivity {

    ListView  lvEventos ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);
        setTitle("Lista de Eventos");

         lvEventos = (ListView)findViewById(R.id.LVEentos);

        CargarEvento();

    }


    public void  CargarEvento (){

        AsyncTask<String,String,ArrayList<Evento>> asyncTask ;

        GetListaEventosTask listaEventosTask = new GetListaEventosTask();
        ArrayList<Evento> listEvent  = null ;


        try {
            asyncTask = listaEventosTask.execute("2");
            listEvent = (ArrayList<Evento>) asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

         if ( listEvent != null  && listEvent.size()> 0) {

             EventosAdapter eventosAdapter = new EventosAdapter(ListaEventos.this,R.layout.lv_detalle_calendar,listEvent);
             lvEventos.setAdapter(eventosAdapter);
         }


    }
}
