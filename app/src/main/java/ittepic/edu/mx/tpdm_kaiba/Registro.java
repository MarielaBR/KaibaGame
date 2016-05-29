package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;


public class Registro extends AppCompatActivity {
    ImageView aceptar;
    EditText usuario,contrasena,email,telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registro);

        aceptar = (ImageView)findViewById(R.id.imageView5);
        usuario=(EditText)findViewById(R.id.editText3);
        contrasena=(EditText)findViewById(R.id.editText4);
        email=(EditText)findViewById(R.id.editText5);
        telefono=(EditText)findViewById(R.id.editText6);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!telefono.getText().toString().equals("") && !usuario.getText().toString().equals("") && !contrasena.getText().toString().equals("")&& !email.getText().toString().equals("")){
                    if(telefono.getText().toString().matches("[0-9]*") && telefono.getText().toString().length()==10) {
                        if(email.getText().toString().matches(("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) && email.getText().length()<100) {
                            if(usuario.getText().toString().matches("[A-Za-z0-9]*") && usuario.getText().toString().length()<=20){
                                if(contrasena.getText().toString().length()<=20) {
                                    try {
                                        ConexionRegistro web = new ConexionRegistro(Registro.this);
                                        web.agregarVariables("usuario", usuario.getText().toString());
                                        web.agregarVariables("contrasena", contrasena.getText().toString());
                                        web.agregarVariables("email", email.getText().toString());
                                        web.agregarVariables("telefono", telefono.getText().toString());
                                        web.execute(new URL("http://kaiba.esy.es/buscarinsertarusuario.php"));

                                    } catch (MalformedURLException e) {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                                        alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
                                    }
                                }
                                else{
                                    AlertDialog.Builder alerta= new AlertDialog.Builder(Registro.this);
                                    alerta.setTitle("Error")
                                            .setMessage("Contraseña menor a 20 caracteres")
                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                }
                        }else
                        {
                            AlertDialog.Builder alerta= new AlertDialog.Builder(Registro.this);
                            alerta.setTitle("Error")
                                    .setMessage("Formato de Usuario inválido, solo números y letras, máximo 20 caracteres")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }

                    }
                    else {
                        AlertDialog.Builder alerta= new AlertDialog.Builder(Registro.this);
                        alerta.setTitle("Error")
                                .setMessage("Formato de email inválido")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                }
                else{
                    AlertDialog.Builder alerta= new AlertDialog.Builder(Registro.this);
                    alerta.setTitle("Error")
                            .setMessage("Número de teléfono inválido")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();

                }

            }
                else{
                    AlertDialog.Builder alerta= new AlertDialog.Builder(Registro.this);
                    alerta.setTitle("Error")
                            .setMessage("Rellene todos los campos")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
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
            if(resultado.startsWith("CORREO DUPLICADO")){
                resultado="YA EXISTE UNA CUENTA CON ESE CORREO ELECTRÓNICO";
            }
            if(resultado.startsWith("TELEFONO DUPLICADO")){
                resultado="YA EXISTE UNA CUENTA CON ESE NUMERO DE TELÈFONO";
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
        }}
}
