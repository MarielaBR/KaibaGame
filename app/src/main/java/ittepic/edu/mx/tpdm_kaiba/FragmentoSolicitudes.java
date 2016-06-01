package ittepic.edu.mx.tpdm_kaiba;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MARIELA on 01/06/2016.
 */
public class FragmentoSolicitudes extends Fragment{
    View root;
    TextView titulo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_solicitudes, container, false);

        titulo=(TextView)root.findViewById(R.id.textView5);
        Typeface normal = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        titulo.setTypeface(normal);


        return root;

    }
}
