package com.online_code.acmenconle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.Usuario;
import Tasks.AutenticarTask;
import Utils.Constantes;

public class Login extends AppCompatActivity {


    EditText txtUser , txtPass;
    Button  btnEntrar ;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        txtUser =  (EditText) findViewById(R.id.txtUserName);
        txtPass  =(EditText) findViewById(R.id.txtPass);
        btnEntrar = (Button) findViewById(R.id.btnLogin);
        preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        //ds
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (txtUser.getText().toString().length()>0 && txtPass.getText().toString().length()>0){
                        AutenticarUsuario();

                    }

                    else {

                        CreateCustomToast("Falta completar datos.", Constantes.icon_error,Constantes.layout_error);
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }



    public  void  AutenticarUsuario () throws ExecutionException, InterruptedException {
        ArrayList<Usuario> lstUser = new ArrayList<Usuario>();
        AsyncTask<String,String,ArrayList<Usuario>> asyncTaskUsuarios  ;
        AutenticarTask autenticarTask = new AutenticarTask();

        asyncTaskUsuarios = autenticarTask.execute("1",txtUser.getText().toString(),txtPass.getText().toString());
        lstUser = (ArrayList<Usuario>) asyncTaskUsuarios.get();
        if (lstUser!= null && lstUser.size() >0){
            Usuario  user =  lstUser.get(0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("UserDni", user.getDni());
            editor.putString("TipoUser",user.getTipoUsuario());
            editor.putString("CodSocio",user.getCodigo());
            editor.putString("UserName",user.getNombres());
            editor.commit();
            Intent intent = new Intent(Login.this , MenuPrincipal.class);
            startActivity(intent);
           // CreateCustomToast("Bienvenido " + user.getNombres(), Constantes.icon_succes,Constantes.layout_success);

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
        Toast toast = new Toast(Login.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
