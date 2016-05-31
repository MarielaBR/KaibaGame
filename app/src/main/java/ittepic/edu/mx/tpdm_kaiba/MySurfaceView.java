package ittepic.edu.mx.tpdm_kaiba;


import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.graphics.Canvas;
import android.view.MotionEvent;
/**
 * Created by cesar_pruefkd on 30/05/2016.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    //Referencia a un thread usaremos para dibujar
    public MySurfaceThread thread;

    public MySurfaceView(Context context) {
        super(context);
        //usaremos esta clase como manejador
        getHolder().addCallback(this);


    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MySurfaceThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("surfaceDestroyed ", "Hilo detenido ");

        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public void onDraw(Canvas canvas) {

    }

    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        //Lo veremos en la siguiente entrega
        return true;

    }

}
