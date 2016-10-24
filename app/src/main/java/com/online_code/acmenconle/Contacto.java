package com.online_code.acmenconle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import Tasks.MnesajeContactoTask;
import Utils.Constantes;

public class Contacto extends AppCompatActivity {

    EditText txtNombre , txtmensaje, txtCelular , txtCorreo;
    Button  btnEnviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        setTitle("Formulario de contacto");
        txtNombre = (EditText) findViewById(R.id.txtNomPersonaC);
        txtmensaje = (EditText)findViewById(R.id.txtMsjContacto);
        txtCelular = (EditText)findViewById(R.id.txtCelContacto);
        txtCorreo = (EditText)findViewById(R.id.txtCorreoContacto);
        btnEnviar = (Button) findViewById(R.id.btntEnviarCorreo);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertaEnvioCorreo();
            }
        });

    }


    public void AlertaEnvioCorreo() {
        new AlertDialog.Builder(Contacto.this)
                .setTitle("Aviso")
                .setMessage("¿Desea enviar la informacón ?")
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                //showToast("Thank you! You're awesome too!");
                               EnviarCorreo();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }


    public  void  EnviarCorreo (){
        if (TextUtils.isEmpty(txtNombre.getText().toString())){

            txtNombre.setError("Faltó escribir su nombre");
            return;
        }

        else  if (TextUtils.isEmpty(txtmensaje.getText().toString())){

            txtmensaje.setError("Debe escribir un mensaje de referencia");
            return;

        }

        else if (TextUtils.isEmpty(txtCelular.getText().toString())){

            txtCelular.setError("Debe ingresar un numero de contacto de referencia");
            return;
        }

        else if  (TextUtils.isEmpty(txtCorreo.getText().toString())){

            txtCorreo.setError("Debre ingresar un correo de referencia.");
            return;
        }

        else{
            String result = "";
            AsyncTask<String,String,String> asyncTaskEnviar;
            MnesajeContactoTask  mnesajeContactoTask =new MnesajeContactoTask();

            try {
                asyncTaskEnviar = mnesajeContactoTask.execute(txtNombre.getText().toString(),txtCelular.getText().toString(),txtCorreo.getText().toString(),txtmensaje.getText().toString());

                result = (String) asyncTaskEnviar.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

             if (result.equals("OK")){

                CreateCustomToast( "Se envio correctamente los datos de contacto , por favor en breve revise su correo ", Constantes.icon_succes,Constantes.layout_success);
                 super.onBackPressed();
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
        Toast toast = new Toast(Contacto.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
