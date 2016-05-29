package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {
    EditText usuario,contrasena;
    ImageView img,reg;
    ConexionBD base;
    String usu,cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        base = new ConexionBD(this,"kaiba",null,1);

        usuario=(EditText)findViewById(R.id.editText);
        contrasena=(EditText)findViewById(R.id.editText2);
        img = (ImageView)findViewById(R.id.imageView3);
        reg =(ImageView)findViewById(R.id.imageView4);



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Registro.class);
                startActivity(i);
            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usu=usuario.getText().toString();
                cont=contrasena.getText().toString();
                if (!usuario.getText().toString().equals("") && !contrasena.getText().toString().equals("")) {

                    try {
                        Conexion web = new Conexion(Login.this);
                        web.agregarVariables("usuario", usuario.getText().toString());
                        web.agregarVariables("contrasena", contrasena.getText().toString());

                        web.execute(new URL("http://kaiba.esy.es/login.php"));
                    } catch (MalformedURLException e) {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                        alerta.setTitle("ERROR").setMessage(e.getMessage()).show();
                    }
                }else{
                    AlertDialog.Builder alerta= new AlertDialog.Builder(Login.this);
                    alerta.setTitle("ERROR")
                            .setMessage("Llenar todos los campos")
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

    public void onDestroy(){
        super.onDestroy();
        System.exit(0);

    }



    public void insertar(String us,String con){
        try{
            SQLiteDatabase bd = base.getWritableDatabase();
            String sql = "INSERT INTO USUARIO VALUES('"+us+"','"+con+"','"+"1')";
            bd.execSQL(sql);
            //Toast.makeText(Login.this, "usuario y contraseña recordados", Toast.LENGTH_SHORT).show();
            bd.close();

        }catch(SQLiteException sqle){
            new AlertDialog.Builder(this).setMessage("Inserción erronea: "+sqle.getMessage()).setTitle("ERROR").
                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    public void mostrarResultado(String resultado){

    if(resultado.startsWith("encontrado")){
        insertar(usu,cont);
        Intent i = new Intent(Login.this, Seleccion.class );
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
        if(resultado.startsWith("no encontrado")){
            resultado="Error usuario y/o contraseña incorrectos";
        }
        alerta.setTitle("Alerta")
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
