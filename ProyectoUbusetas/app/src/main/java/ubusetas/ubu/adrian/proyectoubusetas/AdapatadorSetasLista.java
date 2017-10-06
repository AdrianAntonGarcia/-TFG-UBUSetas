package ubusetas.ubu.adrian.proyectoubusetas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

    /*
    * @name: AdapatadorSetasLista
    * @Author: Adrián Antón García
    * @category: class
    * @Description: Clase que sirve de adaptador al sistema para cargar en cada elemento de la lista una foto
    * y el nombre de la especie de la seta.
    * */

public class AdapatadorSetasLista extends ArrayAdapter<SetasLista> {
    Context context;
    int resource;
    List<SetasLista> lista = null;

    /*
    * @name: AdapatadorSetasLista
    * @Author: Adrián Antón García
    * @category: constructor
    * @Description: Constructor que inicializa el adaptador con el contexto de la actividad, el id asignado al listView,
    * y la lista de datos que se quieren cargar.
    * @param Context , contexto de la actividad
    * @param int , id del listView de la lista de elementos
    * @param List<SetasLista> , conjunto de datos que queremos cargar
    * */

    public AdapatadorSetasLista(Context context, int resource, List<SetasLista> lista) {
        super(context, resource, lista);
        this.context = context;
        this.resource = resource;
        this.lista = lista;
    }

    /*
    * @name: getView
    * @Author: Adrián Antón García
    * @category: constructor
    * @Description: Metodo que devuelve la view de un determinado elemento de la lista cargando
    * en ese elemento el contenido necesario(imagen + texto)
    * @param int , posicion del elemento a devolver
    * @param View , view que vamos a "inflar"
    * @param ViewGroup , vista padre
    * */

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SetasListaHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new SetasListaHolder();
            //enlazamos con los elementos de la lista, imagen+texto
            holder.imagen = (ImageView) row.findViewById(R.id.imageView_imagen_lista);
            holder.texto = (TextView) row.findViewById(R.id.textView_texto_lista);
            //asociamos el holder a la fila
            row.setTag(holder);
        } else {
            holder = (SetasListaHolder) row.getTag();
        }

        SetasLista setas = lista.get(position);
        //asociamos el texto e imagen externos al contendor interno de la lista en esa posicio
        holder.texto.setText(setas.nombre);
        //asociamos la imagen
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open(setas.path);
        } catch (IOException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
        Bitmap bit = BitmapFactory.decodeStream(is);
        holder.imagen.setImageBitmap(bit);
        return row;
    }

    /*
    * @name: SetasListaHolder
    * @Author: Adrián Antón García
    * @category: class
    * @Description: Clase que contiene cada par imagenView-TextView de cada elemento de la lista
    * */

    static class SetasListaHolder {
        ImageView imagen;
        TextView texto;
    }
}
