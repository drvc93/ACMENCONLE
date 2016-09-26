package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.ConceptoPago;
import Model.Seccion;
import Utils.Constantes;

/**
 * Created by drvc_ on 25/09/2016.
 */
public class    GetPuestosSocioTask extends AsyncTask<String,String,ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(String... strings) {


        ArrayList<String> result = new ArrayList<String>();

        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "getPuestosPorSocio";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("Dni", strings[1]);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.bodyIn;
            Log.i("result Soap Puesto Socios ==>",resSoap.toString());
            int cont = resSoap.getPropertyCount();

                SoapObject ic=(SoapObject)resSoap.getProperty(0);
                for (int j = 0; j <  ic.getPropertyCount(); j++) {

                    String var = ic.getProperty(j).toString() ;


                    result.add(var);
                }






        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error TaskGET Puesto Socios ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
