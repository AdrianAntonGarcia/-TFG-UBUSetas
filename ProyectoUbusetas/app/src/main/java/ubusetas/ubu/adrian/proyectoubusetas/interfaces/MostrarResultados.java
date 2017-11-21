package ubusetas.ubu.adrian.proyectoubusetas.interfaces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;
import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.ClaveDicotomica;

/*
* @name: MostrarResultados
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que iplementa la funcionalidad de la actividad que muestra los resultados obtenidos tras
* clasificar una foto
* */

public class MostrarResultados extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    //widgets
    //ImageView imageViewImagenResultado1;

    //TextView textViewTextoResultados;
    Button botonVolverMostrarPrincipal;
    Button botonRefrescarResultados;
    Button boton_clave;
    ListView listViewListaResultados;
    //lista de las especies clasificadas para esa foto
    List<String> resultados;
    //lista de las especies clasificadas para esa foto sin id
    List<String> resultadosSinNum;
    //lista con los nombres de las carpetas de las especies clasificadas para esa foto
    List<String> nombresSetas;
    List<SetasLista> listaSetas;
    Bitmap bitmapImagen;

    int posImagenSeta;

    /*
    * @name: onCreate
    * @Author: Adrián Antón García
    * @category: procedure
    * @Description: Metodo que se ejecuta cuando se inicia la actividad MostrarResultados
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_resultados);

        //inicializo los widgets

        botonVolverMostrarPrincipal = (Button) findViewById(R.id.boton_volver_mostrar_principal);
        botonRefrescarResultados = (Button) findViewById(R.id.boton_refrescar_resultados);
        boton_clave = (Button) findViewById(R.id.boton_clave);
        botonRefrescarResultados.setOnClickListener(this);
        botonVolverMostrarPrincipal.setOnClickListener(this);
        boton_clave.setOnClickListener(this);
        //imageViewImagenResultado1 = (ImageView) findViewById(R.id.imageView_imagen_resultado1);
        //textViewTextoResultados = (TextView) findViewById(R.id.textView_textoResultados);
        listViewListaResultados = (ListView) findViewById(R.id.listView_lista_resultados);

        //recojo los datos provenientes de la actividad principal

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();

        //cargo la imágen introducida por el usuario

        bitmapImagen = (Bitmap) datosRecibidos.get("fotoBitmap");
        //imageViewImagenResultado1.setImageBitmap(bitmapImagen);

        //Cargamos el contador de refresco

        posImagenSeta = (int) datosRecibidos.get("posImagenSeta");


        //inicializo las listas

        resultadosSinNum = new ArrayList<String>();
        nombresSetas = new ArrayList<String>();

        //cargo los resultados obtenidos en las listas

        resultados = (List<String>) datosRecibidos.get("resultados");
        cargarListas(resultados);
        cargarListaElementos();

        //creo el adaptador para inflar el ListView

        AdapatadorSetasLista adaptador = new AdapatadorSetasLista(this, R.layout.listview_item_row, listaSetas);

        //inflo la lista con sus elementos de imagen+texto

        View header = (View) getLayoutInflater().inflate(R.layout.list_header_row, null);

        //le pongo una cabecera y el adaptador

        listViewListaResultados.addHeaderView(header);
        listViewListaResultados.setAdapter(adaptador);
        //activo que la lista este pendiente de ser pulsada
        listViewListaResultados.setOnItemClickListener(this);
        //activo que la lista este pendiente de pulsaciones largas
        listViewListaResultados.setOnItemLongClickListener(this);
    }

    /*
     * @name: cargarListas
     * @Author: Adrián Antón García
     * @category: procedure
     * @Description: Metodo que carga las diferentes listas con los resultados recibidos
     * */

    public void cargarListas(List<String> res) {
        for (String e : res) {

            //Elimino el identificador de especie

            e = e.split("]")[1];
            e = e.trim();
            resultadosSinNum.add(e);

            //elimino el porcentaje de acierto

            e = e.split("\\(")[0];
            e.trim();
            nombresSetas.add(e);
        }
    }

    /*
     * @name: cargarListaElementos
     * @Author: Adrián Antón García
     * @category: procedure
     * @Description: Metodo que carga la lista que se le va a pasar al adaptador para que cargue los resultados
     * en el listView
     * */

    public void cargarListaElementos() {
        listaSetas = new ArrayList<>();
        Random rand = new Random();
        int i = 0;
        for (String s : resultadosSinNum) {
            //path + nombre
            listaSetas.add(new SetasLista("imagenesSetas/" + nombresSetas.get(i).trim() + "/" + nombresSetas.get(i) + "(" + posImagenSeta + ")" + ".jpg", s));
            i++;
        }
    }

    /*
     * @name: onItemClick
     * @Author: Adrián Antón García
     * @category: procedure
     * @Description: Procedimiento que se ejecuta cuando se clica sobre algún elemento del listView
     * */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //busco el icono de la imagen pulsada
        String path = listaSetas.get(position - 1).path;
        //cargo la imagen asociada a ese path
        if (path != null) {
            InputStream is = null;
            try {
                is = this.getResources().getAssets().open(path);
            } catch (IOException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
            Bitmap bit = BitmapFactory.decodeStream(is);
            if (bit != null) {
                //asocio el bitmap al imageview
                Intent mostrarComparativa = new Intent(MostrarResultados.this, MostrarComparativa.class);
                mostrarComparativa.putExtra("fotoBitmap", bitmapImagen);
                mostrarComparativa.putExtra("fotoSeta", bit);
                mostrarComparativa.putExtra("posImagenSeta", posImagenSeta);
                mostrarComparativa.putStringArrayListExtra("resultados", (ArrayList<String>) resultados);
                startActivity(mostrarComparativa);
            } else {
                Toast.makeText(this, "bitmap null", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error en la carga de la imágen", Toast.LENGTH_LONG).show();
        }
    }

    /*
     * @name: onItemLongClick
     * @Author: Adrián Antón García
     * @category: procedure
     * @Description: Procedimiento que se ejecuta cuando se mantiene pulsado sobre algún elemento del listView
     * */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String path = listaSetas.get(position - 1).path;
        //cargo la imagen asociada a ese path
        if (path != null) {
            InputStream is = null;
            try {
                is = this.getResources().getAssets().open(path);
            } catch (IOException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
            Bitmap bit = BitmapFactory.decodeStream(is);
            if (bit != null) {
                //asocio el bitmap al imageview
                Intent mostrarInfoSeta = new Intent(MostrarResultados.this, MostrarInformacionSeta.class);
                mostrarInfoSeta.putExtra("nombreSeta", nombresSetas.get(position - 1));
                mostrarInfoSeta.putExtra("fotoBitmap", bitmapImagen);
                mostrarInfoSeta.putExtra("fotoSeta", bit);
                mostrarInfoSeta.putExtra("posImagenSeta", posImagenSeta);
                mostrarInfoSeta.putStringArrayListExtra("resultados", (ArrayList<String>) resultados);
                startActivity(mostrarInfoSeta);
            } else {
                Toast.makeText(this, "bitmap null", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error en la carga de la imágen", Toast.LENGTH_LONG).show();
        }
        return true;
    }
    /*
     * @name: onClick
     * @Author: Adrián Antón García
     * @category: procedire
     * @Description: Procedimiento que se ejecuta cada vez que se pulsa un botón
     * */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton_volver_mostrar_principal: //Volver a la actividad principal
                Intent cambioActividad = new Intent(MostrarResultados.this, RecogerFoto.class);
                startActivity(cambioActividad);
                break;
            case R.id.boton_refrescar_resultados: //Refrescar
                Intent refresco = getIntent();
                refresco.putStringArrayListExtra("resultados", (ArrayList<String>) resultados);
                refresco.putExtra("fotoBitmap", bitmapImagen);

                posImagenSeta++;
                if (posImagenSeta == 6) {
                    posImagenSeta = 1;
                }
                refresco.putExtra("posImagenSeta", posImagenSeta);
                finish();
                startActivity(refresco);
                break;
            case R.id.boton_clave:
                Intent clave = new Intent(MostrarResultados.this, ClaveDicotomica.class);
                clave.putStringArrayListExtra("resultados", (ArrayList<String>) resultados);
                clave.putExtra("fotoBitmap", bitmapImagen);
                clave.putExtra("posImagenSeta", posImagenSeta);
                clave.putStringArrayListExtra("nombresSetas", (ArrayList<String>) nombresSetas);
                finish();
                startActivity(clave);
                break;
        }
    }


}
