package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.SocioEncuesta;
import Model.SocioPregunta;
import Utils.Constantes;

/**
 * Created by PC on 15/05/2017.
 */

public class GetPreguntasTask extends AsyncTask<String,String,ArrayList<SocioPregunta>> {
    @Override
    protected ArrayList<SocioPregunta> doInBackground(String... strings) {
        ArrayList<SocioPregunta> result = new ArrayList<SocioPregunta>();

        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetSocioPregunta";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("codSocio", strings[1]);
        request.addProperty("codEncuesta", strings[2]);

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
                SocioPregunta c =  new SocioPregunta( );
                c.setCodEcnuetsa(ic.getProperty(0).toString());
                c.setOrden(ic.getProperty(1).toString());
                c.setCodSocio(ic.getProperty(2).toString());
                c.setValor(ic.getProperty(3).toString());
                c.setFechaReg(ic.getProperty(4).toString());
                c.setTipo(ic.getProperty(5).toString());
                c.setPregunta(ic.getProperty(6).toString());



                result.add(c);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error GetTask Socio PREGUNTAS DETALLE ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
