package ubusetas.ubu.adrian.proyectoubusetas.basedatos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.TreeMap;



/*
* @name: AccesoDatosExternos
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que iplementa las funciones necesarias para acceder a los datos externor a la aplicación que se encuentran en la carpeta assets
* */
public class AccesoDatosExternos {


    //Nombre del fichero donde se encuentran las claves dentro de assets/claves/
    private static final String NOMBREFICHERO = "claves.dat";


    private Context contexto;

    public AccesoDatosExternos(Context contexto){
        this.contexto=contexto;
    }

        /*
    * @name: accesoImagenPorPath
    * @Author: Adrián Antón García
    * @category: Metodo
    * @Description: Metodo que devuelve el Bitmap del path pasado por parametro
    * */

    public Bitmap accesoImagenPorPath(Context context, String path){
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open(path);
        } catch (IOException e) {
            Log.d("Error al cargar la foto","Error al cargar la foto desde el path"+path);
        }
        Bitmap bit = BitmapFactory.decodeStream(is);
        return bit;
    }

    /*
    * @name: readFromFile
    * @Author: Adrián Antón García
    * @category: Metodo
    * @Description: Metodo que lee la estructura de las claves del archivo claves.dat
    * */

    public TreeMap<String, ArrayList<Object>> readFromFile() {
        TreeMap<String, ArrayList<Object>> createResumeForm = null;
        try {
            InputStream fileInputStream = contexto.getAssets().open("claves/" + NOMBREFICHERO);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            createResumeForm = (TreeMap<String, ArrayList<Object>>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return createResumeForm;
    }



}
