package ittepic.edu.mx.tpdm_kaiba;

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

/**
 * Created by Cesar_pruefkd on 28/05/2016.
 */
public class Conversacion extends AppCompatActivity{
    EditText campo;
    TextView area;
    String msj,rec,idRem;
    ImageView enviar;
    Thread t;
    Bundle datos;
    boolean ref;
    final Handler handle = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seleccion);

        idRem = "Bearnal";
        area=(TextView)findViewById(R.id.TextView17);
        campo=(EditText)findViewById(R.id.editText8);
        enviar =(ImageView)findViewById(R.id.imageView24);
        ref=true;
        area.setMovementMethod(new ScrollingMovementMethod());

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

    private void cargarMensajes(){/*
        ConexionHTTP con = new ConexionHTTP();

        con.agregarVariables("ID_PACIENTE", idPaciente);

        String resp = con.conectarHTTP("http://diamondnutrition.co.nf/cargar_mensajes_admin.php");

        resp = resp.substring(resp.indexOf("-msj-")+5,resp.indexOf("-/msj-"));
        String[] vectorP = resp.split("&&");

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
*/

    }

    private void enviarMensaje(){/*
        ConexionHTTP con = new ConexionHTTP();

        con.agregarVariables("ID_PACIENTE", idPaciente);
        con.agregarVariables("MENSAJE", campo.getText().toString());


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

}

