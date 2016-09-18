package com.online_code.acmenconle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.util.concurrent.ExecutionException;

import Model.Usuario;
import Tasks.AutenticarTask;
import Utils.Constantes;

public class RegistrarUsuario extends AppCompatActivity {

    EditText txtDNI,txtNombres, txtApellidoPat , txtApeMat , txtPuesto , txtCelular , txtCorreo;
    Spinner spTipoUser ;
    ActionBar actionBarGlobal;
    ActionBar actionBar;
    Button btnSiguiente ;
    LinearLayout butonBar;

    String TipoReg, dni ;
    String codSocio ;

    private String TAG = RegistrarUsuario.class.getSimpleName();
    float initialX, initialY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        butonBar = (LinearLayout)findViewById(R.id.buttonBarRegUs);
        butonBar.setVisibility(View.INVISIBLE);
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
            dni = getIntent().getExtras().getString("DNI");
            CargarDatosSocio(dni);
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
                    if (TipoReg.equals("EDIT")){
                        intent.putExtra("codSocio", codSocio);
                    }


                    startActivity(intent);
                }

            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //mGestureDetector.onTouchEvent(event);

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);
        return  true;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
           // butonBar.setVisibility(View.VISIBLE);
            //set immersive mode here, or whatever...
        }
        else  if (event.getAction() == MotionEvent.ACTION_UP) {
            butonBar.setVisibility(View.VISIBLE);
            HideButonBar();
        }
        return super.dispatchTouchEvent(event);
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

    public void HideButonBar() {

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                //      Toast.makeText(context,String.valueOf(l),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
               butonBar.setVisibility(View.INVISIBLE);
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


    public  void  CargarDatosSocio (String DNI){
        ArrayList<Usuario>   listUser = null;
        Usuario  us ;
        AutenticarTask  getUser = new AutenticarTask() ;
        AsyncTask<String,String,ArrayList<Usuario>>  asyncTaskSocio ;

        try {
            asyncTaskSocio =  getUser.execute("3", DNI, "");
            listUser = asyncTaskSocio.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listUser != null && listUser.size()>0){
            us = listUser.get(0);
            txtDNI.setText(us.getDni());
            txtNombres.setText(us.getNombres());
            txtApellidoPat.setText(us.getApellidoPat());
            txtApeMat.setText(us.getApellidoMat());
            txtPuesto.setText(us.getPuesto());
            txtCelular.setText(us.getCelular());
            txtCorreo.setText(us.getCorreo());
             codSocio = us.getCodigo();
           if (us.getTipoUsuario().equals("ADM")){
              spTipoUser.setSelection(2);
           }
           else {
                spTipoUser.setSelection(1);
           }
        }
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
