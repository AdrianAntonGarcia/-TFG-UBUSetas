package ubusetas.ubu.adrian.proyectoubusetas.elegirclaves;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ubusetas.ubu.adrian.proyectoubusetas.R;

/**
 * Created by adrit on 23/11/2017.
 */


public class AdaptadorSelector extends RecyclerView.Adapter<AdaptadorSelector.ViewHolderSelector> {

    private List<ItemSelector> datos;
    private AppCompatActivity context;

    private SparseBooleanArray seleccionados;

    public AdaptadorSelector(AppCompatActivity context, LinkedList<ItemSelector> datos) {
        //modoSeleccion=false;
        this.context = context;
        this.datos = datos;
        seleccionados = new SparseBooleanArray();
    }

    @Override
    public ViewHolderSelector onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_selector, parent, false);
        ViewHolderSelector viewHolder = new ViewHolderSelector(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolderSelector holder, int position) {
        ItemSelector os = datos.get(position);
        holder.bindView(os);
    }

    @Override
    public int getItemCount() {
        if (datos.isEmpty()) {
            return 0;
        } else {
            return datos.size();
        }
    }


    /**
     * VIEWHOLDER
     */
    class ViewHolderSelector extends RecyclerView.ViewHolder {

        private TextView tv_texto;
        private View item;


        public ViewHolderSelector(View itemView) {
            super(itemView);
            this.item = itemView;
        }

        public void bindView(ItemSelector os) {

            tv_texto = (TextView) item.findViewById(R.id.textView_textoItemSelector);
            tv_texto.setText(os.getTexto());


            //Selecciona el objeto si estaba seleccionado
            if (seleccionados.get(getAdapterPosition())) {
                item.setSelected(true);
            } else {
                item.setSelected(false);
            }
            /**Activa el modo de selección
             item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {
            if (!modoSeleccion){
            modoSeleccion = true;
            v.setSelected(true);
            seleccionados.clear();
            seleccionados.put(getAdapterPosition(), true);
            }

            return true;
            }
            });*/

            /**Selecciona/deselecciona un ítem si está activado el modo selección*/
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

    public boolean haySeleccionados() {
        for (int i = 0; i <= datos.size(); i++) {
            if (seleccionados.get(i))
                return true;
        }
        return false;
    }

    /**
     * Devuelve aquellos objetos marcados.
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
