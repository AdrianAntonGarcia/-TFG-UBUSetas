package ubusetas.ubu.adrian.proyectoubusetas.elegirclaves;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ubusetas.ubu.adrian.proyectoubusetas.R;

/**
 * Clase que implementa el adaptador para cargar los elementos(ItemSelector)
 * de la lista de géneros a seleccionar.
 *
 * @author Adrián Antón García
 * @name AdaptadorSelector
 * @category clase
 */

public class AdaptadorSelector extends RecyclerView.Adapter<AdaptadorSelector.ViewHolderSelector> {

    //Lista de elementos
    private List<ItemSelector> datos;
    //Contexto de la aplicación
    private AppCompatActivity context;
    //Array que almacena un booleano que indica si el elemento de la lista esta pulsado o no
    private SparseBooleanArray seleccionados;

    /**
     * Constructor que inicializa el adaptador de la lista de generos
     *
     * @param AppCompatActivity,        contexto de la actividad donde se va a cargar el adaptador
     * @param LinkedList<ItemSelector>, Lista que contiene los elementos
     * @name AdaptadorSelector
     * @author Adrián Antón García
     * @category constructor
     */

    public AdaptadorSelector(AppCompatActivity context, LinkedList<ItemSelector> datos) {
        //modoSeleccion=false;
        this.context = context;
        this.datos = datos;
        seleccionados = new SparseBooleanArray();
    }

    /**
     * Método que se llama para cargar un elemento en la lista.
     * Estos métodos son llamados por el sistema.
     *
     * @param ViewGroup, Grupo de vistas de los elementos a cargar.
     * @param int,       elemento seleccionado a cargar.
     * @return ViewHolderSelector, contenendor de elementos de la lista.
     * @name onCreateViewHolder
     * @author Adrián Antón García
     * @category método
     */

    @Override
    public ViewHolderSelector onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_selector, parent, false);
        ViewHolderSelector viewHolder = new ViewHolderSelector(v);
        return viewHolder;
    }

    /**
     * Procedimiento que inicializa un elemento de la lista(selector).
     * Estos métodos son llamados por el sistema.
     *
     * @param ViewHolderSelector, Vista del selector
     * @param int,                elemento del selector a inicializar
     * @name onBindViewHolder
     * @author Adrián Antón García
     * @category procedimiento
     */

    @Override
    public void onBindViewHolder(ViewHolderSelector holder, int position) {
        ItemSelector os = datos.get(position);
        holder.bindView(os);
    }

    /**
     * Método que devuelve cuantos elementos hay cargados en la lista.
     * Estos métodos son llamados por el sistema.
     *
     * @return int, número de elementos.
     * @name getItemCount
     * @author Adrián Antón García
     * @category método
     */

    @Override
    public int getItemCount() {
        if (datos.isEmpty()) {
            return 0;
        } else {
            return datos.size();
        }
    }

    /**
     * Clase que implementa los elementos que se deben cargar en la lista(selector)y los
     * relaciona con los elementos de la interfaz.
     *
     * @author Adrián Antón García
     * @name ViewHolderSelector
     * @category clase
     */

    class ViewHolderSelector extends RecyclerView.ViewHolder {

        private TextView tv_texto;
        private View item;

        /**
         * Constructor que inicializa el contenedor de elementos.
         *
         * @param View, vista de la interfaz con el que se relaciona el contenedor
         * @name ViewHolderSelector
         * @author Adrián Antón García
         * @category Constructor
         */

        public ViewHolderSelector(View itemView) {
            super(itemView);
            this.item = itemView;
        }

        /**
         * Procedimiento que inicializa un elemento de la lista(selector)
         * y lo relaciona con los elementos de la interfaz.
         *
         * @param ItemSelector, Elemento a inicializar
         * @name bindView
         * @author Adrián Antón García
         * @category procedimiento
         */

        public void bindView(ItemSelector os) {

            tv_texto = (TextView) item.findViewById(R.id.textView_textoItemSelector);
            tv_texto.setText(os.getTexto());

            //Selecciona el objeto si estaba seleccionado

            if (seleccionados.get(getAdapterPosition())) {
                item.setSelected(true);
            } else {
                item.setSelected(false);
            }

            /*Cuando pulsamos un botón*/

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!v.isSelected()) {
                        v.setSelected(true);
                        seleccionados.put(getAdapterPosition(), true);
                    } else {
                        v.setSelected(false);
                        seleccionados.put(getAdapterPosition(), false);

                    }
                }
            });
        }
    }

    /**
     * Método que devuelve los elementos que han sido marcados.
     *
     * @return ArrayList<String>, lista de elementos seleccionados.
     * @name obtenerSeleccionados
     * @author Adrián Antón García
     * @category método
     */

    public ArrayList<String> obtenerSeleccionados() {
        ArrayList<String> marcados = new ArrayList<>();
        for (int i = 0; i < datos.size(); i++) {
            if (seleccionados.get(i)) {
                marcados.add(datos.get(i).getTexto());
            }
        }
        return marcados;
    }
}
