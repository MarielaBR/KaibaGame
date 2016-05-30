package ittepic.edu.mx.tpdm_kaiba;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MARIELA on 29/05/2016.
 */
public class ConexionPerfil extends AsyncTask<URL,String,String>{
    List<String[]> variables;
    FragmentoPerfil puntero;

    public ConexionPerfil(FragmentoPerfil p){
        variables= new ArrayList<String[]>();
        puntero=p;
    }


    public void agregarVariables(String identificador,String dato){
        String[] temporal={identificador,dato};
        variables.add(temporal);
    }

    @Override
    protected String doInBackground(URL... urls) {

        HttpURLConnection conexion=null;
        String datosPost="";
        String respuesta="";

        try{
            //creacion de cadena clave=valor&clave=valor%...etc
            for(int i=0;i<variables.size();i++){
                String[] temp= variables.get(i);
                datosPost += temp[0]+"="+ URLEncoder.encode(temp[1], "UTF-8")+" ";
            }

            datosPost= datosPost.trim();
            datosPost=datosPost.replaceAll(" ", "&");

            conexion=(HttpURLConnection)urls[0].openConnection();// conexion

            conexion.setDoOutput(true);
            conexion.setFixedLengthStreamingMode(datosPost.length());

            conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            //Flujos
            OutputStream flujoEnvioDatos= new BufferedOutputStream(conexion.getOutputStream());

            flujoEnvioDatos.write(datosPost.getBytes());
            flujoEnvioDatos.flush();//forzador de envio  de datos
            flujoEnvioDatos.close();

            if(conexion.getResponseCode()==200){// el 200 significa que si recibio el host
                //los datos enviados y proceso respuesta

                InputStreamReader flujoLectura= new InputStreamReader(conexion.getInputStream(),"UTF-8");
                BufferedReader lector= new BufferedReader(flujoLectura);

                String linea="";
                do{
                    linea=lector.readLine();
                    if (linea!=null){
                        respuesta+=linea;
                    }
                }while(linea!=null);

            }else{
                respuesta="Error_404";
            }
        }catch (UnknownHostException e){
            return "Error_404_1";
        }catch (IOException e){
            return  "Error_404_2";
        }finally {
            if(conexion!=null){
                conexion.disconnect();
            }
        }
        return respuesta;
    }

    protected void onPostExecute(String res){
        super.onPostExecute(res);
        puntero.mostrarResultado(res);
    }
}
