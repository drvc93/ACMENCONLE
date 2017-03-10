package Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.online_code.acmenconle.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Model.Evento;

/**
 * Created by drvc_ on 02/02/2017.
 */
public class EventosAdapter extends ArrayAdapter<Evento> {
    private Context context ;
    private int ResourceId;
    private ArrayList<Evento> data;



    public EventosAdapter(Context context, int resource, ArrayList<Evento> data) {
        super(context, resource, data);

        this.context= context;
        this.ResourceId = resource;
        this.data = data;
    }

    public static class ViewHolder {
        TextView lblDia;
        TextView lblAnio;
        TextView lblMes;
         TextView lblHora;
        TextView lblDescripCorta;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;

        Evento  ev = data.get(position);
        if (convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(ResourceId, parent, false);
            viewHolder = new ViewHolder();



            viewHolder.lblMes = (TextView)convertView.findViewById(R.id.lblMesCalendar);
            viewHolder.lblDia = (TextView)convertView.findViewById(R.id.lblDiaCalendar);
            viewHolder.lblHora = (TextView)convertView.findViewById(R.id.lblHoraCalendar);
            viewHolder.lblDescripCorta = (TextView) convertView.findViewById(R.id.lblDescrCalendar);
            viewHolder.lblAnio = (TextView) convertView.findViewById(R.id.lblAnioCalendar);
            convertView.setTag(viewHolder);


        }

        else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.lblMes.setText(ev.getMes());
        viewHolder.lblAnio.setText(ev.getAnio());
        viewHolder.lblHora.setText(ev.getHora());
        viewHolder.lblDia.setText(ev.getDia());
        viewHolder.lblDescripCorta.setText(ev.getDescripCorta());

        return  convertView;
    }

}
