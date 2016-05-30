package ittepic.edu.mx.tpdm_kaiba;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by MARIELA on 28/05/2016.
 */
public class FragmentoPartida extends Fragment{
    View root;
    ImageView sig;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_partida, container, false);

        sig = (ImageView)root.findViewById(R.id.imageView26);


        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Partida.class );
                startActivity(i);
            }
        });


        return root;


    }
}
