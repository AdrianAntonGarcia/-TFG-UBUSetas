package ubusetas.ubu.adrian.proyectoubusetas.basedatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

/**
 * Clase que implementa los métodos para acceder y administrar la base de datos
 *
 * @author Adrián Antón García
 * @name DBsetasManager
 * @category clase
 */

public class DBsetasManager {

    //Clase para recuperar la base de datos

    public DBsetas baseDatos = null;

    //Gestor de base de datos SQlite

    SQLiteDatabase bd = null;

    /**
     * Constructor que inicializa la clase que desde la que se accede a la base de datos
     *
     * @param Context, contexto de la aplicación donde se va a acceder a la base de datos.
     * @name DBsetasManager
     * @author Adrián Antón García
     * @category Constructor
     */

    public DBsetasManager(Context contexto) {
        baseDatos = new DBsetas(contexto);
    }

    /**
     * Procedimiento que abre la base de datos en modo lectura y escritura.
     *
     * @name open
     * @author Adrián Antón García
     * @category procedimiento
     */

    public void open() {
        bd = baseDatos.getWritableDatabase();
    }

    /**
     * Procedimiento que cierra la base de datos
     *
     * @name close
     * @author Adrián Antón García
     * @category procedimiento
     */

    public void close() {
        this.baseDatos.close();
    }

    /**
     * Método que devuelve la base de datos
     *
     * @return SQLiteDatabase, devolvemos el gestor de base de datos
     * @name getBaseDatos
     * @author Adrián Antón García
     * @category método
     */

    public SQLiteDatabase getBaseDatos() {
        return bd;
    }

    /**
     * Método que devuelve la descripcion en español asociada al nombre introducido de la seta
     *
     * @param String, nombre de la seta a consultar
     * @return String, Descripción en español de la seta introducida.
     * @name getDescripcionEsp
     * @author Adrián Antón García
     * @category método
     */

    public String getDescripcionEsp(String nombreSeta) {
        nombreSeta = nombreSeta.trim();
        String descEsp = null;
        String[] args = new String[]{nombreSeta};
        Cursor c = bd.rawQuery("SELECT DescripcionEs FROM TablaDescripciones WHERE Nombre=?", args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                descEsp = c.getString(0);
            } while (c.moveToNext());
        } else {
            descEsp = nombreSeta + nombreSeta.length();
        }
        return descEsp;
    }

    /**
     * Método que devuelve la descripcion  en inglés asociada al nombre introducido de la seta
     *
     * @param String, nombre de la seta a consultar
     * @return String, Descripción en inglés de la seta introducida.
     * @name getDescripcionEn
     * @author Adrián Antón García
     * @category método
     */

    public String getDescripcionEn(String nombreSeta) {
        nombreSeta = nombreSeta.trim();
        String desEn = null;
        String[] args = new String[]{nombreSeta};
        Cursor c = bd.rawQuery("SELECT DescripcionEn FROM TablaDescripciones WHERE Nombre=?", args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                desEn = c.getString(0);
            } while (c.moveToNext());
        } else {
            desEn = nombreSeta + nombreSeta.length();
        }
        return desEn;
    }

    /**
     * Método que devuelve el genero de la seta
     *
     * @param String, nombre de la seta a consultar
     * @return String, Género de la seta introducida.
     * @name getGenero
     * @author Adrián Antón García
     * @category método
     */

    public String getGenero(String nombreSeta) {
        nombreSeta = nombreSeta.trim();
        String genero = null;
        String[] args = new String[]{nombreSeta};
        Cursor c = bd.rawQuery("SELECT Especie FROM TablaGeneros WHERE Nombre=?", args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                genero = c.getString(0);
            } while (c.moveToNext());
        } else {
            genero = nombreSeta + nombreSeta.length();
        }
        return genero;
    }

    /**
     * Método que devuelve la comestibilidad de la seta en español
     *
     * @param String, nombre de la seta a consultar
     * @return String, Comestibilidad en Español de la seta introducida.
     * @name getComestibilidadEs
     * @author Adrián Antón García
     * @category método
     */

    public String getComestibilidadEs(String nombreSeta) {
        nombreSeta = nombreSeta.trim();
        String comestible = "";
        String[] args = new String[]{nombreSeta};
        Cursor c = bd.rawQuery("SELECT ComestibleEs FROM TablaComestible WHERE Nombre=?", args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                comestible = comestible + c.getString(0) + "-";
            } while (c.moveToNext());
        } else {
            comestible = nombreSeta + nombreSeta.length();
        }
        return comestible;
    }

    /**
     * Método que devuelve la comestibilidad de la seta en ingles
     *
     * @param String, nombre de la seta a consultar
     * @return String, Comestibilidad en Inglés de la seta introducida.
     * @name getComestibilidadEn
     * @author Adrián Antón García
     * @category method
     */

    public String getComestibilidadEn(String nombreSeta) {
        nombreSeta = nombreSeta.trim();
        String comestible = "";
        String[] args = new String[]{nombreSeta};
        Cursor c = bd.rawQuery("SELECT ComestibleEn FROM TablaComestible WHERE Nombre=?", args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                comestible = comestible + c.getString(0) + "-";
            } while (c.moveToNext());
        } else {
            comestible = nombreSeta + nombreSeta.length();
        }
        return comestible;
    }

    /**
     * Método que devuelve el enlace de la seta
     *
     * @param String, nombre de la seta a consultar
     * @return String, Enlace a la wikipedia de la seta consultada.
     * @name getEnlace
     * @author Adrián Antón García
     * @category method
     */

    public String getEnlace(String nombreSeta) {
        nombreSeta = nombreSeta.trim();
        String enlace = "";
        String[] args = new String[]{nombreSeta};
        Cursor c = bd.rawQuery("SELECT Enlace FROM TablaEnlaces WHERE Nombre=?", args);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                enlace = c.getString(0);
            } while (c.moveToNext());
        } else {
            enlace = nombreSeta + nombreSeta.length();
        }
        return enlace;
    }
}
