package ubusetas.ubu.adrian.proyectoubusetas.basedatos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by adrit on 22/11/2017.
 */

public class AccesoDatosExternos {

    public AccesoDatosExternos(){

    }

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
}
