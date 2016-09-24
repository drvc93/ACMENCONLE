package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.Seccion;
import Model.SocioPuesto;
import Utils.Constantes;

/**
 * Created by drvc_ on 22/09/2016.
 */
public class GetSocioPuestoTask extends AsyncTask<String,String,ArrayList<SocioPuesto>> {
    @Override
    protected ArrayList<SocioPuesto> doInBackground(String... strings) {


        ArrayList<SocioPuesto> result = new ArrayList<SocioPuesto>();

        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "getSociosPuesto";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("CodSocio", strings[1]);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transporte = new HttpTransportSE(URL);
        transporte.debug = true;
        try
        {
            transporte.call(SOAP_ACTION, envelope);

            SoapObject resSoap =(SoapObject)envelope.getResponse();
            Log.i("result Soap getsociopuesto ==>",resSoap.toString());
            int cont = resSoap.getPropertyCount();
            for (int i = 0 ; i<cont; i++){
                SoapObject ic=(SoapObject)resSoap.getProperty(i);
                SocioPuesto sec =  new SocioPuesto( );
                sec.setCodSocion( ic.getProperty(0).toString() );
                sec.setCodNumeroPuesto(ic.getProperty(1).toString());
                sec.setEstado(ic.getProperty(2).toString());
                sec.setFechaReg(ic.getProperty(3).toString());
                sec.setUserReg(ic.getProperty(4).toString());
                sec.setCodSeccion(ic.getProperty(5).toString());


                result.add(sec);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error Task SocioPuesto ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
