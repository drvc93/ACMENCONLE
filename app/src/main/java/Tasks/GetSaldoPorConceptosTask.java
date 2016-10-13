package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.ConceptoPago;
import Model.SaldoConcepto;
import Utils.Constantes;

/**
 * Created by drvc_ on 07/10/2016.
 */
public class GetSaldoPorConceptosTask extends AsyncTask<String,String,ArrayList<SaldoConcepto>> {


    @Override
    protected ArrayList<SaldoConcepto> doInBackground(String... strings) {
        ArrayList<SaldoConcepto> result = new ArrayList<SaldoConcepto>();

        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetSaldoPorConceptos";
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
            Log.i("result Soap SaldoConcepto ==>",resSoap.toString());
            int cont = resSoap.getPropertyCount();
            for (int i = 0 ; i<cont; i++){
                SoapObject ic=(SoapObject)resSoap.getProperty(i);
                SaldoConcepto c =  new SaldoConcepto( );
                c.setCodConcepto( ic.getProperty(0).toString() );
                c.setSaldoxConcepto(ic.getProperty(1).toString());
                c.setPagadoxConceto(ic.getProperty(2).toString());
                if (c.getSaldoxConcepto().equals("anyType{}")){
                    c.setSaldoxConcepto("0");
                }



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
