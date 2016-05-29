package ittepic.edu.mx.tpdm_kaiba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.view.WindowManager;

public class Seleccion extends AppCompatActivity {
    ImageView p1,p2,p3,t1,t2,t3,aceptar;
    int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seleccion);

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
                
            }
        });

    }
}
