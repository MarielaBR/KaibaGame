package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class Confirmacion extends AppCompatActivity {
    ImageView aceptar;
    String codigo,usuario;
    EditText campocodigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_confirmacion);
        //codigo = getIntent().getStringExtra("codigo");
        usuario=getIntent().getStringExtra("usuario");
        aceptar = (ImageView)findViewById(R.id.imageView6);
        campocodigo=(EditText)findViewById(R.id.editText7);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    ConexionConfirmacion web = new ConexionConfirmacion(Confirmacion.this);
                    web.agregarVariables("usuario", usuario);
                    web.agregarVariables("codigo",campocodigo.getText().toString() );
                    web.execute(new URL("http://kaiba.esy.es/confirmacion.php"));

                } catch (MalformedURLException e) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Confirmacion.this);
                    alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
                }

            }
        });
    }





    public void mostrarResultado(String resultado){
        AlertDialog.Builder alerta= new AlertDialog.Builder(Confirmacion.this);


        if(resultado.startsWith("CONFIRMADO")){
            Intent i = new Intent(Confirmacion.this,Login.class );
            alerta.setTitle("Felicidades")
                    .setMessage("Se ha confirmado su cuenta")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            startActivity(i);

        }
        else{
            if(resultado.startsWith("ERROR2")){
                    resultado="Error al enviar el codigo";
            }

            if(resultado.startsWith("INCORRECTO")){
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
