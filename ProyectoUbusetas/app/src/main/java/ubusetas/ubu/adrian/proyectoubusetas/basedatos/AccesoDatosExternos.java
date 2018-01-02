package ubusetas.ubu.adrian.proyectoubusetas.basedatos;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;


/**
 * Clase que implementa las funciones necesarias para acceder a los datos externos a la aplicación que se encuentran en la carpeta assets
 *
 * @author Adrián Antón García
 * @name AccesoDatosExternos
 * @category clase
 */

public class AccesoDatosExternos {


    //Nombre del fichero donde se encuentran las claves dentro de assets/claves/
    private static final String NOMBREFICHERO = "claves.dat";
    private static final String NOMBREFICHEROINGLES = "clavesEn.dat";
    private Context contexto;

    /**
     * Constructor que inicializa la clase
     * para acceder las imágenes y claves dicotómicas
     *
     * @param Context, contexto de la aplicación donde se va a acceder.
     * @name AccesoDatosExternos
     * @author Adrián Antón García
     * @category Constructor
     */

    public AccesoDatosExternos(Context contexto) {
        this.contexto = contexto;
    }

    /**
     * Método que devuelve el Bitmap del path pasado por parametro
     *
     * @param String, path donde se encuentra la imagen a devovler.
     * @return Bitmap, bitmap de la imagén que se encontraba en el path introducido.
     * @name accesoImagenPorPath
     * @author Adrián Antón García
     * @category Método
     */

    public Bitmap accesoImagenPorPath(String path) {
        InputStream is = null;
        Log.d("PATH", path);
        try {
            is = contexto.getResources().getAssets().open(path);
        } catch (IOException e) {
            Log.e("Error al cargar la foto", "Error al cargar la foto desde el path" + path);
            e.printStackTrace();
        }
        Bitmap bit = BitmapFactory.decodeStream(is);
        return bit;
    }

    /**
     * Método que lee la estructura de las claves del archivo claves.dat
     *
     * @name readFromFile
     * @author Adrián Antón García
     * @category Método
     */

    public TreeMap<String, ArrayList<Object>> readFromFile() {
        TreeMap<String, ArrayList<Object>> createResumeForm = null;
        try {
            InputStream fileInputStream = contexto.getAssets().open("claves/" + NOMBREFICHERO);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            createResumeForm = (TreeMap<String, ArrayList<Object>>) objectInputStream.readObject();
            Log.d("CLAVES", createResumeForm.keySet().toString());
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            Log.e("Error claves", "Error al intentar leer las claves del fichero: " + NOMBREFICHERO);
            e.printStackTrace();
        }
        return createResumeForm;
    }

    /**
     * Método que lee la estructura de las claves del archivo claves.dat
     *
     * @name readFromFileEn
     * @author Adrián Antón García
     * @category Método
     */

    public TreeMap<String, ArrayList<Object>> readFromFileEn() {
        TreeMap<String, ArrayList<Object>> createResumeForm = null;
        try {
            InputStream fileInputStream = contexto.getAssets().open("claves/" + NOMBREFICHEROINGLES);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            createResumeForm = (TreeMap<String, ArrayList<Object>>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            Log.e("Error claves", "Error al intentar leer las claves del fichero: " + NOMBREFICHEROINGLES);
            e.printStackTrace();
        }
        return createResumeForm;
    }

    /**
     * Procedimiento que cambia el idioma de la aplicación.
     *
     * @name actualizarIdioma
     * @author Adrián Antón García
     * @category procedimiento
     */

    public void actualizarIdioma(String idioma) {
        Locale localizacion = new Locale(idioma);
        Locale.setDefault(localizacion);

        Resources resources = contexto.getResources();
        Configuration configuracion = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuracion.setLocale(localizacion);
        } else {
            configuracion.locale = localizacion;
        }
        resources.updateConfiguration(configuracion, resources.getDisplayMetrics());
    }
}
