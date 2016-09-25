package Utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.online_code.acmenconle.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Model.Seccion;
import Model.SocioPuesto;
import Tasks.GetSeccionesTask;

/**
 * Created by drvc_ on 22/09/2016.
 */
public class PuestoSeccionAdapater extends ArrayAdapter<SocioPuesto> {
    private  ArrayList<String> listSpiner ;
    private Context context ;
    private ArrayList<SocioPuesto> data;
    private int ResourceID;
    public HashMap<Integer,Integer> indexSpiner;
    public HashMap<Integer,String> valuesText;
    public PuestoSeccionAdapater(Context context, int resource, ArrayList<SocioPuesto> data) {
        super(context, resource, data);

        this.context = context;
        this.ResourceID =resource;
        this.data  =data;
        indexSpiner = new HashMap<Integer, Integer>();
        valuesText  = new HashMap<Integer, String>();
        listSpiner = GetArrayStringSpiner();
    }

    public static class ViewHolder {
        EditText txtNroPuesto;
        Spinner spSeccion ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final ViewHolder viewHolder;
        final SocioPuesto socio = data.get(position);
        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(ResourceID, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtNroPuesto = (EditText) convertView.findViewById(R.id.txtLVNroPuesto);
            viewHolder.spSeccion = (Spinner)convertView.findViewById(R.id.spLVSeccion);
            viewHolder.spSeccion.setAdapter(getAdapterSpiner());
            viewHolder.txtNroPuesto.setInputType(InputType.TYPE_CLASS_NUMBER);

            viewHolder.spSeccion.setSelection(GetSpinerIndex(Integer.valueOf(socio.getCodSeccion())));

            if (socio.getCodNumeroPuesto()!=null){

                viewHolder.txtNroPuesto.setText(socio.getCodNumeroPuesto());
            }

            viewHolder.spSeccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    indexSpiner.put(position,i);
                    data.get(position).setCodSeccion(viewHolder.spSeccion.getSelectedItem().toString().substring(0,3).trim());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            viewHolder.txtNroPuesto.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            valuesText.put(position,String.valueOf(charSequence));
                           data.get(position).setCodNumeroPuesto(String.valueOf(charSequence));
                    Log.i(" posicion "+String.valueOf(position),"value = "+String.valueOf(charSequence));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


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

    public int GetSpinerIndex (int cod ){
        int result=0;
        for (int i = 0; i < listSpiner.size() ; i++) {

            if (Integer.valueOf(listSpiner.get(i).substring(0,3).trim()) ==cod){
                result = i;
                break;
            }
        }

        return  result;
    }

    public  ArrayList<String> GetArrayStringSpiner (){
        ArrayList<String> listString = new ArrayList<String>();
        ArrayList<Seccion> listSeccion =  null;
        GetSeccionesTask  getSeccionesTask = new GetSeccionesTask();
        AsyncTask<String,String,ArrayList<Seccion>>asyncTaskSecciones;

        try {
            asyncTaskSecciones = getSeccionesTask.execute("1","0","0");
            listSeccion = (ArrayList<Seccion>)  asyncTaskSecciones.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(listSeccion!=null && listSeccion.size()>0){

            for (int i = 0; i < listSeccion.size() ; i++) {

                Seccion sec = listSeccion.get(i);

                NumberFormat formatter = new DecimalFormat("00");
                String cod = formatter.format(Integer.valueOf(sec.getCodigo())); //

                listString.add(cod +"  -  " + sec.getDescripcion());
        }


        }

        return  listString;

    }

    public ArrayAdapter<String> getAdapterSpiner (){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,listSpiner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return  adapter;

    }


    public SocioPuesto GetItem(int position) {

        return data.get(position);
    }

    public  ArrayList<SocioPuesto> GetAllData (){

        return  data;

    }

    public void  AddItem (){

        SocioPuesto  sc = new SocioPuesto("0","0","0","","","01");
        data.add(sc);
        this.notifyDataSetChanged();

    }
    }

