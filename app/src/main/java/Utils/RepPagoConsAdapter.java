package Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.online_code.acmenconle.R;

import java.util.ArrayList;
import java.util.List;

import Model.DetalleSaldo;

/**
 * Created by drvc_ on 13/10/2016.
 */
public class RepPagoConsAdapter extends ArrayAdapter<DetalleSaldo> {

    Context context ;
    int resourceID;
    ArrayList<DetalleSaldo> data;

    public RepPagoConsAdapter(Context context, int resource, ArrayList<DetalleSaldo> data) {
        super(context, resource, data);
        this.data = data;
        this.context = context;
        this.resourceID = resource;

    }

    public static class ViewHolder {
        TextView lblMes;
        TextView lblConcepto;
        TextView lblMonto;
        TextView lblEstado;
        LinearLayout ParentLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        DetalleSaldo det =  data.get(position);
        if (convertView == null)
        {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resourceID, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.lblConcepto = (TextView) convertView.findViewById(R.id.lblRCDConcepto);
            viewHolder.lblMes = (TextView) convertView.findViewById(R.id.lblRCDMes);
            viewHolder.lblEstado  = (TextView) convertView.findViewById(R.id.lblRCDestado);
            viewHolder.lblMonto = (TextView)convertView.findViewById(R.id.lblRCDMonto);
            viewHolder.ParentLayout = (LinearLayout) convertView.findViewById(R.id.layoutREP);
            convertView.setTag(viewHolder);

        }

        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

         if (det.getEstado().equals("0")){
             viewHolder.ParentLayout.setBackgroundResource(R.drawable.detalle_debe_layout);
             viewHolder.lblEstado.setText("Debe");

         }
        else  if (det.getEstado().equals("1")){
             viewHolder.ParentLayout.setBackgroundResource(R.drawable.detalle_pago_layout);
             viewHolder.lblEstado.setText("Pagó");

         }

         else  if (det.getEstado().equals("3")){
             viewHolder.ParentLayout.setBackgroundResource(R.drawable.detalle_pendiente_layout);
             viewHolder.lblEstado.setText("Pendiente");

         }

        viewHolder.lblMonto.setText(det.getMonto());
        viewHolder.lblMes.setText(det.getMes() +"-"+det.getAnio());

        if (det.getCodConcepto().equals("1")){

            viewHolder.lblConcepto.setText("VIGILANCIA");
        }

        else  if (det.getCodConcepto().equals("2")){

            viewHolder.lblConcepto.setText("AGUA");
        }

        else  if (det.getCodConcepto().equals("3")){

            viewHolder.lblConcepto.setText("SERVICIOS");
        }

        else  if (det.getCodConcepto().equals("4")){

            viewHolder.lblConcepto.setText("DEUDAS ANT.");
        }

        return  convertView;
    }
}
