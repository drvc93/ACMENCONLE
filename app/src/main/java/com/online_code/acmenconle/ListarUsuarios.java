package com.online_code.acmenconle;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.Usuario;
import Tasks.AutenticarTask;

public class ListarUsuarios extends AppCompatActivity {
    ArrayList<Usuario> listaUsuarios ;
    ListView lvUsuarios ;
    EditText txtSearch ;
    ArrayList<String> listNombres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);
        lvUsuarios = (ListView) findViewById(R.id.LVUsuario);
        txtSearch = (EditText) findViewById(R.id.txtSearchUser);
        listNombres = new ArrayList<String>();
        LoadUsersLIstView();
    }

    public void   LoadUsersLIstView  (){

        AsyncTask<String,String,ArrayList<Usuario>> asyncTask ;
        AutenticarTask autenticarTask = new AutenticarTask();

        asyncTask = autenticarTask.execute("2","","");
        try {
            listaUsuarios = (ArrayList<Usuario>) asyncTask.get();

            if (listaUsuarios!= null && listaUsuarios.size()>0){

                for (int i = 0; i <listaUsuarios.size() ; i++) {
                    Usuario us = listaUsuarios.get(i);
                    listNombres.add(us.getNombres() +" " + us.getApellidoPat() + " " + us.getApellidoMat());

                }
                setAdapterListView(listNombres);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public  void  setAdapterListView(ArrayList<String> listString){

        ArrayAdapter<String> adapterLV = new ArrayAdapter<String>(ListarUsuarios.this,android.R.layout.simple_list_item_1,listString);
        lvUsuarios.setAdapter(adapterLV);

    }
}
