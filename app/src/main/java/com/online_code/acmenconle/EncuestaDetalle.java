package com.online_code.acmenconle;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.v4.widget.ExploreByTouchHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.SocioEncuesta;
import Model.SocioPregunta;
import Tasks.GetEncuestasTask;
import Tasks.GetPreguntasTask;
import Tasks.InsertarSocioEncuestaTask;
import Utils.Constantes;
import Utils.EncuestaAdapter;
import Utils.PreguntaAdapter;

public class EncuestaDetalle extends AppCompatActivity {

    SharedPreferences preferences;
    ListView lvEcnuestas;
    String codSocio, codEncuesta;
    PreguntaAdapter adapter;
    ArrayList<SocioPregunta> dataF ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta_detalle);
        lvEcnuestas = (ListView) findViewById(R.id.lvDetEncuesta);
        setTitle("Lista de Preguntas...");
        codEncuesta = getIntent().getExtras().getString("codEncuesta");
        preferences = PreferenceManager.getDefaultSharedPreferences(EncuestaDetalle.this);
        codSocio  = preferences.getString("CodSocio",null);
        CargarPreguntas(codSocio);
    }


    public  void  CargarPreguntas (String codSoc) {

        AsyncTask<String ,String ,ArrayList<SocioPregunta>> asyncTask;
        GetPreguntasTask getPreguntasTask = new GetPreguntasTask();
        ArrayList<SocioPregunta> listEncuesta;
        listEncuesta = null;



        try {
            asyncTask = getPreguntasTask.execute("4",codSoc , "1");
            listEncuesta = (ArrayList<SocioPregunta>)asyncTask.get();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listEncuesta !=null && listEncuesta.size()>0){

             adapter = new PreguntaAdapter(EncuestaDetalle.this, R.layout.ly_encuestadetalle,listEncuesta);
            lvEcnuestas.setAdapter(adapter);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_encuesta_guardar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {

            AlerSave();

        }


        return true;
    }

    public void AlerSave() {
        new AlertDialog.Builder(EncuestaDetalle.this)
                .setTitle("Advertencia")
                .setIcon(R.drawable.icn_alert)
                .setMessage("¿Esta seguro que desea  guardar la encuesta?")
                //  .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                               RegistrarEncueta();
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();

    }


    public  void  RegistrarEncueta (){

        dataF = adapter.getAlldata();
        int cont = 0 ;
        int contError = 0;
        String res =  "";
        for (int i = 0  ;  i < dataF.size() ; i++ ){
            AsyncTask<String,String,String> asyncTaskInsertEnc;
            InsertarSocioEncuestaTask insertarSocioEncuestaTask  =  new InsertarSocioEncuestaTask();
            String orden = dataF.get(i).getOrden();


            try {
                asyncTaskInsertEnc = insertarSocioEncuestaTask.execute(codEncuesta,orden,codSocio,dataF.get(i).getValor());
                res = (String) asyncTaskInsertEnc.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (res.equals("OK")){

                cont = cont + 1;
            }
            else  {

                contError  = contError + 1 ;
            }



        }

        if (cont > 0  && contError <= 0){

            CreateCustomToast("Se registro correctamente  la encuesta" , Constantes.icon_succes , Constantes.layout_success);
            onBackPressed();
        }
        else  if ( contError> 0){

            CreateCustomToast("Algunos registros no se pudieron guardar recise  de nuevo por favor." , Constantes.icon_error , Constantes.layout_error);
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
        Toast toast = new Toast(EncuestaDetalle.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
    public void  DormirApp  () {

    }
}
