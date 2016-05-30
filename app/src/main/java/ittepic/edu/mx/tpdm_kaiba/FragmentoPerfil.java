package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MARIELA on 28/05/2016.
 */
public class FragmentoPerfil extends Fragment{
    View root;
    TextView puntos,usuario,nivel,tvictorias,tderrotas,victorias,derrotas;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_perfil, container, false);

        Typeface normal = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        Typeface ncursiva = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreamsBoldItalic.ttf");
        Typeface negrita = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreamsBold.ttf");

        puntos = (TextView)root.findViewById(R.id.textView8);
        puntos.setTypeface(normal);

        usuario = (TextView)root.findViewById(R.id.textView);
        usuario.setTypeface(ncursiva);

        nivel = (TextView)root.findViewById(R.id.textView9);
        nivel.setTypeface(normal);

        tvictorias = (TextView)root.findViewById(R.id.textView10);
        tvictorias.setTypeface(negrita);

        tderrotas = (TextView)root.findViewById(R.id.textView11);
        tderrotas.setTypeface(negrita);

        victorias = (TextView)root.findViewById(R.id.textView12);
        victorias.setTypeface(normal);

        derrotas = (TextView)root.findViewById(R.id.textView13);
        derrotas.setTypeface(normal);


        return root;

    }

    public void mostrarResultado(String resultado){
        AlertDialog.Builder alerta= new AlertDialog.Builder(getActivity());


        if(resultado.startsWith("CONFIRMADO")){
            Toast.makeText(getActivity(), "Su cuenta ha sido confirmada", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(),Login.class );
            startActivity(i);
        }
        else{
            if(resultado.startsWith("ERROR2")){
                resultado="Error al enviar el codigo";
            }

            if(resultado.startsWith("INCORRECTO")){
                resultado="Código incorrecto, vuelva a intentarlo";
            }

            alerta.setTitle("ERROR")
                    .setMessage(resultado)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }
}
