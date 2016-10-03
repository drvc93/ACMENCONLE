package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.CPagos;
import Model.Seccion;
import Utils.Constantes;

/**
 * Created by drvc_ on 03/10/2016.
 */
public class GetReportePagosTask extends AsyncTask<String,String,ArrayList<CPagos>> {
    @Override
    protected ArrayList<CPagos> doInBackground(String... strings) {
        ArrayList<CPagos> result = new ArrayList<CPagos>();

        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetRepPagos";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("codSocio", strings[1]);
        request.addProperty("nroPuesto", strings[2]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();
            Log.i("result Soap Rep Pagos ==>",resSoap.toString());
            int cont = resSoap.getPropertyCount();
            for (int i = 0 ; i<cont; i++){
                SoapObject ic=(SoapObject)resSoap.getProperty(i);
                CPagos c =  new CPagos( );
                c.setNombres(ic.getProperty(0).toString());
                c.setFechaPago(ic.getProperty(1).toString());
                c.setSeccion(ic.getProperty(2).toString());
                c.setMonto(ic.getProperty(3).toString());
                c.setBanco(ic.getProperty(4).toString());
                c.setNroPuesto(ic.getProperty(5).toString());
                c.setEstado(ic.getProperty(6).toString());


                result.add(c);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error task REP PAGOS ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
