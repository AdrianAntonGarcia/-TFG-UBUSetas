package ubusetas.ubu.adrian.proyectoubusetas.tarjetasclaves;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.ClaveDicotomica;

/**
 * Clase que implementa el adaptador para cargar los elementos
 * de la lista de las claves dicotómicas.
 *
 * @author Adrián Antón García
 * @name AdaptadorTarjetasClaves
 * @category clase
 */

public class AdaptadorTarjetasClaves extends RecyclerView.Adapter<AdaptadorTarjetasClaves.ViewHolder> {

    //contexto de la actividad
    public Context context;
    //lista de tarjetas
    public ArrayList<TarjetaClave> listaTarjetaClaves;

    /**
     * Constructor que inicializa el adaptador de la lista de claves
     *
     * @param AppCompatActivity,       contexto de la actividad donde se va a cargar el adaptador
     * @param ArrayList<TarjetaClave>, Lista que contiene los elementos
     * @name AdaptadorSelector
     * @author Adrián Antón García
     * @category constructor
     */

    public AdaptadorTarjetasClaves(Context context, ArrayList<TarjetaClave> cardsList) {
        this.context = context;
        this.listaTarjetaClaves = cardsList;
    }

    /**
     * Método que se llama para cargar un elemento en la lista.
     * Estos métodos son llamados por el sistema.
     *
     * @param ViewGroup, Grupo de vistas de los elementos a cargar.
     * @param int,       elemento seleccionado a cargar.
     * @return ViewhHodler, contenendor de elementos de la lista.
     * @name onCreateViewHolder
     * @author Adrián Antón García
     * @category método
     */

    @Override
    public AdaptadorTarjetasClaves.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.tarjetaclave_view_holder, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Procedimiento que inicializa todos los atributos de un elemento de la lista.
     * Estos métodos son llamados por el sistema.
     *
     * @param ViewHolderSelector, Vista del selector
     * @param int,                elemento del selector a inicializar
     * @name onBindViewHolder
     * @author Adrián Antón García
     * @category procedimiento
     */

    @Override
    public void onBindViewHolder(AdaptadorTarjetasClaves.ViewHolder holder, int position) {
        String name = listaTarjetaClaves.get(position).getName();
        int color = listaTarjetaClaves.get(position).getColorResource();
        long id = listaTarjetaClaves.get(position).getId();
        TextView nombre = holder.nombre;
        TextView inicial = holder.inicial;
        RelativeLayout layout = holder.layoutSeta;
        nombre.setText(name);
        nombre.setBackgroundColor(color);
        inicial.setBackgroundColor(color);
        inicial.setText(Character.toString(name.charAt(0)) + "-" + id);
        layout.setBackgroundColor(color);
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
        if (listaTarjetaClaves.isEmpty()) {
            return 0;
        } else {
            return listaTarjetaClaves.size();
        }
    }

    /**
     * Clase que implementa los elementos que se deben cargar en la lista de claves.
     *
     * @author Adrián Antón García
     * @name ViewHolder
     * @category clase
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView inicial;
        private TextView nombre;
        private RelativeLayout layoutSeta;

        /**
         * Constructor que inicializa el contenedor de elementos.
         *
         * @param View, vista de la interfaz con el que se relaciona el contenedor
         * @name ViewHolder
         * @author Adrián Antón García
         * @category Constructor
         */

        public ViewHolder(View v) {
            super(v);
            inicial = (TextView) v.findViewById(R.id.inicial_tarjeta_clave);
            nombre = (TextView) v.findViewById(R.id.nombre_tarjeta_clave);
            //imagenSeta = (ImageView) v.findViewById(R.id.imagen_tarjeta_clave);
            layoutSeta = (RelativeLayout) v.findViewById(R.id.relative_layout_tarjeta_clave);

            //Al pulsar una tarjeta
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView nombre = (TextView) v.findViewById(R.id.nombre_tarjeta_clave);
                    Toast.makeText(context, nombre.getText(), Toast.LENGTH_SHORT).show();
                    Intent mostrarSeta = new Intent(context, ClaveDicotomica.class);
                    mostrarSeta.putExtra("nombreClave", nombre.getText());
                    mostrarSeta.putExtra("actividadPrevia", 2);
                    context.startActivity(mostrarSeta);
                }
            });
        }
    }
}
