package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import Utils.Constantes;

/**
 * Created by drvc_ on 15/09/2016.
 */
public class RegistrarSocioPuestoTask extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String result ="";
        // String urlserver = params[2];
        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "InsertSocioPuesto";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("codSocio", strings[0]);
        request.addProperty("nroPuesto", strings[1]);
        request.addProperty("codSeccion", strings[2]);
        request.addProperty("userReg", strings[3]);


        SoapSerializationEnvelope envelope =  new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE transporte = new HttpTransportSE(URL);

        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
            String res = resultado_xml.toString();

            result=res;


        }
        catch (Exception e)
        {
            result = "";
        }
        Log.i("retur value insertSeccionSocios Task => ", result);
        return result;
    }
}
