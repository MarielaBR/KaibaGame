package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;
import android.os.Handler;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Cesar_pruefkd on 28/05/2016.
 */
public class Conversacion extends AppCompatActivity{
    EditText campo;
    TextView area;
    String msj,rec,idRem;
    
    ImageView enviar;
    Thread t;
    boolean ref;
    final Handler handle = new Handler();
    String usu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_conversacion);

        idRem = "Bearnal";
        area=(TextView)findViewById(R.id.TextView17);
        campo=(EditText)findViewById(R.id.editText8);
        enviar =(ImageView)findViewById(R.id.enviarMensaje);
        ref=true;

        usu = getIntent().getStringExtra("usuario");

        cargarMensajes();
        //miThread();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!campo.getText().toString().equals("")) {
                    enviarMensaje();
                    campo.setText("");
                }
            }
        });

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
            cargarMensajes();
        }
    };

    private void cargarMensajes(){

        try {

            ConexionConversacion web = new ConexionConversacion(Conversacion.this);
            web.agregarVariables("USUARIO", usu);
            web.agregarVariables("DEST",idRem);
            web.agregarVariables("usuario", idRem);

            //change
            web.execute(new URL("http://kaiba.esy.es/cargarmensaje.php"));

        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Conversacion.this);
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }


    }

    private void enviarMensaje(){
        try {

            ConexionConversacion web = new ConexionConversacion(Conversacion.this);
            web.agregarVariables("usuario", usu);
            web.agregarVariables("rem",idRem);
            web.agregarVariables("mensaje", campo.getText().toString());


            //change
            web.execute(new URL("http://kaiba.esy.es/enviarmensaje.php"));

        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Conversacion.this);
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }

    /*
        String resp = con.conectarHTTP("http://diamondnutrition.co.nf/enviar_msj_paciente.php");

        resp = resp.substring(resp.indexOf("-msj-")+5,resp.indexOf("-/msj-"));

        Toast.makeText(this,resp,Toast.LENGTH_SHORT).show();

        if(resp.equals("Mensaje enviado.")){
            cargarMensajes();
            return;
        }*/
    }

    public void onDestroy() {
        ref = false;
        super.onDestroy();
    }

    public void mostrarResultado(String resultado){
        AlertDialog.Builder alerta= new AlertDialog.Builder(Conversacion.this);


        if(resultado.startsWith("Mensaje")){
            Toast.makeText(Conversacion.this,"Mensaje enviado",Toast.LENGTH_LONG).show();
        }
        if(resultado.startsWith("-msj")){
            resultado = resultado.substring(resultado.indexOf("-msj-")+5,resultado.indexOf("-/msj-"));
            String[] vectorP = resultado.split("&&");
            area.setText("");
            if(vectorP[0].equals(" ")){
                area.setText("Escribe mensaje.");
                return;
            }
            if(area.getText().toString().equals("Escribe mensaje."))
                area.setText("");
            for(int i=vectorP.length-1;i>=0;i--){
                area.setText(area.getText().toString()+'\n'+vectorP[i]);
            }


        } else {
            if (resultado.startsWith("ERROR2")) {
                resultado = "Error al enviar el codigo";
            }

            if (resultado.startsWith("INCORRECTO")) {
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

