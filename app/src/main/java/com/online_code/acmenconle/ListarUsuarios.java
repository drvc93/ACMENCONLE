package com.online_code.acmenconle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.SocioPuesto;
import Model.Usuario;
import Tasks.AutenticarTask;

public class ListarUsuarios extends AppCompatActivity {
    ArrayList<Usuario> listaUsuarios ;
    ListView lvUsuarios ;
    EditText txtSearch ;
    int EDITAR = 0 , ASIGNAR=1;
    ArrayList<String> listNombres;
    public  ArrayAdapter<String> adapterLV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_usuarios);
        setTitle("Lista  de socios y usuarios");
        lvUsuarios = (ListView) findViewById(R.id.LVUsuario);
        txtSearch = (EditText) findViewById(R.id.txtSearchUser);
        listNombres = new ArrayList<String>();
        LoadUsersLIstView();

        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               AlerOpcion(i);
            }
        });
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
                    listNombres.add(us.getDni() + " - "+us.getNombres() +" " + us.getApellidoPat() + " " + us.getApellidoMat());

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

         adapterLV = new ArrayAdapter<String>(ListarUsuarios.this,android.R.layout.simple_list_item_1,listString);
        lvUsuarios.setAdapter(adapterLV);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_usuarios, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Buscar) {

            FilterList();
        }

        return true;
    }

    public  void  FilterList (){


        ListarUsuarios.this.adapterLV.getFilter().filter(txtSearch.getText().toString());
    }


    public  void  SeleccionarOpcion (int accion, int position ){
        if (accion==EDITAR) {
            String getdni = lvUsuarios.getItemAtPosition(position).toString();
            getdni = getdni.substring(0, 8);
            getdni = getdni.trim();
            Intent intent = new Intent(ListarUsuarios.this, RegistrarUsuario.class);
            intent.putExtra("DNI", getdni);
            intent.putExtra("TipoReg", "EDIT");
            startActivity(intent);
        }

        else  if (accion==ASIGNAR){
            String  getdni  = lvUsuarios.getItemAtPosition(position).toString();
            getdni = getdni.substring(0,8);
            Usuario us = UserFromDni(getdni);
            if (us!=null){

              Intent intent = new Intent(ListarUsuarios.this , SelecSeccion.class );
                intent.putExtra("CodSocio",us.getCodigo());
                intent.putExtra("NombreSocio", us.getApellidoPat()+ " "+ us.getApellidoMat() + " " + us.getNombres());
                startActivity(intent);

            }


        }

    }


    public void AlerOpcion(final int pos) {

    /*
     * WebView is created programatically here.
     *
     * @Here are the list of items to be shown in the list
     */
        final CharSequence[] items = { "Editar datos", "Asignar puesto" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ListarUsuarios.this);
        builder.setTitle("Seleccionar opción ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                SeleccionarOpcion(item,pos);
                // will toast your selection
               // showToast("Name: " + items[item]);
                dialog.dismiss();

            }
        }).show();
    }

    public  Usuario UserFromDni (String dni) {
        Usuario result = null;
        for (int i = 0; i <listaUsuarios.size() ; i++) {

            Usuario u = listaUsuarios.get(i);

            if (u.getDni().equals(dni)){

                result = u;
                break;
            }

        }

        return  result;


    }
}
