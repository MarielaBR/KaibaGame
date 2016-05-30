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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_amigos, container, false);




        base= new ConexionBD(getActivity(),"kaiba",null,1);
        consultarusuario();
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

            ConexionAmigos web = new ConexionAmigos(FragmentoAmigos.this);
            web.agregarVariables("usuario", usu);
            web.execute(new URL("http://kaiba.esy.es/amigos.php"));
        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }
    }



    public void mostrarResultado(String resultado){
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
            Toast.makeText(getActivity(),resultado,Toast.LENGTH_SHORT).show();
        }
    }

}
