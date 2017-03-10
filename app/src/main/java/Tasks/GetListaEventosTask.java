package Tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import Model.DetalleSaldo;
import Model.Evento;
import Utils.Constantes;

/**
 * Created by drvc_ on 02/02/2017.
 */
public class GetListaEventosTask extends AsyncTask<String,String,ArrayList<Evento>> {
    @Override
    protected ArrayList<Evento> doInBackground(String... strings) {
        ArrayList<Evento> result = new ArrayList<Evento>();


        final String NAMESPACE = Constantes.UrlWS;
        final String URL=NAMESPACE+Constantes.NameSpaceWS;
        final String METHOD_NAME = "GetListaEventos";
        final String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("accion", strings[0]);

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
                Evento e =  new Evento( );
                // sec.setCodigo( ic.getProperty(0).toString() );
                e.setCodEvento(ic.getProperty(0).toString());
                e.setAnio(ic.getProperty(1).toString());
                e.setMes(ic.getProperty(2).toString());
                e.setDia(ic.getProperty(3).toString());
                e.setHora(ic.getProperty(4).toString());
                e.setDescripCorta(ic.getProperty(5).toString());



                result.add(e);

            }



        }
        catch (Exception e){

            String B = e.getMessage();
            Log.i("Error Get  lista eventos ==>" , e.getMessage());

        }

//        Log.i("Size result array ==>" ,String.valueOf(result.length) );

        return result;
    }
}
