package ubusetas.ubu.adrian.proyectoubusetas.clasificador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ubusetas.ubu.adrian.proyectoubusetas.R;

public class MostrarComparativa extends AppCompatActivity implements View.OnClickListener{

    private Button botonVolver;
    private ImageView imagenArriba;
    private ImageView imagenAbajo;

    private Bitmap imagenUsuario;
    private Bitmap imagenComparar;
    private int posImagenSeta;
    private List<String> resultados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_comparativa);
        botonVolver = (Button) findViewById(R.id.boton_volver_comparativa);
        botonVolver.setOnClickListener(this);
        imagenArriba = (ImageView) findViewById(R.id.ImageView_Comparativa_1);
        imagenAbajo = (ImageView) findViewById(R.id.ImageView_Comparativa_2);

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();
        //recibo la información que llega de mostrar resultados

        imagenComparar = (Bitmap) datosRecibidos.get("fotoSeta");
        imagenUsuario = (Bitmap) datosRecibidos.get("fotoBitmap");
        posImagenSeta = (int) datosRecibidos.get("posImagenSeta");
        resultados = (List<String>) datosRecibidos.get("resultados");

        //Asocio las imagenes

        imagenArriba.setImageBitmap(imagenComparar);
        imagenAbajo.setImageBitmap(imagenUsuario);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton_volver_comparativa: //Volver a la actividad mostrar resultados
                Intent cambioActividad = new Intent(MostrarComparativa.this, MostrarResultados.class);
                //devuelvo la info necesaria para cargar la clase mostrar resultados
                cambioActividad.putExtra("fotoBitmap", imagenUsuario);
                cambioActividad.putExtra("posImagenSeta", posImagenSeta);
                cambioActividad.putStringArrayListExtra("resultados", (ArrayList<String>) resultados);
                startActivity(cambioActividad);
                break;
        }
    }
}
