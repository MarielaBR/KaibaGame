package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MARIELA on 28/05/2016.
 */
public class FragmentoPerfil extends Fragment{
    View root;
    TextView puntos,usuario,nivel,tvictorias,tderrotas,victorias,derrotas;
    int niv,per,punt,vic,derr;
    String usu;
    ImageView imagen;
    ConexionBD base;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_perfil, container, false);

        Typeface normal = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        Typeface ncursiva = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreamsBoldItalic.ttf");
        Typeface negrita = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreamsBold.ttf");

        puntos = (TextView)root.findViewById(R.id.textView8);
        puntos.setTypeface(normal);

        usuario = (TextView)root.findViewById(R.id.textView);
        usuario.setTypeface(negrita);

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

        imagen=(ImageView)root.findViewById(R.id.imageView12);

        base= new ConexionBD(getActivity(),"kaiba",null,1);

        consultarusuario();

        try {

            ConexionPerfil web = new ConexionPerfil(FragmentoPerfil.this);
            web.agregarVariables("usuario", usu);
            web.execute(new URL("http://kaiba.esy.es/confirmacion.php"));
        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }

        return root;

    }

    public void consultarusuario(){
        try{
            SQLiteDatabase bd = base.getReadableDatabase();
            String sql = "SELECT USUARIO FROM USUARIO ";
            Cursor res = bd.rawQuery(sql, null);
            if(res.moveToFirst()){
                usu = res.getString(0);
                bd.close();
            }else{
                bd.close();
            }


        }catch(SQLiteException sqle){
            new AlertDialog.Builder(getActivity()).setMessage("Consulta erronea: "+sqle.getMessage()).setTitle("ERROR").
                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    public void mostrarResultado(String resultado){
        AlertDialog.Builder alerta= new AlertDialog.Builder(getActivity());


        if(resultado.startsWith("NO")){
            alerta.setTitle("ERROR")
                    .setMessage(resultado)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }else if(resultado.startsWith("ERROR")){
            if(resultado.startsWith("ERROR2")){
                resultado="No se insertó el estado conectado";
            }

            if(resultado.startsWith("Error_404")){
                resultado="Host inaccesible";
            }

            if(resultado.startsWith("Error_404_1")){
                resultado="No existe el host";
            }

            if(resultado.startsWith("Error_404_1")){
                resultado="Fallo flujo de datos";
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
        }else{
            String [] res=resultado.split(",");
            niv=Integer.parseInt(res[0]);
            per=Integer.parseInt(res[1]);
            punt=Integer.parseInt(res[2]);
            vic=Integer.parseInt(res[3]);
            derr=Integer.parseInt(res[4]);

            nivel.setText("Nivel " + niv);
            switch (per){
                case 1:
                    imagen.setImageResource(R.drawable.b1);
                    break;
                case 2:
                    imagen.setImageResource(R.drawable.b2);
                    break;
                case 3:
                    imagen.setImageResource(R.drawable.b3);
                    break;
            }
            puntos.setText(punt+" puntos");
            victorias.setText(vic+"");
            derrotas.setText(derr+"");

        }
    }
}
