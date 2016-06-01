package ittepic.edu.mx.tpdm_kaiba;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.Log;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.os.CountDownTimer;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cesar_pruefkd on 30/05/2016.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    Drawable fondo;
    Bitmap pm,pv,pa,p1,cajon;
    final Handler handle = new Handler();
    float lim1,lim2,sum;
    boolean b1,estatico,bandera,sem1;
    boolean ref;
    Point punto;
    int cActual[],cSig[];
    int ancho,alto,jug1,jug2,sesion,habilidad,casilla1,casilla2;
    int habilidad2,x2,y2,turno2;
    Point m[][];
    Thread j;
    CountDownTimer mov;
    public MySurfaceThread thread;
    Thread t;
    Jugador juga1,juga2;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

        fondo = getResources().getDrawable(R.drawable.prueba1);
        pm = BitmapFactory.decodeResource(getResources(), R.drawable.boots);
        pa = BitmapFactory.decodeResource(getResources(), R.drawable.sword);
        pv = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        cajon = BitmapFactory.decodeResource(getResources(),R.drawable.cajon);
        b1=true;
        sem1 = false;

        estatico = true;
        ref=true;

        m = new Point[6][6];
        cActual = new int[2];
        cSig = new int[2];
        cActual[0]=1;
        cActual[1]=1;


        jug1= 1;
        jug2= 2;

        sesion = 1;

        juga1 = new Jugador(jug1,"",MySurfaceView.this,1);
        juga2 = new Jugador(jug2,"",MySurfaceView.this,0);

        juga1.pos[0] = 1;
        juga1.pos[1] = 1;

        juga2.pos[0] = 4;
        juga2.pos[1] = 4;

        bandera = true;

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

    public void onDraw(Canvas c) {
        c.drawColor(Color.BLACK);
        Paint p = new Paint();
        p.setAntiAlias(true);


        ancho = c.getWidth();
        alto = c.getHeight();
        c.drawColor(Color.BLACK);
        p.setAntiAlias(true);

        lim1 = (float) (ancho * 0.20);
        lim2 = (float) (ancho * 0.80);
        sum = (float) (ancho * 0.10);


        if (b1) {
            for (int i = 0; i < 6; i++) {
                for (int j = -3; j < 3; j++) {
                    punto = new Point();
                    punto.set((int) (((sum * (i + 0.5)) + lim1) ), (int) ((((alto / 2) + (sum * (j + 0.5))))));
                    m[i][j + 3] = punto;
                }
            }

            juga1.x=m[1][1].x;
            juga1.y=m[1][1].y;

            juga2.x=m[4][4].x;
            juga2.y=m[4][4].y;
            b1 = false;
        }

        int altoIm = fondo.getIntrinsicHeight();
        int anchoIm = fondo.getIntrinsicWidth();
        float medioIm = (float) altoIm / anchoIm;

        fondo.setBounds(0, 0, ancho, alto);


        fondo.draw(c);
        p.setARGB(100, 40, 40, 40);

        c.drawLine(lim1, (alto / 2), lim2, alto / 2, p);

        c.drawLine(lim1, (alto / 2) + sum, lim2, (alto / 2) + sum, p);
        c.drawLine(lim1, (alto / 2) + sum * 2, lim2, (alto / 2) + sum * 2, p);
        c.drawLine(lim1, (alto / 2) + sum * 3, lim2, (alto / 2) + sum * 3, p);
        c.drawLine(lim1, (alto / 2) - sum, lim2, (alto / 2) - sum, p);
        c.drawLine(lim1, (alto / 2) - sum * 2, lim2, (alto / 2) - sum * 2, p);
        c.drawLine(lim1, (alto / 2) - sum * 3, lim2, (alto / 2) - sum * 3, p);

        c.drawLine((ancho / 2), 10, (ancho / 2), alto - 10, p);
        c.drawLine((ancho / 2) + sum, 10, (ancho / 2) + sum, alto - 10, p);
        c.drawLine((ancho / 2) + sum * 2, 10, (ancho / 2) + sum * 2, alto - 10, p);
        c.drawLine((ancho / 2) + sum * 3, 10, (ancho / 2) + sum * 3, alto - 10, p);
        c.drawLine((ancho / 2), 10, (ancho / 2), alto - 10, p);
        c.drawLine((ancho / 2) - sum, 10, (ancho / 2) - sum, alto - 10, p);
        c.drawLine((ancho / 2) - sum * 2, 10, (ancho / 2) - sum * 2, alto - 10, p);
        c.drawLine((ancho / 2) - sum * 3, 10, (ancho / 2) - sum * 3, alto - 10, p);


        p.reset();

        p.setColor(Color.WHITE);
        p.setShadowLayer(2, 1, 1, Color.BLACK);
        p.setTextSize(70);
        c.drawText("PM " + juga1.pm, (float) (lim1 * 0.30), (float) (alto * 0.70), p);
        c.drawText("PA " + juga1.pa, (float) (lim1 * 0.30), (float) (alto * 0.70) + 80, p);
        c.drawText("PV " + juga1.pv, (float) (lim1 * 0.30), (float) (alto * 0.70) + 160, p);

        c.drawBitmap(pm, (float) (lim1 * 0.30) - 95, (float) (alto * 0.70) - 65, p);
        c.drawBitmap(pa, (float) (lim1 * 0.30) - 95, (float) (alto * 0.70) - 65 + 80, p);
        c.drawBitmap(pv, (float) (lim1 * 0.30) - 95, (float) (alto * 0.70) - 65 + 160, p);

        c.drawText("PM " + juga2.pm, (float) (lim2 * 1.08), (float) (alto * 0.10), p);
        c.drawText("PA " + juga2.pa, (float) (lim2 * 1.08), (float) (alto * 0.10) + 80, p);
        c.drawText("PV " + juga2.pv, (float) (lim2 * 1.08), (float) (alto * 0.10) + 160, p);
        c.drawBitmap(pm, (float) (lim2 * 1.08) - 95, (float) (alto * 0.10) - 65, p);
        c.drawBitmap(pa, (float) (lim2 * 1.08) - 95, (float) (alto * 0.10) - 65 + 80, p);
        c.drawBitmap(pv, (float) (lim2 * 1.08) - 95, (float) (alto * 0.10) - 65 + 160, p);

        c.drawBitmap(juga1.burbu,0,0,p);


        c.drawBitmap(cajon,ancho - juga2.burbu.getWidth(),alto - juga2.burbu.getHeight()-170,p);
        c.drawBitmap(cajon, 0, juga1.burbu.getHeight(), p);
        c.drawBitmap(juga1.h1.ima1,10,juga1.burbu.getHeight()+5,p);
        c.drawBitmap(juga1.h2.ima2,120,juga1.burbu.getHeight()+5,p);
        c.drawBitmap(juga1.h3.ima3,230,juga1.burbu.getHeight()+5,p);


        c.drawBitmap(juga2.h1.ima1,ancho - juga2.burbu.getWidth()+10,alto - juga2.burbu.getHeight()-155,p);
        c.drawBitmap(juga2.h2.ima2,ancho - juga2.burbu.getWidth()+120,alto - juga2.burbu.getHeight()-155,p);
        c.drawBitmap(juga2.h3.ima3,ancho - juga2.burbu.getWidth()+230,alto - juga2.burbu.getHeight()-155,p);

        c.drawBitmap(juga2.burbu,ancho-juga2.burbu.getWidth(),alto-juga2.burbu.getHeight()-60,p);



        c.drawBitmap(juga1.imagen, juga1.x-50, juga1.y-144, p);   //-50 x -144 y PARA IMAGEN*******IMPORTANTE
        c.drawBitmap(juga2.imagen, juga2.x-50, juga2.y-144, p);   //-50 x -144 y PARA IMAGEN*******IMPORTANTE


    }

    protected void miThread(){
        t = new Thread(){
            public void run(){
                try{
                    while(ref){
                        if(juga1.turno==0)
                            handle.post(proceso);
                        else
                            handle.post(proceso2);
                        Thread.sleep(5000);
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    final Runnable proceso = new Runnable(){
        public void run(){
            ConsultarServ();
        }
    };

    final Runnable proceso2 = new Runnable(){
        public void run(){
            if(juga2.turno == 1)
                ConsultarServ();
        }
    };

    private void enviarServ(){

        try {
            ConexionPartida web = new ConexionPartida(MySurfaceView.this);
            if(sesion == 1 ) {
                web.agregarVariables("JUGADOR",sesion+"");
                web.agregarVariables("HABILIDAD", habilidad+"");
                web.agregarVariables("X", casilla1+"");
                web.agregarVariables("Y", casilla2+"");

                web.agregarVariables("TURNO",juga1.turno+"");
            }else{
                web.agregarVariables("JUGADOR", sesion + "");
                web.agregarVariables("HABILIDAD", habilidad+"");
                web.agregarVariables("X", casilla1+"");
                web.agregarVariables("Y", casilla2+"");
                web.agregarVariables("TURNO",juga2.turno+"");
            }

            //change
            web.execute(new URL("http://kaiba.esy.es/insertauxiliar.php"));

        } catch (MalformedURLException e) {

        }

    }

    public void movimiento(final int[] sig, final int op, final Jugador j){
        Log.e("Msj1", "Movimiento1");
        bandera = true;
        mov=new CountDownTimer(100,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(op == 1) {

                    if(m[j.pos[0]][j.pos[1]].x>j.x) {
                        j.x = j.x + 5;
                    }
                    else {

                        Log.e("Msj3", "If final");
                        j.x = m[sig[0]][j.pos[1]].x;
                        bandera = false;
                    }
                }else if (op == 2) {

                    if(m[j.pos[0]][j.pos[1]].x<j.x) {
                        j.x = j.x - 5;
                    }
                    else {

                        Log.e("Msj3", "If final");
                        j.x = m[sig[0]][j.pos[1]].x;
                        bandera = false;
                    }
                }else if (op == 3) {

                    if(m[j.pos[0]][j.pos[1]].y-1>j.y) {
                        j.y = j.y + 5;
                    }
                    else {

                        Log.e("Msj3", "If final");
                        j.y = m[j.pos[0]][sig[1]].y;
                        bandera = false;
                    }
                }else if (op == 4) {

                    if(m[j.pos[0]][j.pos[1]].y<j.y) {
                        j.y = j.y - 5;
                    }
                    else {

                        Log.e("Msj3", "If final");
                        j.y = m[j.pos[0]][sig[1]].y;
                        bandera = false;
                    }
                }


            }
            @Override
            public void onFinish() {
                if(bandera) {
                    mov.start();
                }
                if(j.pos[0]!=cSig[0] || j.pos[1]!=cSig[1])
                    emparejaCasilla(sig,j);

                if(j.pos[0]==cSig[0] && j.pos[1] ==cSig[1])
                    emparejaCasilla(sig,j);

            }
        };
        mov.start();
    }

    public void emparejaCasilla(int[] sig,Jugador j){
        int op;
        cActual = j.pos;
        if (j.pos[0] < cSig[0]) {
            j.pos[0] = j.pos[0] + 1;
            j.pm=j.pm-1;
            op = 1;
            j.cambiarImagen(4);
            movimiento(sig, op, j);

            habilidad = 1;
            casilla1 = m[sig[0]][sig[1]].x;
            casilla2 = m[sig[0]][sig[1]].y;

            enviarServ();

        } else if (j.pos[0] > sig[0]) {
            j.pos[0] = j.pos[0] - 1;
            j.pm=j.pm-1;
            op = 2;
            j.cambiarImagen(2);
            movimiento(sig, op, j);

            habilidad = 2;

            habilidad = 1;
            casilla1 = m[sig[0]][sig[1]].x;
            casilla2 = m[sig[0]][sig[1]].y;
            enviarServ();
        }else if (j.pos[1] < sig[1]) {
            j.pos[1] = j.pos[1] + 1;
            j.pm=j.pm-1;
            op = 3;
            j.cambiarImagen(1);
            movimiento(sig, op, j);

            habilidad = 3;

            habilidad = 1;
            casilla1 = m[sig[0]][sig[1]].x;
            casilla2 = m[sig[0]][sig[1]].y;
            enviarServ();
        } else if (j.pos[1] > sig[1]) {
            j.pos[1] = j.pos[1] - 1;
            j.cambiarImagen(3);
            j.pm=j.pm-1;
            op = 4;
            movimiento(sig,op, j);

            habilidad = 4;

            habilidad = 1;
            casilla1 = m[sig[0]][sig[1]].x;
            casilla2 = m[sig[0]][sig[1]].y;
            enviarServ();
        }

    }

    public boolean onTouchEvent(MotionEvent e) {
        // TODO Auto-generated method stub



        if(e.getAction()==MotionEvent.ACTION_UP) {

            if(sesion ==  1 && juga1.turno == 1) {
                cSig[0] = juga1.pos[0];
                cSig[1] = juga1.pos[1];
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (e.getX() > m[i][j].x - (sum * 0.5) && e.getX() < m[i][j].x + (sum * 0.5) && e.getY() > m[i][j].y - (sum * 0.5) && e.getY() < m[i][j].y + (sum * 0.5)) {
                            Log.e("asdm..CASILLA: ", i + " , " + j);
                            cSig[0] = i;
                            cSig[1] = j;
                        }
                    }
                }
                if ( e.getX()<juga1.burbu.getWidth() && e.getY()<juga1.burbu.getHeight()){
                    juga1.turno=0;
                    juga2.turno = 1;

                    juga1.cambiaBurbuja();
                    juga2.cambiaBurbuja();
                    enviarServ();
                }

                if((Math.abs(cSig[0]-juga1.pos[0])+Math.abs(cSig[1]-juga1.pos[1])) <= juga1.pm) {
                    emparejaCasilla(cSig, juga1);

                }
            }else if(sesion == 2 && juga2.turno == 1){
                cSig[0] = juga2.pos[0];
                cSig[1] = juga2.pos[1];

                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (e.getX() > m[i][j].x - (sum * 0.5) && e.getX() < m[i][j].x + (sum * 0.5) && e.getY() > m[i][j].y - (sum * 0.5) && e.getY() < m[i][j].y + (sum * 0.5)) {
                            cSig[0] = i;
                            cSig[1] = j;

                        }
                    }
                }
                if ( e.getX()>ancho-juga2.burbu.getWidth() && e.getY()> alto-juga2.burbu.getHeight()-60 && e.getY()< alto-60){
                    juga1.turno=1;
                    juga2.turno = 0;

                    juga1.cambiaBurbuja();
                    juga2.cambiaBurbuja();
                    enviarServ();
                }


                if((Math.abs(cSig[0]-juga2.pos[0])+Math.abs(cSig[1]-juga2.pos[1])) <= juga2.pm)
                    emparejaCasilla(cSig,juga2);
            }
        }
        return true;

    }
    private void ConsultarServ(){

        try {
            ConexionPartida web = new ConexionPartida(MySurfaceView.this);

            //change
            web.execute(new URL("http://kaiba.esy.es/consultaauxiliar.php"));

        } catch (MalformedURLException e) {
        }

    }

    public void mostrarResultado(String resultado){
        AlertDialog.Builder alerta= new AlertDialog.Builder(this.getContext());

        if(!resultado.equals("")){
            String temp[] = resultado.split(",");
            if(Integer.parseInt(temp[0])==1){
                juga2.x = x2=Integer.parseInt(temp[2]);
                juga2.y = y2=Integer.parseInt(temp[3]);
            }
            habilidad2=Integer.parseInt(temp[1]);
            turno2=Integer.parseInt(temp[4]);
        }
        if(resultado.startsWith("Mensaje")){
            Toast.makeText(this.getContext(), "Mensaje enviado", Toast.LENGTH_LONG).show();
        }else if(resultado.startsWith("-msj")){
            resultado = resultado.substring(resultado.indexOf("-msj-")+5,resultado.indexOf("-/msj-"));



        }  else {
            if (resultado.startsWith("ERROR2")) {
                resultado = "Error al enviar el codigo";
            }

            if (resultado.startsWith("INCORRECTO")) {
                resultado="Código incorrecto, vuelva a intentarlo";
            }

        }
    }


}
