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

/*
* @name: AdaptadorSelector
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que implementa el adaptador para cargar los elementos(ItemSelector)
* de la lista de géneros a seleccionar
* */

public class AdaptadorSelector extends RecyclerView.Adapter<AdaptadorSelector.ViewHolderSelector> {

    //Lista de elementos
    private List<ItemSelector> datos;
    //Contexto de la aplicación
    private AppCompatActivity context;
    //Array que almacena un booleano que indica si el elemento de la lista esta pulsado o no
    private SparseBooleanArray seleccionados;

    /*
    * @name: AdaptadorSelector
    * @Author: Adrián Antón García
    * @category: constructor
    * @Description: Constructor que inicializa el adaptador de la lista de generos
    * @param: AppCompatActivity, contexto de la actividad donde se va a cargar el adaptador
    * @param: LinkedList<ItemSelector>,  Lista que contiene los elementos
    * */

    public AdaptadorSelector(AppCompatActivity context, LinkedList<ItemSelector> datos) {
        //modoSeleccion=false;
        this.context = context;
        this.datos = datos;
        seleccionados = new SparseBooleanArray();
    }

    /*
     * @name: onCreateViewHolder
     * @Author: Adrián Antón García
     * @category: método
     * @Description: Método que se llama para cargar un elemento en la lista.
     * Estos métodos son llamados por el sistema.
     * @param: ViewGroup, Grupo de vistas de los elementos a cargar.
     * @param: int, elemento seleccionado a cargar.
     * @return: ViewHolderSelector, contenendor de elementos de la lista.
     * */

    @Override
    public ViewHolderSelector onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_selector, parent, false);
        ViewHolderSelector viewHolder = new ViewHolderSelector(v);
        return viewHolder;
    }

    /*
     * @name: onBindViewHolder
     * @Author: Adrián Antón García
     * @category: procedimiento
     * @Description: Procedimiento que inicializa un elemento de la lista(selector).
     * Estos métodos son llamados por el sistema.
     * @param: ViewHolderSelector, Vista del selector
     * @param: int, elemento del selector a inicializar
     * */

    @Override
    public void onBindViewHolder(ViewHolderSelector holder, int position) {
        ItemSelector os = datos.get(position);
        holder.bindView(os);
    }

    /*
     * @name: getItemCount
     * @Author: Adrián Antón García
     * @category: método
     * @Description: Método que devuelve cuantos elementos hay cargados en la lista.
     * Estos métodos son llamados por el sistema.
     * @return: int, número de elementos.
     * */

    @Override
    public int getItemCount() {
        if (datos.isEmpty()) {
            return 0;
        } else {
            return datos.size();
        }
    }

    /*
    * @name: ViewHolderSelector
    * @Author: Adrián Antón García
    * @category: clase
    * @Description: Clase que implementa los elementos que se deben cargar en la lista(selector)y los
    * relaciona con los elementos de la interfaz.
    * */

    class ViewHolderSelector extends RecyclerView.ViewHolder {

        private TextView tv_texto;
        private View item;

        /*
        * @name: ViewHolderSelector
        * @Author: Adrián Antón García
        * @category: Constructor
        * @Description: Constructor que inicializa el contenedor de elementos.
        * @param: View, vista de la interfaz con el que se relaciona el contenedor
        * */

        public ViewHolderSelector(View itemView) {
            super(itemView);
            this.item = itemView;
        }

        /*
         * @name: bindView
         * @Author: Adrián Antón García
         * @category: procedimiento
         * @Description: Procedimiento que inicializa un elemento de la lista(selector)
         * y lo relaciona con los elementos de la interfaz.
         * @param: ItemSelector, Elemento a inicializar
         * */

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

    /*
     * @name: obtenerSeleccionados
     * @Author: Adrián Antón García
     * @category: método
     * @Description: Método que devuelve los elementos que han sido marcados.
     * @return: ArrayList<String>, lista de elementos seleccionados.
     * */

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
