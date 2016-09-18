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
 * Created by drvc_ on 18/09/2016.
 */
public class GetConceptosPagoTask extends AsyncTask<String,String,ArrayList<ConceptoPago>> {
    @Override
    protected ArrayList<ConceptoPago> doInBackground(String... strings) {

        ArrayList<ConceptoPago> result = new ArrayList<ConceptoPago>();

        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetConceptosPago";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);
        request.addProperty("codConcepto", strings[1]);

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
                ConceptoPago c =  new ConceptoPago( );
               c.setCodConcepto( ic.getProperty(0).toString() );
                c.setDescripcion(ic.getProperty(1).toString());
                c.setMonto(ic.getProperty(2).toString());
                c.setUserReg(ic.getProperty(3).toString());
                c.setFecharReg(ic.getProperty(4).toString());


                result.add(c);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error GetTask ConceptosPagos ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
