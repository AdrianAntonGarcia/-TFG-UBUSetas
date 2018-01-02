package ubusetas.ubu.adrian.proyectoubusetas.resultados;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;

/**
 * Clase que sirve de adaptador al sistema para cargar en cada elemento de la lista una foto
 * y el nombre de la especie de la seta.
 *
 * @author Adrián Antón García
 * @name AdapatadorSetasLista
 * @category class
 */

public class AdapatadorSetasLista extends ArrayAdapter<SetasLista> {
    Context context;
    int resource;
    List<SetasLista> lista = null;

    /**
     * Constructor que inicializa el adaptador con el contexto de la actividad, el id asignado al listView,
     * y la lista de datos que se quieren cargar.
     *
     * @param Context          , contexto de la actividad
     * @param int              , id del listView de la lista de elementos
     * @param List<SetasLista> , conjunto de datos que queremos cargar
     * @name AdapatadorSetasLista
     * @author Adrián Antón García
     * @category constructor
     */

    public AdapatadorSetasLista(Context context, int resource, List<SetasLista> lista) {
        super(context, resource, lista);
        this.context = context;
        this.resource = resource;
        this.lista = lista;
    }

    /**
     * Método que devuelve la view de un determinado elemento de la lista cargando
     * en ese elemento el contenido necesario(imagen + texto)
     *
     * @param int       , posicion del elemento a devolver
     * @param View      , view que vamos a "inflar"
     * @param ViewGroup , vista padre
     * @name getView
     * @author Adrián Antón García
     * @category constructor
     */

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
        AccesoDatosExternos acceso = new AccesoDatosExternos(context);
        Bitmap bit = acceso.accesoImagenPorPath(setas.path);
        holder.imagen.setImageBitmap(bit);
        return row;
    }

    /**
     * Clase que contiene cada par imagenView-TextView de cada elemento de la lista
     *
     * @author Adrián Antón García
     * @name SetasListaHolder
     * @category class
     */

    static class SetasListaHolder {
        ImageView imagen;
        TextView texto;

        /**
         * Método que devuelve el texto del eleento de la lista
         *
         * @return String, el texto del elemento
         * @author Adrián Antón García
         * @category Método
         */

        public String getTexto() {
            return texto.getText().toString();
        }
    }
}
