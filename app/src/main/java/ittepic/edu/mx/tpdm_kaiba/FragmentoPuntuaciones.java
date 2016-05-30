package ittepic.edu.mx.tpdm_kaiba;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by MARIELA on 28/05/2016.
 */
public class FragmentoPuntuaciones extends Fragment{
    View root;
    String[] titulo = new String[]{
            "Usuario         Nivel            Puntuación",
            "   -              -                  -     ",
            "   -              -                  -     ",
            "   -              -                  -     ",
            "   -              -                  -     ",
            "   -              -                  -     ",
    };

    int[] imagenes = {
            R.drawable.vacio,
            R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4,
            R.drawable.c5
    };
    ListViewAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_puntuaciones, container, false);

        final ListView lista = (ListView) root.findViewById(R.id.listView);
        adapter = new ListViewAdapter(getActivity(), titulo, imagenes);
        lista.setAdapter(adapter);

        return root;

    }
}
