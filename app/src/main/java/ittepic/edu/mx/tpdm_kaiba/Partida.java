package ittepic.edu.mx.tpdm_kaiba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Partida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //oculto titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //ocultamos barras de notifiaciones
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new MySurfaceView(this));
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        setContentView(null);

        finish();
    }
}
