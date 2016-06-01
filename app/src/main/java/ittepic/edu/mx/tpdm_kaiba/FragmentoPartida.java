package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MARIELA on 28/05/2016.
 */
public class FragmentoPartida extends Fragment{
    RelativeLayout layout;
    View root;
    ImageView sig;
    ConexionBD base;
    String usu="";

    Thread t;
    boolean ref;
    final Handler handle = new Handler();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragmento_partida, container, false);

        sig = (ImageView)root.findViewById(R.id.imageView26);
        base= new ConexionBD(getActivity(),"kaiba",null,1);

        consultarusuario();
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout.setBackgroundResource(R.drawable.fondobu);
                sig.setImageResource(R.drawable.btnbuju2);
                consultar();
            }
        });


        return root;


    }


    public void onDestroy() {
        ref = false;
        super.onDestroy();
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
    public void consultar(){
        try {

            ConexionCola web = new ConexionCola(FragmentoPartida.this);
            web.agregarVariables("USUARIO", usu);

            //change
            web.execute(new URL("http://kaiba.esy.es/buscarjugador.php"));

        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }
    }


    protected void miThread(){
        t = new Thread(){
            public void run(){
                try{
                    while(ref){
                        handle.post(proceso);
                        Thread.sleep(5000);
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }
    final Runnable proceso = new Runnable(){
        public void run(){
            consultar();
        }
    };



    public void mostrarResultado(String resultado){
        if(resultado!=""){
            String cadenaenviar[]=resultado.split(",");

            Intent i = new Intent(getActivity(), Partida.class );
            i.putExtra("usuario1", cadenaenviar[0]);
            i.putExtra("tipo1", cadenaenviar[1]);
            i.putExtra("usuario2", cadenaenviar[2]);
            i.putExtra("tipo2", cadenaenviar[3]);
            i.putExtra("id", cadenaenviar[4]);
            startActivity(i);
        }


    }//mostrar res
}
