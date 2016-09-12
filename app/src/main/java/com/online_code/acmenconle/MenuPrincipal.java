package com.online_code.acmenconle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import Utils.Constantes;

public class MenuPrincipal extends AppCompatActivity {
    SharedPreferences preferences;
    String username  ;
    Button btnModUsuarios , btnModPagos,  btnModEcnuesta,btnModEvento ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        setTitle("Menú Principal");
        btnModUsuarios = (Button)findViewById(R.id.btnModUsuarios);



        preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
        username = preferences.getString("UserName",null);
        if (username!= null && username.length()>0){

            CreateCustomToast("Bienvenido "+ username, Constantes.icon_succes,Constantes.layout_success);
        }


        btnModUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(MenuPrincipal.this , MenuModUsuarios.class);
                startActivity(intent);
            }
        });
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
        Toast toast = new Toast(MenuPrincipal.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
