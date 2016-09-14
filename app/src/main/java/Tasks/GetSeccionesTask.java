package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.Seccion;
import Model.Usuario;
import Utils.Constantes;

/**
 * Created by drvc_ on 14/09/2016.
 */
public class GetSeccionesTask extends AsyncTask<String,String ,ArrayList<Seccion>> {

    @Override
    protected ArrayList<Seccion> doInBackground(String... strings) {
        ArrayList<Seccion> result = new ArrayList<Seccion>();
        // String urlserver = params[1];
        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetSecciones";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("codSeccion", strings[1]);

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
                Seccion sec =  new Seccion( );
                sec.setCodigo( ic.getProperty(0).toString() );
                sec.setDescripcion(ic.getProperty(1).toString());
                sec.setFecha(ic.getProperty(2).toString());


                result.add(sec);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error TaskGETSecciones ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
