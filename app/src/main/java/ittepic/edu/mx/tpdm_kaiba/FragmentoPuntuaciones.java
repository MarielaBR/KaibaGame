package ittepic.edu.mx.tpdm_kaiba;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by MARIELA on 28/05/2016.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MARIELA on 28/05/2016.
 */
public class FragmentoPuntuaciones extends Fragment{
    View root;
    String res;
    String[] titulo = new String[]{
            "Jugador        Nivel            Puntuación",
            "     ",
            "     ",
            "     ",
            "     ",
            "     ",
    };


    int[] imagenes = {
            R.drawable.vacio,
            R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4,
            R.drawable.c5
    };

    ListView lista;
    ListViewAdapter adapter;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_puntuaciones, container, false);

        lista = (ListView) root.findViewById(R.id.listView);
        adapter = new ListViewAdapter(getActivity(), titulo, imagenes);
        lista.setAdapter(adapter);

        consultar();



        //lista.setAdapter(arrayAdapter);
        return root;




    }


    public void consultar(){
        try {

            ConexionTabla web = new ConexionTabla(FragmentoPuntuaciones.this);
            web.agregarVariables("usuario", "");
            web.execute(new URL("http://kaiba.esy.es/puntuaciones.php"));
        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }
    }


    public void mostrarResultado(String resultado){

        String relleno[]=new String[6];
        String arr[]=resultado.split(",");
        String aux="";
        String aux2[];
        ;


        relleno[0]="Jugador        Nivel            Puntuación";
        for(int i=0;i<5;i++){
            aux2=arr[i].split("---");
            aux=aux2[0]+"                 "+aux2[1]+"                "+aux2[2];
            relleno[i+1]=aux;
        }

        adapter = new ListViewAdapter(getActivity(), relleno, imagenes);
        lista.setAdapter(adapter);


    }

}
