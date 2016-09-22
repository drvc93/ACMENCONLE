package Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.online_code.acmenconle.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.SocioPuesto;

/**
 * Created by drvc_ on 22/09/2016.
 */
public class PuestoSeccionAdapater extends ArrayAdapter<SocioPuesto> {

    private Context context ;
    private ArrayList<SocioPuesto> data;
    private int ResourceID;
    public HashMap<Integer,Integer> indexSpiner;
    public PuestoSeccionAdapater(Context context, int resource, ArrayList<SocioPuesto> data) {
        super(context, resource, data);

        this.context = context;
        this.ResourceID =resource;
        this.data  =data;
        indexSpiner = new HashMap<Integer, Integer>();
    }

    public static class ViewHolder {
        EditText txtNroPuesto;
        Spinner spSeccion ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;
        SocioPuesto socio = data.get(position);
        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(ResourceID, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtNroPuesto = (EditText) convertView.findViewById(R.id.txtLVNroPuesto);
            viewHolder.spSeccion = (Spinner)convertView.findViewById(R.id.spLVSeccion);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (socio.getCodNumeroPuesto().equals("")){


        }
        else {
            viewHolder.txtNroPuesto.setText(socio.getCodNumeroPuesto());
        }

        return convertView;
    }

    public SocioPuesto GetItem(int position) {

        return data.get(position);
    }
    }

