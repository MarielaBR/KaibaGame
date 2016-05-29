package ittepic.edu.mx.tpdm_kaiba;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MARIELA on 28/05/2016.
 */
public class FragmentoMensajes2 extends Fragment{
    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_mensajes2, container, false);

        return root;

    }
}
