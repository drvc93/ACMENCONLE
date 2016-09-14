package com.online_code.acmenconle;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Utils.Constantes;

public class RegistrarUsuario extends AppCompatActivity {

    EditText txtDNI,txtNombres, txtApellidoPat , txtApeMat , txtPuesto , txtCelular , txtCorreo;
    Spinner spTipoUser ;
    ActionBar actionBarGlobal;
    ActionBar actionBar;
    Button btnSiguiente ;

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
        btnSiguiente = (Button) findViewById(R.id.btnSigRegUs);
        txtCorreo = (EditText)findViewById(R.id.txtCorreoUs);
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



        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidarFomrato()==true) {
                    Intent intent = new Intent(RegistrarUsuario.this, SelecSeccion.class);
                    intent.putExtra("TipoReg", TipoReg);
                    intent.putExtra("dni",txtDNI.getText().toString());
                    intent.putExtra("nombre",txtNombres.getText().toString());
                    intent.putExtra("apePat", txtApellidoPat.getText().toString());
                    intent.putExtra("apeMat",txtApeMat.getText().toString());
                    intent.putExtra("puesto", txtPuesto.getText().toString());
                    intent.putExtra("celular" ,txtCelular.getText().toString());
                    intent.putExtra("tipoUs",spTipoUser.getSelectedItem().toString());
                    intent.putExtra("correo",txtCorreo.getText().toString());

                    startActivity(intent);
                }

            }
        });
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

    public  boolean  ValidarFomrato (){
        boolean res = true;
        String msj = "-";
        if (txtDNI.getText().toString().equals("")){

            msj = "Debe ingresar el DNI";
            res = false;
        }

        else  if ( txtNombres.getText().toString().equals("")){

            msj = "Debe ingresar los nombres";
            res = false;
        }
        else  if ( txtApellidoPat.getText().toString().equals("")){

            msj = "Debe ingresar el apellido paterno";
            res = false;
        }

        else  if ( txtApeMat.getText().toString().equals("")){

            msj = "Debe ingresar  el apellido materno";
            res = false;
        }
        else  if ( txtPuesto.getText().toString().equals("")){

            msj = "Debe ingresar el número de puesto.";
            res = false;
        }

        else  if (spTipoUser.getSelectedItemPosition()==0){

            msj= "Debe seleccionar un tipo de usuario correcto ";
            res = false;
        }

        if (msj.length()>1){

            CreateCustomToast(msj , Constantes.icon_error,Constantes.layout_error);

        }

        return  res;
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
        Toast toast = new Toast(RegistrarUsuario.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
