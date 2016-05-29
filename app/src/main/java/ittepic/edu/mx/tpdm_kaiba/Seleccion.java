package ittepic.edu.mx.tpdm_kaiba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.database.Cursor;
import android.view.WindowManager;

public class Seleccion extends AppCompatActivity {
    ImageView p1,p2,p3,t1,t2,t3,aceptar;
    ConexionBD base;
    String usu;
    int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seleccion);

        base = new ConexionBD(this,"kaiba",null,1);
        p1 = (ImageView)findViewById(R.id.imageView12);
        p2 = (ImageView)findViewById(R.id.imageView13);
        p3 = (ImageView)findViewById(R.id.imageView14);

        t1 = (ImageView)findViewById(R.id.imageView16);
        t2 = (ImageView)findViewById(R.id.imageView17);
        t3 = (ImageView)findViewById(R.id.imageView18);
        p = 2;
        aceptar = (ImageView)findViewById(R.id.imageView19);


        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1.setImageResource(R.drawable.p1);
                p2.setImageResource(R.drawable.p2g);
                p3.setImageResource(R.drawable.p3g);

                t1.setImageResource(R.drawable.n1);
                t2.setImageResource(R.drawable.n2e);
                t3.setImageResource(R.drawable.n3e);

                p=1;
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1.setImageResource(R.drawable.p1g);
                p2.setImageResource(R.drawable.p2);
                p3.setImageResource(R.drawable.p3g);

                t1.setImageResource(R.drawable.n1e);
                t2.setImageResource(R.drawable.n2);
                t3.setImageResource(R.drawable.n3e);

                p=2;
            }
        });
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p1.setImageResource(R.drawable.p1g);
                p2.setImageResource(R.drawable.p2g);
                p3.setImageResource(R.drawable.p3);

                t1.setImageResource(R.drawable.n1e);
                t2.setImageResource(R.drawable.n2e);
                t3.setImageResource(R.drawable.n3);
                p=3;
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarusuario();
                System.out.println(usu);
            }
        });

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
            new AlertDialog.Builder(this).setMessage("Consulta erronea: "+sqle.getMessage()).setTitle("ERROR").
                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}
