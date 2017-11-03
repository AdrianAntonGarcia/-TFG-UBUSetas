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


}
