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
        codigo = getIntent().getStringExtra("codigo");
        usuario=getIntent().getStringExtra("usuario");
        aceptar = (ImageView)findViewById(R.id.imageView6);
        campocodigo=(EditText)findViewById(R.id.editText7);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(campocodigo.getText().toString().equals(codigo)){
                    Intent i = new Intent(Confirmacion.this, Login.class);
                    startActivity(i);
                }
                else{
                    AlertDialog.Builder alerta= new AlertDialog.Builder(Confirmacion.this);
                    alerta.setTitle("ERROR")

                            .setMessage("Código Incorrecto, vuelva a ingresar el código de confirmación")
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

        }


}
