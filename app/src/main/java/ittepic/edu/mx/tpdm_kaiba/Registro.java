package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class Registro extends AppCompatActivity {
    Button aceptar,registro;
    EditText usuario,contrasena,email,telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registro);

        aceptar = (Button)findViewById(R.id.button3);
        usuario=(EditText)findViewById(R.id.editText3);
        contrasena=(EditText)findViewById(R.id.editText4);
        email=(EditText)findViewById(R.id.editText5);
        telefono=(EditText)findViewById(R.id.editText6);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    ConexionRegistro web= new ConexionRegistro(Registro.this);
                    web.agregarVariables("usuario",usuario.getText().toString());
                    web.agregarVariables("contrasena",contrasena.getText().toString());
                    web.agregarVariables("email",email.getText().toString());
                    web.agregarVariables("telefono",telefono.getText().toString());
                    web.execute(new URL("http://kaiba.esy.es/buscarinsertarusuario.php"));

                }catch(MalformedURLException e){
                    AlertDialog.Builder  alerta= new AlertDialog.Builder(Registro.this);
                    alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
                }

            }
        });


    }


    public void mostrarResultado(String resultado){

        if(resultado.startsWith("EXITO")){
            Toast.makeText(this,"cuenta creada correctamente",Toast.LENGTH_LONG).show();
            Intent i = new Intent(Registro.this, Login.class );
            startActivity(i);
        }else{
            AlertDialog.Builder alerta= new AlertDialog.Builder(this);

            if(resultado.startsWith("Error_404_1")){
                resultado="Hosting no encontrado";
            }
            if(resultado.startsWith("Error_404_2")){
                resultado="Error al ENVIAR/RECIBIR informacion con el PHP";
            }
            if(resultado.startsWith("Error_404")){
                resultado="Al parecer no existe el PHP buscado";
            }
            if(resultado.startsWith("ERROR1")){
                resultado="Datos incorrectos";
            }
            if(resultado.startsWith("EXISTE")){
                resultado="USUARIO EXISTENTE ESCRIBA OTRO NOMBRE DE USUARIO DIFERENTE";
            }
            alerta.setTitle("Respuesta desde SERVIDOR:")
                    .setMessage(resultado)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }}
}
