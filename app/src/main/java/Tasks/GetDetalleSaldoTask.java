package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.DetalleSaldo;
import Model.Seccion;
import Utils.Constantes;

/**
 * Created by drvc_ on 13/10/2016.
 */
public class GetDetalleSaldoTask extends AsyncTask<String,String,ArrayList<DetalleSaldo>> {
    @Override
    protected ArrayList<DetalleSaldo> doInBackground(String... strings) {
        ArrayList<DetalleSaldo> result = new ArrayList<DetalleSaldo>();


        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetDetalleSaldo";
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
            Log.i("result Soap detalle saldo ==>",resSoap.toString());
            int cont = resSoap.getPropertyCount();
            for (int i = 0 ; i<cont; i++){
                SoapObject ic=(SoapObject)resSoap.getProperty(i);
                DetalleSaldo d =  new DetalleSaldo( );
               // sec.setCodigo( ic.getProperty(0).toString() );
                d.setCodConcepto(ic.getProperty(0).toString());
                d.setMes(ic.getProperty(1).toString());
                d.setAnio(ic.getProperty(2).toString());
                d.setEstado(ic.getProperty(3).toString());
                d.setMonto(ic.getProperty(4).toString());


                result.add(d);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error Get detalle saldo ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
