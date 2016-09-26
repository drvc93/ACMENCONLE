package com.online_code.acmenconle;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.SocioPuesto;
import Tasks.GetSocioPuestoTask;
import Tasks.RegistrarSocioPuestoTask;
import Utils.Constantes;
import Utils.PuestoSeccionAdapater;

public class SelecSeccion extends AppCompatActivity {



    ListView lvSocioPuesto ;
    PuestoSeccionAdapater  adapater;
    SharedPreferences preferences;
    String userReg;
    String codSocio ,UserNames;
    Button btnRgUsuarioP ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_seccion);
        lvSocioPuesto = (ListView) findViewById(R.id.LVSecciones);
        btnRgUsuarioP = (Button)findViewById(R.id.btnRegUsuarioP);
        preferences = PreferenceManager.getDefaultSharedPreferences(SelecSeccion.this);
        userReg = preferences.getString("UserDni",null);
        codSocio = getIntent().getStringExtra("CodSocio");
        UserNames = getIntent().getStringExtra("NombreSocio");
        setTitle(UserNames);
        LoadListview(codSocio);


         btnRgUsuarioP.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 AlerSave(codSocio);
             }
         });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_puestos_seccion, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Guardar) {

            AlerSave(codSocio);

        }
        if (id == R.id.Agregar) {

        adapater.AddItem();
        }

        return true;
    }

    public  void  LoadListview (String codSocio){


        ArrayList<SocioPuesto>  lisSocioP = null;
        AsyncTask<String,String,ArrayList<SocioPuesto>> asyncTaskSocio;
        GetSocioPuestoTask getSocioPuestoTask = new GetSocioPuestoTask();
        asyncTaskSocio = getSocioPuestoTask.execute("4",codSocio);
        try {
            lisSocioP = (ArrayList<SocioPuesto>) asyncTaskSocio.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (lisSocioP!= null && lisSocioP.size()>0){

              adapater = new PuestoSeccionAdapater(SelecSeccion.this,R.layout.lv_puesto_seccion,lisSocioP);
            lvSocioPuesto.setAdapter(adapater);
        }
        else {
            adapater = new PuestoSeccionAdapater(SelecSeccion.this,R.layout.lv_puesto_seccion,lisSocioP);
            adapater.AddItem();
            lvSocioPuesto.setAdapter(adapater);
        }



    }


    public  void RegistrarSocioPuesto (String codSocio){
        int cont = 0 ;
        ArrayList<SocioPuesto> data = adapater.GetAllData();
        if (validarDatos(data) ==true) {
            for (int i = 0; i < data.size(); i++) {
                SocioPuesto sc = data.get(i);
                String res = "";
                AsyncTask<String, String, String> asynckRegSocioPuesto;
                RegistrarSocioPuestoTask regSocioPuestoTask = new RegistrarSocioPuestoTask();

                try {
                    asynckRegSocioPuesto = regSocioPuestoTask.execute(codSocio, sc.getCodNumeroPuesto(), sc.getCodSeccion(), userReg);
                    res = asynckRegSocioPuesto.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                if (res.equals("OK")) {

                    cont = cont + 1;
                }

            }

            if (cont > 0) {

                CreateCustomToast("Se registro correctamente los datos ", Constantes.icon_succes, Constantes.layout_success);

            } else if (cont <= 0) {

                CreateCustomToast("Error al registrar ", Constantes.icon_error, Constantes.layout_error);

            }

        }
        else {

            CreateCustomToast("Faltan llenar algunos datos ..." , Constantes.icon_warning,Constantes.layot_warning);

        }


    }

    public boolean  validarDatos (ArrayList<SocioPuesto> listSP){
        boolean  result = true;

        for (int i = 0; i < listSP.size(); i++) {
            SocioPuesto sc = listSP.get(i);
            if (sc.getCodNumeroPuesto().equals("") || sc.getCodNumeroPuesto().equals("")){

                result = false;
                break;
            }


        }

        return  result;
    }

    public void AlerSave(final String codSocio) {
        new AlertDialog.Builder(SelecSeccion.this)
                .setTitle("Advertencia")
                .setMessage("¿Esta seguro que desea guardar los datos?")
              //  .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                RegistrarSocioPuesto(codSocio);
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
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
        Toast toast = new Toast(SelecSeccion.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }


}
