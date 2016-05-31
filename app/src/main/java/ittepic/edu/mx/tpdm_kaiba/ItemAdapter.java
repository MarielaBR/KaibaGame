package ittepic.edu.mx.tpdm_kaiba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MARIELA on 30/05/2016.
 */
public class ItemAdapter extends BaseAdapter{
    Context contexto;
    String [] titulos;
    int[] imagenes;


    public ItemAdapter(Context c, String t[],int[] i){
        contexto=c;
        titulos=t;
        imagenes=i;
    }
    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View renglon=convertView;
        LayoutInflater layin;

        if(convertView==null){
            layin=(LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            renglon=layin.inflate(R.layout.item,parent,false);
        }
        TextView etiqueta=(TextView)renglon.findViewById(R.id.textView);
        ImageView imagen=(ImageView)renglon.findViewById(R.id.imageView);

        etiqueta.setText(titulos[position]);
        imagen.setImageResource(imagenes[position]);

        return renglon;
    }
}
