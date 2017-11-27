package ubusetas.ubu.adrian.proyectoubusetas.basedatos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.TreeMap;



/*
* @name: AccesoDatosExternos
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que iplementa las funciones necesarias para acceder a los datos externor a la aplicación que se encuentran en la carpeta assets
* */

public class AccesoDatosExternos {


    //Nombre del fichero donde se encuentran las claves dentro de assets/claves/
    private static final String NOMBREFICHERO = "claves.dat";
    private Context contexto;

    /*
    * @name: AccesoDatosExternos
    * @Author: Adrián Antón García
    * @category: Constructor
    * @Description: Constructor que inicializa la clase
    * para acceder las imágenes y claves dicotómicas
    * @param: Context, contexto de la aplicación donde se va a acceder.
    * */

    public AccesoDatosExternos(Context contexto) {
        this.contexto = contexto;
    }

        /*
    * @name: accesoImagenPorPath
    * @Author: Adrián Antón García
    * @category: Método
    * @Description: Metodo que devuelve el Bitmap del path pasado por parametro
    * @param: String, path donde se encuentra la imagen a devovler.
    * @return: Bitmap, bitmap de la imagén que se encontraba en el path introducido.
    * */

    public Bitmap accesoImagenPorPath(String path) {
        InputStream is = null;
        try {
            is = contexto.getResources().getAssets().open(path);
        } catch (IOException e) {
            Log.e("Error al cargar la foto", "Error al cargar la foto desde el path" + path);
            e.printStackTrace();
        }
        Bitmap bit = BitmapFactory.decodeStream(is);
        return bit;
    }

    /*
    * @name: readFromFile
    * @Author: Adrián Antón García
    * @category: Método
    * @Description: Método que lee la estructura de las claves del archivo claves.dat
    * */

    public TreeMap<String, ArrayList<Object>> readFromFile() {
        TreeMap<String, ArrayList<Object>> createResumeForm = null;
        try {
            InputStream fileInputStream = contexto.getAssets().open("claves/" + NOMBREFICHERO);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            createResumeForm = (TreeMap<String, ArrayList<Object>>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            Log.e("Error claves", "Error al intentar leer las claves del fichero: " + NOMBREFICHERO);
            e.printStackTrace();
        }
        return createResumeForm;
    }
}
