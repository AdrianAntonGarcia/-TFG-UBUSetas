package ubusetas.ubu.adrian.proyectoubusetas.basedatos;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/*
* @name: DBsetas
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que carga la base de datos SQLite encontrada en assets/databases
* */

public class DBsetas extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "prueba2.db";
    private static final int DATABASE_VERSION = 1;

    public DBsetas(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
