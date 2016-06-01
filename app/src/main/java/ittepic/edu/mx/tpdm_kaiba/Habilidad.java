package ittepic.edu.mx.tpdm_kaiba;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by cesar_pruefkd on 01/06/2016.
 */
public class Habilidad {
    int alcance,danoi,danoo,movi,movo,vidi,atai,ata2,jugador,typ;
    Bitmap ima1;
    Bitmap ima2;
    Bitmap ima3;
    MySurfaceView lienzo;
    public Habilidad(int j,int a1, int d1, int d2, int m1, int m2, int v1,int pa1, int pa2,MySurfaceView li,int tipo){
        jugador = j;
        alcance = a1;
        danoo = d1;
        danoi = d2;
        movi = m1;
        movo = m2;
        vidi = v1;
        atai = pa1;
        ata2 = pa2;
        lienzo = li;
        typ = tipo;

        switch (j){
            case 1:
                if(typ == 1)
                    ima1 = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.h11);
                else if(typ == 2)
                    ima2 = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.h12);
                else if(typ==3)
                    ima3 = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.h13);
                break;
            case 2:
                if(typ == 1)
                    ima1 = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.h21);
                else if(typ == 2)
                    ima2 = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.h22);
                else if(typ==3)
                    ima3 = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.h23);
                break;
            case 3:
                if(typ == 1)
                    ima1 = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.h31);
                else if(typ == 2)
                    ima2 = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.h32);
                else if(typ==3)
                    ima3 = BitmapFactory.decodeResource(lienzo.getResources(), R.drawable.h33);
                break;
        }
    }
}
