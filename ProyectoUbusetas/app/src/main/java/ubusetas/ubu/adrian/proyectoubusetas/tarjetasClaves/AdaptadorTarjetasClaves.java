package ubusetas.ubu.adrian.proyectoubusetas.tarjetasClaves;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ubusetas.ubu.adrian.proyectoubusetas.R;

/**
 * Created by adrit on 17/11/2017.
 */

public class AdaptadorTarjetasClaves extends RecyclerView.Adapter<AdaptadorTarjetasClaves.ViewHolder> {

    //contexto de la actividad
    public Context context;
    //lista de tarjetas
    public ArrayList<TarjetaClave> listaTarjetaClaves;

    //constructor
    public AdaptadorTarjetasClaves(Context context, ArrayList<TarjetaClave> cardsList) {
        this.context = context;
        this.listaTarjetaClaves = cardsList;
    }

    //devuelve un nuevo view inflado con los elementos necesarios
    @Override
    public AdaptadorTarjetasClaves.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.tarjetaclave_view_holder, parent, false);
        return new ViewHolder(v);
    }

    //carga el view holder asociado a la posicion
    @Override
    public void onBindViewHolder(AdaptadorTarjetasClaves.ViewHolder holder, int position) {
        String name = listaTarjetaClaves.get(position).getName();
        int color = listaTarjetaClaves.get(position).getColorResource();
        Bitmap bit = listaTarjetaClaves.get(position).getImagenSeta();
        //TextView inicial = holder.inicial;
        TextView nombre = holder.nombre;
        ImageView imagenSeta = holder.imagenSeta;
        RelativeLayout layout = holder.layoutSeta;
        nombre.setText(name);
        nombre.setBackgroundColor(color);
        //inicial.setBackgroundColor(color);
        //inicial.setText(Character.toString(name.charAt(0)));
        imagenSeta.setImageBitmap(bit);
        layout.setBackgroundColor(color);
    }

    //numero de elementos en la lista (tarjetas)
    @Override
    public int getItemCount() {
        if (listaTarjetaClaves.isEmpty()) {
            return 0;
        } else {
            return listaTarjetaClaves.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //private TextView inicial;
        private TextView nombre;
        private ImageView imagenSeta;
        private RelativeLayout layoutSeta;
        public ViewHolder(View v) {
            super(v);
            //inicial = (TextView) v.findViewById(R.id.inicial_tarjeta);
            nombre = (TextView) v.findViewById(R.id.nombre_tarjeta_clave);
            imagenSeta = (ImageView) v.findViewById(R.id.imagen_tarjeta_clave);
            layoutSeta = (RelativeLayout) v.findViewById(R.id.relative_layout_tarjeta_clave) ;

            //Al pulsar una tarjeta
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView nombre=(TextView) v.findViewById(R.id.nombre_tarjeta_clave);

                    Toast.makeText(context, nombre.getText(), Toast.LENGTH_SHORT).show();
                    /*Intent mostrarSeta = new Intent(context, MostrarInformacionSeta.class);
                    mostrarSeta.putExtra("nombreSeta",nombre.getText());
                    context.startActivity(mostrarSeta);*/

                }
            });
        }
    }
}
