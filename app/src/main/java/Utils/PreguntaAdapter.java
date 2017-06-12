package Utils;

import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.online_code.acmenconle.R;

import java.util.ArrayList;
import java.util.List;

import Model.SocioEncuesta;
import Model.SocioPregunta;

/**
 * Created by PC on 15/05/2017.
 */

public class PreguntaAdapter extends ArrayAdapter<SocioPregunta> {

    private  Context context ;
    private  int ResourceId;
    private  ArrayList<SocioPregunta> data;
    //private  String[] str={"SI";"NO"};

    public PreguntaAdapter(Context context,  int resource, ArrayList<SocioPregunta> data) {
        super(context, resource, data);
        this.context = context;
        this.ResourceId = resource;
        this.data = data;

    }

    public static class ViewHolder {
        RelativeLayout  Masterlayout;
        Spinner spPregunta;
        RatingBar rtnBarPregunta;
        TextView lblTitulo ;


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder ;
        SocioPregunta p = data.get(position);
          String[] str={"SI","NO"};


        if (convertView == null){

            LayoutInflater inflater =  ((Activity)context ).getLayoutInflater();
            convertView =  inflater.inflate(ResourceId , parent , false);
            viewHolder = new ViewHolder();

            viewHolder.Masterlayout = (RelativeLayout) convertView.findViewById(R.id.lyDetalleEncuesta);
            viewHolder.rtnBarPregunta = (RatingBar) convertView.findViewById(R.id.rtnPregunta);
            viewHolder.spPregunta = (Spinner) convertView.findViewById(R.id.spPreguntaED);
            viewHolder.lblTitulo = (TextView) convertView.findViewById(R.id.lblPreguntaTxt);
            convertView.setTag(viewHolder);

        viewHolder.rtnBarPregunta.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                data.get(position).setValor(String.valueOf(v));
                Log.i("rating " + String.valueOf(position) ,  String.valueOf(v));
            }
        });

            viewHolder.spPregunta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0){

                        data.get(position).setValor("SI");
                    }
                    {
                        data.get(position).setValor("NO");
                    }

                    Log.i("Spiner " + String.valueOf(position) ,  String.valueOf(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    data.get(position).setValor("NO");
                }
            });


        }

        else  {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        if (data.get(position).getTipo().equals( "RT"))
        {

            viewHolder.Masterlayout.removeView(viewHolder.spPregunta);
            viewHolder.spPregunta.setVisibility(View.INVISIBLE);

        }
        else {

            viewHolder.Masterlayout.removeView(viewHolder.rtnBarPregunta);
            viewHolder.rtnBarPregunta.setVisibility(View.INVISIBLE);
            ArrayAdapter<String> adpsp= new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,str);
            adpsp.setDropDownViewResource(android.R.layout.simple_spinner_item);
            viewHolder.spPregunta.setAdapter(adpsp);
        }

        viewHolder.lblTitulo.setText(p.getPregunta());


        return  convertView;
    }

    public    ArrayList<SocioPregunta> getAlldata (){

        return  data;
    }
}
