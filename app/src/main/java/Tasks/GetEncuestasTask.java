package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.ConceptoPago;
import Model.SocioEncuesta;
import Utils.Constantes;

/**
 * Created by PC on 03/04/2017.
 */

public class GetEncuestasTask extends AsyncTask<String,String,ArrayList<SocioEncuesta>> {

    @Override
    protected ArrayList<SocioEncuesta> doInBackground(String... strings) {
        ArrayList<SocioEncuesta> result = new ArrayList<SocioEncuesta>();

        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetEncuestasSocios";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("codSocio", strings[1]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();
            Log.i("result Soap ==>",resSoap.toString());
            int cont = resSoap.getPropertyCount();
            for (int i = 0 ; i<cont; i++){
                SoapObject ic=(SoapObject)resSoap.getProperty(i);
                SocioEncuesta c =  new SocioEncuesta( );
                c.setCodEncuesta(ic.getProperty(0).toString() );
                c.setDescrip(ic.getProperty(1).toString());
                c.setFecha(ic.getProperty(2).toString());



                result.add(c);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error GetTask Socio EnCUESTAS ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
