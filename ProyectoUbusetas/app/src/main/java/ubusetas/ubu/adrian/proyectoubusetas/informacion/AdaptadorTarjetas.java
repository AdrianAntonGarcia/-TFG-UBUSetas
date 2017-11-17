package ubusetas.ubu.adrian.proyectoubusetas.informacion;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ubusetas.ubu.adrian.proyectoubusetas.R;

/**
 * Created by adrit on 17/11/2017.
 */

public class AdaptadorTarjetas extends RecyclerView.Adapter<AdaptadorTarjetas.ViewHolder> {

    //contexto de la actividad
    public Context context;
    //lista de tarjetas
    public ArrayList<Tarjeta> listaTarjetas;

    //constructor
    public AdaptadorTarjetas(Context context, ArrayList<Tarjeta> cardsList) {
        this.context = context;
        this.listaTarjetas = cardsList;
    }

    //devuelve un nuevo view inflado con los elementos necesarios
    @Override
    public AdaptadorTarjetas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.tarjeta_view_holder, parent, false);
        return new ViewHolder(v);
    }

    //carga el view holder asociado a la posicion
    @Override
    public void onBindViewHolder(AdaptadorTarjetas.ViewHolder holder, int position) {
        String name = listaTarjetas.get(position).getName();
        int color = listaTarjetas.get(position).getColorResource();
        Bitmap bit = listaTarjetas.get(position).getImagenSeta();
        TextView inicial = holder.inicial;
        TextView nombre = holder.nombre;
        ImageView imagenSeta = holder.imagenSeta;
        RelativeLayout layout = holder.layoutSeta;
        nombre.setText(name);
        nombre.setBackgroundColor(color);
        inicial.setBackgroundColor(color);
        inicial.setText(Character.toString(name.charAt(0)));
        imagenSeta.setImageBitmap(bit);
        layout.setBackgroundColor(color);


    }

    //numero de elementos en la lista (tarjetas)
    @Override
    public int getItemCount() {
        if (listaTarjetas.isEmpty()) {
            return 0;
        } else {
            return listaTarjetas.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView inicial;
        private TextView nombre;
        private ImageView imagenSeta;
        private RelativeLayout layoutSeta;
        public ViewHolder(View v) {
            super(v);
            inicial = (TextView) v.findViewById(R.id.inicial_tarjeta);
            nombre = (TextView) v.findViewById(R.id.nombre_tarjeta);
            imagenSeta = (ImageView) v.findViewById(R.id.imagen_tarjeta);
            layoutSeta = (RelativeLayout) v.findViewById(R.id.relative_layout_tarjeta) ;

            //Al pulsar una tarjeta
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView nombre=(TextView) v.findViewById(R.id.nombre_tarjeta);
                    Toast.makeText(context, nombre.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
