package ubusetas.ubu.adrian.proyectoubusetas.basedatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

/*
* @name: DBsetasManager
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que iplementa los metodos para acceder y administrar la base de datos
* */

public class DBsetasManager {
    //Clase para recuperar la base de datos
    public DBsetas baseDatos=null;
    //base de datos SQlite
    SQLiteDatabase bd=null;
    Context contexto;
    /*
     * @name: DBsetasManager
     * @Author: Adrián Antón García
     * @category: constructor
     * @Description: Constructor que inicializa el gestor de base de datos
     * */

    public DBsetasManager(Context contexto){
        baseDatos = new DBsetas(contexto);

        contexto=contexto;
    }

    public void open(){
        bd = baseDatos.getWritableDatabase();
    }

    public void close(){
        this.baseDatos.close();
    }
    /*
    * @name: getBaseDatos
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Metodo que devuelve la base de datos
    * */

    public SQLiteDatabase getBaseDatos(){
        return bd;
    }

    /*
    * @name: getDescripcionEsp
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Metodo que devuelve la descripcion en español asociada al nombre introducido de la seta
    * @String: nombre de la seta a consultar
    * */

    public String getDescripcionEsp(String nombreSeta){
        nombreSeta = nombreSeta.trim();
        String descEsp=null;
        String[] args = new String[] {nombreSeta};
        Cursor c = bd.rawQuery("SELECT DescripcionEs FROM TablaDescripciones WHERE Nombre=?",args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                descEsp = c.getString(0);
            } while(c.moveToNext());
        }else{
            descEsp=nombreSeta+nombreSeta.length();
        }
        return descEsp;
    }

    /*
    * @name: getDescripcionEn
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Metodo que devuelve la descripcion  en inglés asociada al nombre introducido de la seta
    * @String: nombre de la seta a consultar
    * */

    public String getDescripcionEn(String nombreSeta){
        nombreSeta = nombreSeta.trim();
        String desEn=null;
        String[] args = new String[] {nombreSeta};
        Cursor c = bd.rawQuery("SELECT DescripcionEn FROM TablaDescripciones WHERE Nombre=?",args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                desEn = c.getString(0);
            } while(c.moveToNext());
        }else{
            desEn=nombreSeta+nombreSeta.length();
        }
        return desEn;
    }

        /*
    * @name: getGenero
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Metodo que devuelve el genero de la seta
    * @String: nombre de la seta a consultar
    * */

    public String getGenero(String nombreSeta){
        nombreSeta = nombreSeta.trim();
        String genero=null;
        String[] args = new String[] {nombreSeta};
        Cursor c = bd.rawQuery("SELECT Especie FROM TablaGeneros WHERE Nombre=?",args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                genero = c.getString(0);
            } while(c.moveToNext());
        }else{
            genero=nombreSeta+nombreSeta.length();
        }
        return genero;
    }

            /*
    * @name: getComestibilidadEs
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Metodo que devuelve la comestibilidad de la seta en español
    * @String: nombre de la seta a consultar
    * */

    public String getComestibilidadEs(String nombreSeta){
        nombreSeta = nombreSeta.trim();
        String comestible="";
        String[] args = new String[] {nombreSeta};
        Cursor c = bd.rawQuery("SELECT ComestibleEs FROM TablaComestible WHERE Nombre=?",args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                comestible = comestible+c.getString(0)+"-";
            } while(c.moveToNext());
        }else{
            comestible=nombreSeta+nombreSeta.length();
        }
        return comestible;
    }

                /*
    * @name: getComestibilidadEn
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Metodo que devuelve la comestibilidad de la seta en ingles
    * @String: nombre de la seta a consultar
    * */

    public String getComestibilidadEn(String nombreSeta){
        nombreSeta = nombreSeta.trim();
        String comestible="";
        String[] args = new String[] {nombreSeta};
        Cursor c = bd.rawQuery("SELECT ComestibleEn FROM TablaComestible WHERE Nombre=?",args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                comestible = comestible+c.getString(0)+"-";
            } while(c.moveToNext());
        }else{
            comestible=nombreSeta+nombreSeta.length();
        }
        return comestible;
    }

                    /*
    * @name: getEnlace
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Metodo que devuelve el enlace de la seta
    * @String: nombre de la seta a consultar
    * */

    public String getEnlace(String nombreSeta){
        nombreSeta = nombreSeta.trim();
        String enlace="";
        String[] args = new String[] {nombreSeta};
        Cursor c = bd.rawQuery("SELECT Enlace FROM TablaEnlaces WHERE Nombre=?",args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                enlace = c.getString(0);
            } while(c.moveToNext());
        }else{
            enlace=nombreSeta+nombreSeta.length();
        }
        return enlace;
    }
}
