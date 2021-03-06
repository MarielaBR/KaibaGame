package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    TextView area,titulo;
    String msj,rec;
    
    ImageView enviar;
    Thread t;
    boolean ref;
    final Handler handle = new Handler();
    String usu,dest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_conversacion);


        area=(TextView)findViewById(R.id.TextView17);
        campo=(EditText)findViewById(R.id.editText8);
        enviar =(ImageView)findViewById(R.id.enviarMensaje);
        titulo=(TextView)findViewById(R.id.textView6);
        ref=true;
        area.setMovementMethod(new ScrollingMovementMethod());

        dest = getIntent().getStringExtra("destinatario");
        usu = getIntent().getStringExtra("usuario");

        Typeface normal = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        titulo.setTypeface(normal);
        titulo.setText("Conversación con "+dest);



        cargarMensajes();
        miThread();

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
            web.agregarVariables("DEST",dest);

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
            web.agregarVariables("usuario", dest);
            web.agregarVariables("rem",usu);
            web.agregarVariables("mensaje", campo.getText().toString());


            //change
            web.execute(new URL("http://kaiba.esy.es/enviarmensaje.php"));

        } catch (MalformedURLException e) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Conversacion.this);
            alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
        }

    }

    public void onDestroy() {
        ref = false;
        super.onDestroy();
    }

    public void mostrarResultado(String resultado){
        AlertDialog.Builder alerta= new AlertDialog.Builder(Conversacion.this);


        if(resultado.startsWith("Mensaje")){
            Toast.makeText(Conversacion.this,"Mensaje enviado",Toast.LENGTH_LONG).show();
        }else if(resultado.startsWith("-msj")){
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


        }else if (resultado.startsWith("/**/")){

        }  else {
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

