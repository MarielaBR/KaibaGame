package ittepic.edu.mx.tpdm_kaiba;

/**
 * Created by Liz on 29/05/2016.
 */
public class ListaMenu {


    private String nombre;
    private int drawableImageID;

    public ListaMenu(String nombre, int drawableImageID) {
        this.nombre = nombre;
        this.drawableImageID = drawableImageID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDrawableImageID() {
        return drawableImageID;
    }

    public void setDrawableImageID(int drawableImageID) {
        this.drawableImageID = drawableImageID;
    }


}
