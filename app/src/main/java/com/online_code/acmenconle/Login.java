package com.online_code.acmenconle;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
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
import Tasks.ComprobarDeudaTask;
import Tasks.GetPuestosSocioTask;
import Utils.Constantes;

public class Login extends AppCompatActivity {


    EditText txtUser , txtPass;
    Button  btnEntrar ;
    SharedPreferences preferences;
    String dniSocio = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        txtUser =  (EditText) findViewById(R.id.txtUserName);
        txtPass  =(EditText) findViewById(R.id.txtPass);
        btnEntrar = (Button) findViewById(R.id.btnLogin);
        preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        dniSocio = preferences.getString("UserDni",null);

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


    /*
 * Show AlertDialog with a simple list view.
 *
 * No XML needed.
 */
    public void SelecPuesto(final Usuario usuario) {

        ArrayList<String> listString = null;
        CharSequence[] items = null ;
        AsyncTask<String,String,ArrayList<String>> asyncTaskPuesto;
        GetPuestosSocioTask getPuestosSocioTask=  new GetPuestosSocioTask();

        try {
            asyncTaskPuesto = getPuestosSocioTask.execute("5", usuario.getDni());
            listString = (ArrayList<String>) asyncTaskPuesto.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (listString!=null && listString.size()>0) {
            items = new String[listString.size()];
            for (int i = 0; i < listString.size() ; i++) {

                items[i] = listString.get(i);
            }



            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setTitle("Seleccione con que Nº de puesto desea ingresar.");
            final CharSequence[] finalItems = items;
            builder.setCancelable(false);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {

                    // will toast your selection
                   // Toast.makeText(Login.this, finalItems[item], Toast.LENGTH_SHORT).show(); //showToast("Name: " + items[item]);
                   GoMainActivity( String.valueOf(finalItems[item]), usuario);
                    dialog.dismiss();

                }
            }).show();
        }
    }


     public void GoMainActivity (String nroPuesto , Usuario us){

         SharedPreferences.Editor editor = preferences.edit();
         editor.putString("nroPuesto", nroPuesto);
         editor.commit();
         AsyncTask<String,String,String>  asyncDeuda ;
         ComprobarDeudaTask comprobarDeudaTask = new ComprobarDeudaTask();
         String result  = "";


         try {
             asyncDeuda = comprobarDeudaTask.execute("3" ,us.getCodigo(), nroPuesto);
             result =  (String) asyncDeuda.get();
         } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (ExecutionException e) {
             e.printStackTrace();
         }

          if (result.equals("Error") ){

              CreateCustomToast("No se pudo comprobar el estado de deudas del socio.",Constantes.icon_error,Constantes.layout_error);

          }
          else if (result.equals("")){

              Intent intent = new Intent(Login.this, MenuPrincipal.class);
              startActivity(intent);
          }
        //  else  if (Double.valueOf(result)>0){
          //      AlerDeuda();

         // }


         else {
              Intent intent = new Intent(Login.this, MenuPrincipal.class);
              startActivity(intent);
          }
     }

    public void AlerDeuda() {
        new AlertDialog.Builder(Login.this)
                .setTitle("Alerta de deuda pendiente")
                .setMessage("Se ha restringido el acceso a  la zona socios por motivos de deuda , desea contactarnos con nosotros?")
                .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                //showToast("Thank you! You're awesome too!");
                                Intent  intent = new Intent(Login.this,Contacto.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
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
            editor.putString("UserNombreLargo",user.getNombres() + " " + user.getApellidoPat()+ " " + user.getApellidoMat());
            editor.commit();


            SelecPuesto(user);
           //ntent intent = new Intent(Login.this , MenuPrincipal.class);
           //zartActivity(intent);
           // CreateCustomToast("Bienvenido " + user.getNombres(), Constantes.icon_succes,Constantes.layout_success);

        }

        else {
            CreateCustomToast("Usuario  o  contraseña incorrectos", Constantes.icon_error, Constantes.layout_error);

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
