package com.online_code.acmenconle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    String username ,nropuesto  ;
    Button btnModUsuarios , btnModPagos,btnMasInfo,  btnModEcnuesta,btnModEvento, btnGaleria ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        setTitle("Menú Principal");
        btnModUsuarios = (Button)findViewById(R.id.btnModUsuarios);
        btnModPagos = (Button) findViewById(R.id.btnModPagos);
        btnMasInfo = (Button) findViewById(R.id.btnMasInfo);
        btnGaleria = (Button)findViewById(R.id.brnGaleria);
        btnModEvento = (Button) findViewById(R.id.btnModEventos);
        btnModEcnuesta = (Button)findViewById(R.id.btnModEncuesta);


        preferences = PreferenceManager.getDefaultSharedPreferences(MenuPrincipal.this);
        username = preferences.getString("UserName",null);
        nropuesto  = preferences.getString("nroPuesto", null);
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

        btnModPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this,MenuModPago.class);
                startActivity(intent);
            }
        });

        btnMasInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MenuPrincipal.this, MasInfo.class);
                startActivity(intent);
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecExplorer();
            }
        });

        btnModEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent  = new Intent(MenuPrincipal.this , ListaEventos.class);
                startActivity(intent);
            }

            });

        btnModEcnuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MenuPrincipal.this ,ListaEncuestas.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_prioncipal_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.InfoUser) {



        }
        if (id == R.id.CerrarSesion) {


        }

        return true;
    }



    public void SelecExplorer() {

    /*
     * WebView is created programatically here.
     *
     * @Here are the list of items to be shown in the list
     */
        final CharSequence[] items = { "Ver en App", "Ver en explorador" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
             if (item == 0) {
                 Intent i = new Intent(MenuPrincipal.this , Galeria.class);
                 startActivity(i);


             }

              else {

                 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://acmenconle.com/Imgs.aspx"));
                 startActivity(browserIntent);

             }
                // will toast your selection
               // showToast("Name: " + items[item]);
               // dialog.dismiss();

            }
        }).show();
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
