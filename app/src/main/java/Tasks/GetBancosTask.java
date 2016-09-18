package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.Banco;
import Model.Seccion;
import Utils.Constantes;

/**
 * Created by drvc_ on 18/09/2016.
 */
public class GetBancosTask extends AsyncTask<String,String,ArrayList<Banco>> {
    @Override
    protected ArrayList<Banco> doInBackground(String... strings) {
        ArrayList<Banco> result = new ArrayList<Banco>();

        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetBancos";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("codBanco", strings[1]);


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
                Banco  b =  new Banco( );
                b.setCodBanco( ic.getProperty(0).toString() );
                b.setNombreLargo(ic.getProperty(1).toString());
                b.setNombreCorto(ic.getProperty(2).toString());
                b.setNroCuenta(ic.getProperty(3).toString());


                result.add(b);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error TaskGETBancos ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
