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
public class FragmentoMensajes extends Fragment{
    View root;
    ImageView ir;
    String usu;
    TextView texto;
    ListView lista;
    ItemAdapter adaptador;
    String[] amigos;
    int[] personajes;

    ConexionBD base;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_mensajes, container, false);


        Typeface normal = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");
        texto=(TextView)root.findViewById(R.id.textView4);
        texto.setTypeface(normal);

        lista=(ListView)root.findViewById(R.id.listView2);


        base = new ConexionBD(getActivity(),"kaiba",null,1);

        consultarusuario();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), Conversacion.class);
                i.putExtra("usuario", usu);
                i.putExtra("destinatario",amigos[position]);

                startActivity(i);
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

            ConexionMensajes web = new ConexionMensajes(FragmentoMensajes.this);
            web.agregarVariables("usuario", usu);
            web.execute(new URL("http://kaiba.esy.es/amigosmensaje.php"));
        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }
    }

    public void mostrarResultado(String resultado){
        AlertDialog.Builder alerta= new AlertDialog.Builder(getActivity());

        Toast.makeText(getActivity(), resultado, Toast.LENGTH_SHORT).show();

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
            Toast.makeText(getActivity(), res.length+"", Toast.LENGTH_SHORT).show();
            String [] r;
            amigos=new String[res.length];
            personajes=new int[res.length];
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
            Toast.makeText(getActivity(), resultado, Toast.LENGTH_SHORT).show();
        }
    }

}
