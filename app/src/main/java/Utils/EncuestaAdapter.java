package Utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.online_code.acmenconle.R;

import java.util.ArrayList;

import Model.SocioEncuesta;

/**
 * Created by PC on 03/04/2017.
 */

public class EncuestaAdapter extends ArrayAdapter<SocioEncuesta> {
    private Context    context ;
    private  int ResourceId;
    private ArrayList<SocioEncuesta> data;

    public EncuestaAdapter( Context context,  int resource, ArrayList<SocioEncuesta> data) {
        super(context, resource, data);
        this.context = context;
        this.ResourceId = resource;
        this.data = data;
    }

    public static class ViewHolder {
        TextView  lblencuesta ;
        TextView  lblEstado;
        TextView Fecha ;


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       ViewHolder  viewHolder;
        SocioEncuesta e =  data.get(position);
        if (convertView == null)
        {

            LayoutInflater inflater  = ((Activity) context).getLayoutInflater();
            convertView  = inflater.inflate(ResourceId,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.lblencuesta = (TextView)convertView.findViewById(R.id.lblEncuestaTitulo);
            viewHolder.lblEstado = (TextView)convertView.findViewById(R.id.lblEncuestaEstado);
            viewHolder.Fecha = (TextView)convertView.findViewById(R.id.lblEncuestaFecha);
            convertView.setTag(viewHolder);



        }

        else   {

            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.lblencuesta.setText(e.getDescrip());
        viewHolder.Fecha.setText(e.getFecha());
        viewHolder.lblEstado.setText("Activo");

        return  convertView;
    }
}
