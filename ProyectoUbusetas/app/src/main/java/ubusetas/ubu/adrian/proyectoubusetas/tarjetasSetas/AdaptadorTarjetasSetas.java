package ubusetas.ubu.adrian.proyectoubusetas.tarjetasSetas;

import android.content.Context;
import android.content.Intent;
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
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;

/**
 * Created by adrit on 17/11/2017.
 */

public class AdaptadorTarjetasSetas extends RecyclerView.Adapter<AdaptadorTarjetasSetas.ViewHolder> {

    //contexto de la actividad
    public Context context;
    //lista de tarjetas
    public ArrayList<TarjetaSeta> listaTarjetaSetas;

    //constructor
    public AdaptadorTarjetasSetas(Context context, ArrayList<TarjetaSeta> cardsList) {
        this.context = context;
        this.listaTarjetaSetas = cardsList;
    }

    //devuelve un nuevo view inflado con los elementos necesarios
    @Override
    public AdaptadorTarjetasSetas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.tarjetaseta_view_holder, parent, false);
        return new ViewHolder(v);
    }

    //carga el view holder asociado a la posicion
    @Override
    public void onBindViewHolder(AdaptadorTarjetasSetas.ViewHolder holder, int position) {
        long id = listaTarjetaSetas.get(position).getId();
        id++;
        String name = listaTarjetaSetas.get(position).getName();
        int color = listaTarjetaSetas.get(position).getColorResource();
        Bitmap bit = listaTarjetaSetas.get(position).getImagenSeta();
        TextView inicial = holder.inicial;
        TextView nombre = holder.nombre;
        ImageView imagenSeta = holder.imagenSeta;
        RelativeLayout layout = holder.layoutSeta;

        nombre.setText(name);
        nombre.setBackgroundColor(color);
        inicial.setBackgroundColor(color);
        inicial.setText(Character.toString(name.charAt(0))+"-"+id);
        imagenSeta.setImageBitmap(bit);
        layout.setBackgroundColor(color);


    }

    //numero de elementos en la lista (tarjetas)
    @Override
    public int getItemCount() {
        if (listaTarjetaSetas.isEmpty()) {
            return 0;
        } else {
            return listaTarjetaSetas.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView inicial;
        private TextView nombre;
        private ImageView imagenSeta;
        private RelativeLayout layoutSeta;
        public ViewHolder(View v) {
            super(v);
            inicial = (TextView) v.findViewById(R.id.inicial_tarjeta_seta);
            nombre = (TextView) v.findViewById(R.id.nombre_tarjeta_seta);
            imagenSeta = (ImageView) v.findViewById(R.id.imagen_tarjeta_seta);
            layoutSeta = (RelativeLayout) v.findViewById(R.id.relative_layout_tarjeta_seta) ;

            //Al pulsar una tarjeta
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView nombre=(TextView) v.findViewById(R.id.nombre_tarjeta_seta);

                    Toast.makeText(context, nombre.getText(), Toast.LENGTH_SHORT).show();
                    Intent mostrarSeta = new Intent(context, MostrarInformacionSeta.class);
                    mostrarSeta.putExtra("nombreSeta",nombre.getText());
                    context.startActivity(mostrarSeta);

                }
            });
        }
    }
}
