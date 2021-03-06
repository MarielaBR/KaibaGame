package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
    ConexionBD bd;
    ImageView inicio,partida,amigos,mensajes,puntuaciones,salir;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_menu, container, false);

        inicio = (ImageView) root.findViewById(R.id.imageView6);
        partida = (ImageView) root.findViewById(R.id.imageView7);
        amigos = (ImageView) root.findViewById(R.id.imageView8);
        mensajes = (ImageView) root.findViewById(R.id.imageView9);
        puntuaciones = (ImageView) root.findViewById(R.id.imageView10);
        salir = (ImageView) root.findViewById(R.id.imageView11);

        bd = new ConexionBD(getActivity(),"kaiba",null,1);

        inicio.setImageResource(R.drawable.kaibap2);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicio.setImageResource(R.drawable.kaibap2);
                partida.setImageResource(R.drawable.partida);
                amigos.setImageResource(R.drawable.amigos);
                mensajes.setImageResource(R.drawable.mensajes);
                puntuaciones.setImageResource(R.drawable.puntuaciones);
                salir.setImageResource(R.drawable.salir);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contenedor_fragment, new FragmentoPerfil());
                ft.commit();
            }
        });

        partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicio.setImageResource(R.drawable.kaibap);
                partida.setImageResource(R.drawable.partida2);
                amigos.setImageResource(R.drawable.amigos);
                mensajes.setImageResource(R.drawable.mensajes);
                puntuaciones.setImageResource(R.drawable.puntuaciones);
                salir.setImageResource(R.drawable.salir);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contenedor_fragment, new FragmentoPartida());
                ft.commit();
            }
        });

        amigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicio.setImageResource(R.drawable.kaibap);
                partida.setImageResource(R.drawable.partida);
                amigos.setImageResource(R.drawable.amigos2);
                mensajes.setImageResource(R.drawable.mensajes);
                puntuaciones.setImageResource(R.drawable.puntuaciones);
                salir.setImageResource(R.drawable.salir);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contenedor_fragment, new FragmentoAmigos());
                ft.commit();
            }
        });

        mensajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicio.setImageResource(R.drawable.kaibap);
                partida.setImageResource(R.drawable.partida);
                amigos.setImageResource(R.drawable.amigos);
                mensajes.setImageResource(R.drawable.mensajes2);
                puntuaciones.setImageResource(R.drawable.puntuaciones);
                salir.setImageResource(R.drawable.salir);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contenedor_fragment, new FragmentoMensajes());
                ft.commit();
            }
        });

        puntuaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicio.setImageResource(R.drawable.kaibap);
                partida.setImageResource(R.drawable.partida);
                amigos.setImageResource(R.drawable.amigos);
                mensajes.setImageResource(R.drawable.mensajes);
                puntuaciones.setImageResource(R.drawable.puntuaciones2);
                salir.setImageResource(R.drawable.salir);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contenedor_fragment, new FragmentoPuntuaciones());
                ft.commit();
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicio.setImageResource(R.drawable.kaibap);
                partida.setImageResource(R.drawable.partida);
                amigos.setImageResource(R.drawable.amigos);
                mensajes.setImageResource(R.drawable.mensajes);
                puntuaciones.setImageResource(R.drawable.puntuaciones);
                salir.setImageResource(R.drawable.salir2);

                AlertDialog.Builder alerta=new AlertDialog.Builder(root.getContext());
                alerta.setTitle("Cerrar Sesión");
                alerta.setMessage("¿Estás seguro que deseas cerrar la sesión?");
                alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actualizar();
                        Intent ventana=new Intent(getActivity(),Login.class);
                        startActivity(ventana);
                        getActivity().finish();
                    }
                });
                alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.contenedor_fragment, new FragmentoPerfil());
                        ft.commit();
                        inicio.setImageResource(R.drawable.kaibap2);
                    }
                });
                alerta.show();

            }
        });

        return root;

    }

    public void actualizar(){
        try{
            SQLiteDatabase base = bd.getWritableDatabase();
            String sql = "UPDATE USUARIO SET STATUS= '0'";
            base.execSQL(sql);
        }catch(SQLiteException sqle){
            new AlertDialog.Builder(getActivity()).setMessage("error al actualizar "+sqle.getMessage()).setTitle("ERROR").
                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }


}
