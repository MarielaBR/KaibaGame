package ittepic.edu.mx.tpdm_kaiba;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.BitmapCompat;

/**
 * Created by cesar_pruefkd on 31/05/2016.
 */
public class Jugador {
    int tipo,pv,pm,pa, pos[],x,y;
    Habilidad h1,h2,h3;
    Bitmap imagen,burbu;
    String usuario;
    MySurfaceView lienzo;
    int turno;

    public Jugador(int tipoj,String usu, MySurfaceView m,int t ){
        tipo =  tipoj;
        pos = new int[2];
        usuario = usu;
        lienzo = m;
        turno = t;
        switch (tipo){
            case 1: pv = 40;
                pa= 6;
                pm = 3;
                h1 = new Habilidad(1,0,0,12,0,0,0,-3,0,lienzo,1);
                h2 = new Habilidad(1,0,2,0,0,0,2,-3,2,lienzo,2);
                h3 = new Habilidad(1,0,0,20,0,0,0,-4,2,lienzo,3);
                imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.pette1);
                if(turno == 1)
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.b11);
                else
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.i11);
                break;
            case 2: pv = 30;

                h1 = new Habilidad(2,0,0,7,0,0,0,-3,0,lienzo,1);
                h2 = new Habilidad(2,2,-3,0,0,0,0,-3,0,lienzo,2);
                h3 = new Habilidad(2,0,0,12,0,0,0,-4,0,lienzo,3);
                pa = 6;
                pm = 2;
                imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.lucy1);
                if(turno == 1)
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.b22);
                else
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.i22);
                break;
            case 3:pv = 35;
                h1 = new Habilidad(3,0,0,7,0,0,0,-3,0,lienzo,1);
                h2 = new Habilidad(3,2,-3,0,0,0,0,-3,0,lienzo,2);
                h3 = new Habilidad(3,0,0,12,0,0,0,-4,0,lienzo,3);
                pa = 6;
                pm = 3;
                if(turno == 1)
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.b33);
                else
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.i33);
                break;
        }
    }

    public void cambiaBurbuja(){
        switch(tipo){
            case 1:
                if(turno == 1)
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.b11);
                else
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.i11);
                break;
            case 2:
                if(turno ==1)
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.b22);
                else
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.i22);
                break;
            case 3:
                if(turno == 1)
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.b33);
                else
                    burbu = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.i33);
                break;
        }
    }

    public void cambiarImagen(int dir){
        switch (dir){
            case 1 :
                if(tipo == 1)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.pette1);
                if(tipo == 2)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.lucy1);
                if(tipo == 3)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.midna1);
                break;
            case 2:
                if(tipo == 1)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.pette2);
                if(tipo == 2)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.lucy2);
                if(tipo == 3)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.midna2);
                break;
            case 3:
                if(tipo == 1)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.pette3);
                if(tipo == 2)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.lucy3);
                if(tipo == 3)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.midna3);
                break;
            case 4:
                if(tipo == 1)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.pette4);
                if(tipo == 2)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.lucy4);
                if(tipo == 3)
                    imagen = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.midna4);
                break;
        }
    }

}
