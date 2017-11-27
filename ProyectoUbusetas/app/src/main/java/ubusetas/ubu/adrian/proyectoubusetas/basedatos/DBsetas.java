package ubusetas.ubu.adrian.proyectoubusetas.basedatos;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/*
* @name: DBsetas
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que carga la base de datos SQLite encontrada en assets/databases
* */

public class DBsetas extends SQLiteAssetHelper {

    //Nombre de la base de datos
    private static final String DATABASE_NAME = "prueba2.db";
    //Versión de la base de datos
    private static final int DATABASE_VERSION = 1;

    /*
    * @name: DBsetas
    * @Author: Adrián Antón García
    * @category: Constructor
    * @Description: Constructor que inicializa la base de datos
    * de SQlite
    * @param: Context, contexto de la aplicación donde se va a acceder a la base de datos.
    * */

    public DBsetas(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
