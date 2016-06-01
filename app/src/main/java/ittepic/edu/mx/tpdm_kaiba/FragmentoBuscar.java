package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MARIELA on 01/06/2016.
 */
public class FragmentoBuscar extends Fragment{
    View root;
    ConexionBD base;
    String usu;
    ListView lista;
    ItemAdapter adaptador;
    String[] amigos;
    int[] personajes;
    int conexion;
    ImageView buscar;
    EditText edittext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_buscar, container, false);
        lista=(ListView)root.findViewById(R.id.listView4);
        buscar=(ImageView)root.findViewById(R.id.imageView29);
        edittext=(EditText)root.findViewById(R.id.editText9);
        conexion=1;

        base= new ConexionBD(getActivity(),"kaiba",null,1);
        conexion=1;
        consultarusuario();

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar(edittext.getText().toString());
            }
        });

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

    public void buscar(String usuario){
        try {
            conexion=1;
            ConexionBuscar web = new ConexionBuscar(FragmentoBuscar.this);
            web.agregarVariables("usuario", usuario);
            web.execute(new URL("http://kaiba.esy.es/buscar.php"));
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
                String [] res=resultado.split(",");
                amigos=new String []{res[0]};
                personajes=new int [1];

                switch(Integer.parseInt(res[1])) {
                    case 1:
                        personajes[0] = R.drawable.i1;
                        break;
                    case 2:
                        personajes[0] = R.drawable.i2;
                        break;
                    case 3:
                        personajes[0] = R.drawable.i3;
                        break;
                }

                adaptador=new ItemAdapter(getActivity(),amigos,personajes);
                lista.setAdapter(adaptador);
            }
        }

        if(conexion==2){

        }
    }

}
