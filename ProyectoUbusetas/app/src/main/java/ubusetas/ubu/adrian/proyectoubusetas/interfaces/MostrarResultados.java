package ubusetas.ubu.adrian.proyectoubusetas.interfaces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;
import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.ClaveDicotomica;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

/*
* @name: MostrarResultados
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que iplementa la funcionalidad de la actividad que muestra los resultados obtenidos tras
* clasificar una foto
* */

public class MostrarResultados extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, NavigationView.OnNavigationItemSelectedListener {

    //widgets
    //ImageView imageViewImagenResultado1;

    //TextView textViewTextoResultados;

    FloatingActionButton botonRefrescarResultados;
    FloatingActionButton boton_clave;
    ListView listViewListaResultados;
    //lista de las especies clasificadas para esa foto
    List<String> resultados;
    //lista de las especies clasificadas para esa foto sin id
    List<String> resultadosSinNum;
    //lista con los nombres de las carpetas de las especies clasificadas para esa foto
    List<String> nombresSetas;
    List<SetasLista> listaSetas;
    Bitmap bitmapImagen;

    //Paramatro que indica la foto cargada
    int posImagenSeta=1;

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


        botonRefrescarResultados = (FloatingActionButton) findViewById(R.id.boton_refrescar_resultados);
        boton_clave = (FloatingActionButton) findViewById(R.id.boton_clave);
        botonRefrescarResultados.setOnClickListener(this);

        boton_clave.setOnClickListener(this);
        listViewListaResultados = (ListView) findViewById(R.id.listView_lista_resultados);

        //recojo los datos provenientes de la actividad principal

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();

        //cargo la imágen introducida por el usuario

        bitmapImagen = (Bitmap) datosRecibidos.get("fotoBitmap");

        //inicializo las listas

        resultadosSinNum = new ArrayList<String>();
        nombresSetas = new ArrayList<String>();

        //cargo los resultados obtenidos en las listas

        resultados = (List<String>) datosRecibidos.get("resultados");
        cargarListas(resultados);
        //cargo el listview
        inflarLista();

        //activo que la lista este pendiente de ser pulsada y de pulsaciones largas
        listViewListaResultados.setOnItemClickListener(this);
        listViewListaResultados.setOnItemLongClickListener(this);

        //parte del menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_mostrar_resultados);
        //cargamos la nueva barra
        setSupportActionBar(toolbar);

        //cargamos el layout del menu y lo inicializamos
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_resultados);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d("resultados","resultados"+resultados.toString());
        Log.d("resultadosSinNum","resultadosSinNum"+resultadosSinNum.toString());
        Log.d("nombresSetas","nombresSetas"+nombresSetas.toString());
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
     * en el listView, usa el numFoto para cargar la imagen correspondiente al número
     * */

    public void cargarListaElementos(int numFoto) {
        listaSetas = new ArrayList<>();
        int i = 0;
        for (String nombre : resultadosSinNum) {
            //path + nombre
            listaSetas.add(new SetasLista("imagenesSetas/" + nombresSetas.get(i).trim() + "/" + nombresSetas.get(i) + "(" + numFoto + ")" + ".jpg", nombre));
            i++;
        }
    }

    /*
     * @name: inflarLista
     * @Author: Adrián Antón García
     * @category: procedure
     * @Description: Procedimiento que carga los resultados en la lista
     * */

    public void inflarLista(){
        //cargamos la la lista con el número de foto correspondiente
        cargarListaElementos(posImagenSeta);
        AdapatadorSetasLista adaptador = new AdapatadorSetasLista(this, R.layout.listview_item_row, listaSetas);
        listViewListaResultados.destroyDrawingCache();
        listViewListaResultados.setAdapter(adaptador);
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
        String path = listaSetas.get(position).path;
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
                //mostrarComparativa.putExtra("posImagenSeta", posImagenSeta);
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
        String path = listaSetas.get(position).path;
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
                mostrarInfoSeta.putExtra("nombreSeta", nombresSetas.get(position));
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
            case R.id.boton_refrescar_resultados: //Refrescar
                posImagenSeta++;
                if (posImagenSeta == 6) {
                    posImagenSeta = 1;
                }
                inflarLista();
                break;
            case R.id.boton_clave:
                Intent clave = new Intent(MostrarResultados.this, ClaveDicotomica.class);
                clave.putExtra("nombreClave",resultados.get(0).split(" ")[0].trim());
                finish();
                startActivity(clave);
                break;
        }
    }

        /*
    * @name: onBackPressed
    * @Author: Adrián Antón García
    * @category: Procedimiento
    * @Description: Procedimiento que se ejectua cuando se pulsa el boton volver del movil.
    * */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_resultados);
        //si el menu esta abierto
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //lo cerramos
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //si el menu esta cerrado llamamos al constructor padre
            super.onBackPressed();
        }
    }
        /*
    * @name: onNavigationItemSelected
    * @Author: Adrián Antón García
    * @category: Metodo
    * @Description: Metodo que se activa cuando pulsamos un botón del menú
    * */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_clasificar) {
            Intent cambioActividad = new Intent(MostrarResultados.this, RecogerFoto.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_ir_claves) {
            Intent cambioActividad = new Intent(MostrarResultados.this, MostrarClaves.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_home) {
            Intent cambioActividad = new Intent(MostrarResultados.this, Lanzadora.class);
            startActivity(cambioActividad);
        }
        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_resultados);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
