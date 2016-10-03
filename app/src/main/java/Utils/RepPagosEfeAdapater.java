package Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.online_code.acmenconle.R;

import java.util.ArrayList;
import java.util.List;

import Model.CPagos;

/**
 * Created by drvc_ on 03/10/2016.
 */
public class RepPagosEfeAdapater extends ArrayAdapter<CPagos> {

    private  Context context ;
    private  ArrayList<CPagos> data;
    private  int ResourceID;

    public RepPagosEfeAdapater(Context context, int resource, ArrayList<CPagos> data) {
        super(context, resource, data);

        this.context = context;
        this.ResourceID = resource;
        this.data= data;

    }

    public static class ViewHolder {
        TextView lblFecha;
        TextView lblMonto;
        TextView lblBanco;
        TextView lblNroPuesto;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder ;
        CPagos c = data.get(position);
        if (convertView == null){

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(ResourceID, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.lblFecha = (TextView)convertView.findViewById(R.id.lblFechaRPE);
            viewHolder.lblMonto = (TextView)convertView.findViewById(R.id.lblMontoRPE);
            viewHolder.lblBanco = (TextView)convertView.findViewById(R.id.lblBancoRPE);
            viewHolder.lblNroPuesto = (TextView) convertView.findViewById(R.id.lblNroPuestoRPE);
            convertView.setTag(viewHolder);


        }

        else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.lblFecha.setText(c.getFechaPago());
        viewHolder.lblNroPuesto.setText(c.getNroPuesto());
        viewHolder.lblBanco.setText(c.getBanco());
        viewHolder.lblMonto.setText(c.getMonto());

        return  convertView;
    }
}
