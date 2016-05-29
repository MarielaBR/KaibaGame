package ittepic.edu.mx.tpdm_kaiba;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    CCanvas lienzo;
    int height,width,transparencia,tl,jugary,xj,yj;
    CountDownTimer timer,timer2;
    Bitmap fondo,mini,desliza;
    Matrix matrix;
    Boolean band1;
    ConexionBD bd;
    String user,pass,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_principal);
        bd = new ConexionBD(this,"kaiba",null,1);
        lienzo= new CCanvas(this);
        setContentView(lienzo);
        matrix = new Matrix();
        band1=false;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        transparencia=tl=0;

        fondo= BitmapFactory.decodeResource(getResources(), R.drawable.logohor);
        mini= BitmapFactory.decodeResource(getResources(), R.drawable.kaiba2);
        desliza= BitmapFactory.decodeResource(getResources(), R.drawable.deslizar);

        timer=new CountDownTimer(5000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(transparencia<251){
                    transparencia+=7;
                }else {
                    timer.onFinish();
                    timer.cancel();
                }
                lienzo.invalidate();
            }
            @Override
            public void onFinish() {
                fond();
            }
        };
        timer.start();


    }

    public class CCanvas extends View {

        public CCanvas(Context context){
            super(context);
            this.setBackgroundColor(Color.BLACK);
        }
        public void onDraw(Canvas c){
            Paint p=new Paint();
            Paint d=new Paint();

            p.setAlpha(tl);
            c.drawBitmap(fondo, 0, 0, p);

            p.setAlpha(transparencia);
            c.drawBitmap(mini, (c.getWidth() / 2) - 620, (c.getHeight() / 2) - 300, p);



        }

        public boolean onTouchEvent(MotionEvent e){
            if(e.getAction() == MotionEvent.ACTION_DOWN){
                consulta();
                if(status.equals("1")) {
                    Intent i = new Intent(Principal.this, MenuPrincipal.class);
                    startActivity(i);
                    Principal.this.finish();
                }
                else{
                    Intent i = new Intent(Principal.this, Login.class);
                    startActivity(i);
                    Principal.this.finish();
                }
            }
            return true;
        }

    }



    public void consulta(){
        try{
            SQLiteDatabase base = bd.getReadableDatabase();
            String sql = "SELECT * FROM USUARIO";
            Cursor res = base.rawQuery(sql, null);
            if(res.moveToFirst()){
                user=res.getString(0);
                pass=res.getString(1);
                status=res.getString(2);
            }else{
                user="";
                pass="";
                status="";
            }
            base.close();

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







    public void fond(){
        timer2=new CountDownTimer(5000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(tl<250){
                    tl+=10;
                }else{
                    timer2.onFinish();
                    timer2.cancel();
                }
                lienzo.invalidate();
            }
            @Override
            public void onFinish() {
                consulta();
                if(status.equals("1")) {
                    Intent i = new Intent(Principal.this, MenuPrincipal.class);
                    startActivity(i);
                    Principal.this.finish();
                }
                else{
                    Intent i = new Intent(Principal.this, Login.class);
                    startActivity(i);
                    Principal.this.finish();
                }
            }
        };
        timer2.start();
    }

}
