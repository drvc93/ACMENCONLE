package com.online_code.acmenconle;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class RegistrarUsuario extends AppCompatActivity {

    EditText txtDNI,txtNombres, txtApellidoPat , txtApeMat , txtPuesto , txtCelular ;
    Spinner spTipoUser ;
    ActionBar actionBarGlobal;
    ActionBar actionBar;

    String TipoReg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        TipoReg =  getIntent().getExtras().getString("TipoReg");
        txtDNI = (EditText)findViewById(R.id.txtDniUs);
        txtNombres = (EditText)findViewById(R.id.txtNombreUs);
        txtApellidoPat = (EditText)findViewById(R.id.txtApePatUs);
        txtApeMat = (EditText)findViewById(R.id.txtApeMatUs);
        txtPuesto = (EditText)findViewById(R.id.txtPuestoUs);
        txtCelular = (EditText)findViewById(R.id.txtCelUs);
        spTipoUser = (Spinner) findViewById(R.id.spTipoUser);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(8);
        txtDNI.setFilters(filterArray);


        LoadSpinerTipoUsuario();


        if (TipoReg.equals("NEW")){

            setTitle("Registrar Nuevo Usuario");
        }
        else {
            setTitle("Modificar Usuario");
        }

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            actionBar = getSupportActionBar();
            actionBarGlobal = actionBar;
            HideToolBar();

        }
        else  {
            ActionBar ac = getSupportActionBar();
            ac.hide();
        }
    }



    public void   LoadSpinerTipoUsuario (){

        ArrayList<String> list = new ArrayList<String>();
        list.add("--SELECCIONE--");
        list.add("USUARIO");
        list.add("ADMINISTRADOR");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistrarUsuario.this,android.R.layout.simple_spinner_dropdown_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoUser.setAdapter(adapter);
        spTipoUser.setPrompt("Seleccione tipo de usuario");
    }



    public void HideToolBar() {

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                //      Toast.makeText(context,String.valueOf(l),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                actionBarGlobal.hide();
                //  getWindow().setStatusBarColor(Color.parseColor("#fc0101"));
            }
        }.start();

    }
}
