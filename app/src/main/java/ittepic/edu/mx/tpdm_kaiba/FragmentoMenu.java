package ittepic.edu.mx.tpdm_kaiba;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by MARIELA on 28/05/2016.
 */
public class FragmentoMenu extends Fragment{
    View root;
    ImageView inicio,partida,amigos,mensajes,puntuaciones,salir;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_menu, container, false);

        inicio = (ImageView) root.findViewById(R.id.imageView6);
        partida = (ImageView) root.findViewById(R.id.imageView7);
        amigos = (ImageView) root.findViewById(R.id.imageView8);
        mensajes = (ImageView) root.findViewById(R.id.imageView9);
        puntuaciones = (ImageView) root.findViewById(R.id.imageView10);
        salir = (ImageView) root.findViewById(R.id.imageView11);

        /*alta.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                alta.setImageResource(R.drawable.palta_citas);
                baja.setImageResource(R.drawable.baja_citas);
                modificacion.setImageResource(R.drawable.modificar_citas);
                consulta.setImageResource(R.drawable.consultar_citas);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contenedor_fragment, new FragmentoAltaCita());
                ft.commit();
            }
        });*/


        return root;

    }


}
