package ittepic.edu.mx.tpdm_kaiba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Liz on 29/05/2016.
 */
public class ConexionBD extends SQLiteOpenHelper {

    public ConexionBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//Hasta tres bd por app
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USUARIO(USUARIO VARCHAR(20) PRIMARY KEY, CONTRASENA VARCHAR(20),STATUS CHAR(1))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
