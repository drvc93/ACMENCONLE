package com.online_code.acmenconle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Model.Banco;
import Model.ConceptoPago;
import Tasks.GetBancosTask;
import Tasks.GetConceptosPagoTask;
import Tasks.GetPuestosSocioTask;
import Tasks.GetSocioPuestoTask;
import Tasks.RegistrarPagoTask;
import Utils.Constantes;

public class FormularioPago extends AppCompatActivity {

    ArrayList<Banco> listBancos;
    ArrayList<ConceptoPago> listConceptos;
   // Spinner spBanco, spConcepto, spPuesto;
    EditText txtFecha, txtMensaje, txtMontoPago, txtNroOpe;
    Button btnRegPago;
    SharedPreferences preferences;
    String dniSocio ,codSocio;
    String FechaFinal,NroPuesto ;
    String paramBanco , paramConcepto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pago);
        setTitle("Formulario de reporte de pago");
        preferences = PreferenceManager.getDefaultSharedPreferences(FormularioPago.this);
        dniSocio = preferences.getString("UserDni",null);
        codSocio = preferences.getString("CodSocio",null);
        NroPuesto =  preferences.getString("nroPuesto",null);
        //spBanco = (Spinner) findViewById(R.id.spBanco);
      //  spConcepto = (Spinner) findViewById(R.id.spConcepto);
    //    spPuesto = (Spinner) findViewById(R.id.spPuesto);
        txtFecha = (EditText) findViewById(R.id.txtFechaPago);
        txtMensaje = (EditText) findViewById(R.id.txtMensajePago);
        txtNroOpe = (EditText) findViewById(R.id.txtNroOperacion);
        btnRegPago = (Button) findViewById(R.id.btnRegPago);
        txtMontoPago = (EditText) findViewById(R.id.txtMontoPago);
        txtMontoPago.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        paramBanco = getIntent().getStringExtra("prmCodBanco");
        paramConcepto = getIntent().getStringExtra("prmConcepto");
        LoadSpinerBancos();
        LoadSpinerConcepto();
        LoadSpinerPuestos(dniSocio);

        btnRegPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidarPago() == true) {
                    AlerSave();
                }
            }
        });

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelecFecha();

            }
        });
    }


    public boolean ValidarPago (){
            boolean res = true ;
            String msj = "";

        if (txtNroOpe.getText().toString()==null || txtNroOpe.getText().toString().equals("")){

            msj="Debe  ingresar el número de operación.";

            res = false;

        }
        else  if (txtMontoPago.getText().toString()==null || txtMontoPago.getText().toString().equals("")){

            msj= "Debe ingresar el monto pagado.";
            res = false;
        }

        else  if (txtFecha.getText().toString()==null || txtFecha.getText().toString().equals("")){

            msj ="Debe seleccionar la fecha de pago.";

            res = false;
        }

        return  res;
    }




     public  void  RegistrarPago () {
         String accion  = "NEW" ;
         String codConcepto = paramConcepto;
         String nroOp = txtNroOpe.getText().toString();
         String codBanco =  paramBanco;
         String obs  = txtMensaje.getText().toString();
         String monto = txtMontoPago.getText().toString();
        // monto  = monto.replace(".",",");

         String puesto = NroPuesto;
         String fecha = txtFecha.getText().toString();
         RegistrarPagoTask registrarPagoTask = new RegistrarPagoTask();
         AsyncTask<String,String,String> asyncTaskPago  ;
         String res   = null;
         Log.i("Monto Pago " ,  monto);

         try {
             asyncTaskPago = registrarPagoTask.execute(accion,codSocio,codConcepto,nroOp,codBanco,obs,monto,puesto,FechaFinal);
             res =  (String)  asyncTaskPago.get();
         } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (ExecutionException e) {
             e.printStackTrace();
         }


         if (res.equals("OK") ) {

             CreateCustomToast("Se envio la información correctamente  , en el transcurso del día se confirmara el pago", Constantes.icon_succes, Constantes.layout_success);
             super.onBackPressed();

         }

         else if  (res.equals("PAGADO")){

             CreateCustomToast("El pago de este mes por el concepto seleccionado ya fue realizado",Constantes.icon_error,Constantes.layout_error);
             super.onBackPressed();
         }

         else if  (res.equals("FECHA")){

             CreateCustomToast("La fecha de pago es posterior a la fecha actual , verificar por favor",Constantes.icon_error,Constantes.layout_error);
             super.onBackPressed();
         }
         else if  (res.equals("NOSALDO")){

             CreateCustomToast("El monto  pagado excede a la deuda.",Constantes.icon_error,Constantes.layout_error);
             super.onBackPressed();
         }

     }


    public void LoadSpinerBancos() {

        AsyncTask<String, String, ArrayList<Banco>> asyncTaskBancos;
        GetBancosTask getBancosTask = new GetBancosTask();


        try {
            asyncTaskBancos = getBancosTask.execute("1", "0");
            listBancos = (ArrayList<Banco>) asyncTaskBancos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listBancos != null && listBancos.size() > 0) {
            ArrayList<String> dataBancos = new ArrayList<String>();
            for (int i = 0; i < listBancos.size(); i++) {
                Banco b =  listBancos.get(i);
                dataBancos.add(b.getCodBanco() + "  - "+b.getNombreLargo());
            }

            ArrayAdapter<String> adaparteBanco = new ArrayAdapter<String>(FormularioPago.this, android.R.layout.simple_spinner_dropdown_item, dataBancos);
            adaparteBanco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          //  spBanco.setPrompt("Seleccionar Banco");
            //spBanco.setAdapter(adaparteBanco);


        }


    }


    public  void  LoadSpinerConcepto (){
        AsyncTask<String,String,ArrayList<ConceptoPago>>  asyncConceptos ;
        GetConceptosPagoTask getConceptostask = new GetConceptosPagoTask();


        try {
            asyncConceptos = getConceptostask.execute("1","0");
            listConceptos = (ArrayList<ConceptoPago>) asyncConceptos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listConceptos != null && listConceptos.size()>0){

            ArrayList<String> dataConceptos  = new ArrayList<String>();
            for (int i = 0; i <listConceptos.size() ; i++) {
                ConceptoPago c = listConceptos.get(i);
                dataConceptos.add(c.getCodConcepto() + "  - " + c.getDescripcion());

            }

            ArrayAdapter<String> adapterConceptos   = new ArrayAdapter<String>(FormularioPago.this,android.R.layout.simple_spinner_dropdown_item,dataConceptos);
            adapterConceptos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //spConcepto.setPrompt("Seleccionar concepto pago");
           // spConcepto.setAdapter(adapterConceptos);
        }



    }

    public  void  LoadSpinerPuestos (String codSocio){
        ArrayList<String> listPuestos = null;
        AsyncTask<String,String,ArrayList<String>>  asyncTaskPuestos;
        GetPuestosSocioTask getSocioPuestoTask = new GetPuestosSocioTask();



        try {
            asyncTaskPuestos = getSocioPuestoTask.execute("5",codSocio);
            listPuestos = (ArrayList<String>) asyncTaskPuestos.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (listPuestos!= null && listPuestos.size()>0){

            ArrayAdapter<String> adapterPuestos = new ArrayAdapter<String>(FormularioPago.this,android.R.layout.simple_spinner_dropdown_item,listPuestos);
            adapterPuestos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //spPuesto.setAdapter(adapterPuestos);

        }
    }

    public void SelecFecha() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker_layout, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        new AlertDialog.Builder(FormularioPago.this).setView(view)
                .setTitle("Seleccionar Fecha")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    // @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();
                        String mes = String.format("%02d", month);

                        String dia = String.format("%02d", day);
                        txtFecha.setText(dia + "/" + mes + "/" + String.valueOf(year));

                            FechaFinal = mes + "/" + dia + "/" + String.valueOf(year);
                          //  FFinGlobal = String.valueOf(year) + "-" + mes + "-" + dia;
                         //   Log.i("Fecha global FIN => ", FFinGlobal);

                        dialog.cancel();

                    }

                }).show();
    }

    public void AlerSave() {
        new AlertDialog.Builder(FormularioPago.this)
                .setTitle("Advertencia")
                .setIcon(R.drawable.icn_alert)
                .setMessage("¿Esta seguro que desea enviar la información?")
                //  .setIcon(R.drawable.icn_alert)
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                RegistrarPago();
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
        Toast toast = new Toast(FormularioPago.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();


    }
}
