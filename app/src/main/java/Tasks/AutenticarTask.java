package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.Usuario;
import Utils.Constantes;

/**
 * Created by drvc_ on 10/09/2016.
 */
public class AutenticarTask extends AsyncTask<String,String,ArrayList<Usuario>> {
    @Override
    protected ArrayList<Usuario> doInBackground(String... strings) {
     ArrayList<Usuario> result = new ArrayList<Usuario>();
       // String urlserver = params[1];
        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetUsuario";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("acccion", strings[0]);
        request.addProperty("username", strings[1]);
        request.addProperty("password", strings[2]);
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
              Usuario us =  new Usuario( );
                us.setCodigo( ic.getProperty(0).toString() );
                us.setDni(ic.getProperty(1).toString());
                us.setNombres(ic.getProperty(2).toString());
                us.setApellidoPat(ic.getProperty(3).toString());
                us.setApellidoMat(ic.getProperty(4).toString());
                us.setPuesto(ic.getProperty(5).toString());
                us.setCelular(ic.getProperty(6).toString());
                us.setTipoUsuario(ic.getProperty(8).toString());
                us.setFechaRegistro(ic.getProperty(7).toString());
                us.setEstado(ic.getProperty(9).toString());
                us.setCorreo(ic.getProperty(10).toString());

                result.add(us);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error TaskGETusuario ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;

    }
}
