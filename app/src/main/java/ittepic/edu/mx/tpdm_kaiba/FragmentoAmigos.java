package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MARIELA on 28/05/2016.
 */
public class FragmentoAmigos extends Fragment{
    View root;
    ConexionBD base;
    String usu;
    ListView lista;
    ItemAdapter adaptador;
    String[] amigos;
    int[] personajes;
    int conexion;
    ImageView solicitudes,buscar,imagen;
    TextView texto;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_amigos, container, false);

        lista=(ListView)root.findViewById(R.id.listView3);
        solicitudes=(ImageView)root.findViewById(R.id.imageView25);
        buscar=(ImageView)root.findViewById(R.id.imageView30);

        base= new ConexionBD(getActivity(),"kaiba",null,1);
        conexion=1;
        consultarusuario();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int pos=position;

                AlertDialog.Builder alerta=new AlertDialog.Builder(getActivity());
                alerta.setTitle("Eliminar amigo");
                alerta.setMessage("¿Estás seguro que deseas eliminar?");
                alerta.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarAmigo(amigos[pos]);
                    }
                });
                alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alerta.show();


            }
        });

        solicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contenedor_fragment, new FragmentoSolicitudes());
                ft.commit();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contenedor_fragment, new FragmentoBuscar());
                ft.commit();
            }
        });

        obtenerAmigos();

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

    public void obtenerAmigos(){
        try {
            conexion=1;
            ConexionAmigos web = new ConexionAmigos(FragmentoAmigos.this);
            web.agregarVariables("usuario", usu);
            web.execute(new URL("http://kaiba.esy.es/amigos.php"));
        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }
    }

    public void eliminarAmigo(String amigo){
        try {
            conexion=2;
            ConexionAmigos web = new ConexionAmigos(FragmentoAmigos.this);
            web.agregarVariables("usuario", usu);
            web.agregarVariables("amigo",amigo);
            web.execute(new URL("http://kaiba.esy.es/eliminaramigo.php"));
        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }
    }

    public void mostrarResultado(String resultado){
        if(conexion==1){
            AlertDialog.Builder alerta= new AlertDialog.Builder(getActivity());

            if(resultado.startsWith("Error")){
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
                String [] res=resultado.split("-");
                String [] r;
                amigos=new String[res.length-1];
                personajes=new int[res.length-1];
                for(int i=0;i<res.length-1;i++){
                    r=res[i].split(",");
                    amigos[i]=r[0];
                    switch(Integer.parseInt(r[1])){
                        case 1:
                            personajes[i]=R.drawable.i1;
                            break;
                        case 2:
                            personajes[i]=R.drawable.i2;
                            break;
                        case 3:
                            personajes[i]=R.drawable.i3;
                            break;
                    }
                }

                adaptador=new ItemAdapter(getActivity(),amigos,personajes);
                lista.setAdapter(adaptador);
            }
        }

        if(conexion==2){
            obtenerAmigos();
        }
    }

}
