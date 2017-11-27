package ubusetas.ubu.adrian.proyectoubusetas.informacion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;
import ubusetas.ubu.adrian.proyectoubusetas.tarjetasSetas.AdaptadorTarjetasSetas;
import ubusetas.ubu.adrian.proyectoubusetas.tarjetasSetas.TarjetaSeta;

/*
* @name: MostrarSetas
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que muestra las setas de la aplicación.
* */

public class MostrarSetas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //RecyclerView que va a contener las tarjetas de las setas
    private RecyclerView recyclerView;
    //Adaptador que va a enlazar las tarjetas con sus layout
    private AdaptadorTarjetasSetas adapter;
    //Lista con las tarjetas
    private ArrayList<TarjetaSeta> listaTarjetaSetas = new ArrayList<>();
    //Array con los colores de las tarjetas
    private int[] colors;
    //Array con los nombres de las tarjetas
    private String[] names;

    /*
    * @name: onCreate
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que se ejecuta cuando se carga la clase, inicializa los elementos
    * y los relaciona con el contexto.
    * @param: Bundle, Bundle donde se guardan los datos cuando se cierra la actividad.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostar_setas);

        //cargamos la lista de setas y los colores
        names = getResources().getStringArray(R.array.nombres_setas);
        colors = getResources().getIntArray(R.array.initial_colors_mostrar_setas);

        //inicializamos las tarjetas
        inicializarTarjetas();

        //Creamos el adaptador entre las tarjetas y el layout de las tarjetas
        if (adapter == null) {
            adapter = new AdaptadorTarjetasSetas(this, listaTarjetaSetas);
        }

        //a que recycler esta adapatado esta actividad
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_lista_setas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //parte del menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_mostrar_setas);
        //cargamos la nueva barra
        setSupportActionBar(toolbar);

        //cargamos el layout del menu y lo inicializamos
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_setas);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*
     * @name: inicializarTarjetas
     * @Author: Adrián Antón García
     * @category: Procedimiento
     * @Description: Procedimiento que inicializa las tarjetas de las setas
     * */

    private void inicializarTarjetas() {
        //170 tarjetas
        for (int i = 0; i < 171; i++) {
            //Inicializamos la tarjeta
            String nombreSeta = names[i];
            TarjetaSeta card = new TarjetaSeta();
            card.setId((long) i);
            card.setName(nombreSeta);
            card.setColorResource(colors[i]);

            //Cargamos la imágen de esa tarjeta
            String path = "imagenesSetas/" + nombreSeta.toLowerCase() + "/" + nombreSeta.toLowerCase().trim() + " " + "(" + 1 + ")" + ".jpg";
            AccesoDatosExternos acceso = new AccesoDatosExternos(this);
            Bitmap bit = acceso.accesoImagenPorPath(path);
            card.setImagenSeta(bit);
            listaTarjetaSetas.add(card);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_setas);
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
    * @Description: Metodo que se activa cuando pulsamos un botón del menú.
    * @Param: MenuItem, Item pulsado del menú.
    * */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_clasificar) {
            Intent cambioActividad = new Intent(MostrarSetas.this, RecogerFoto.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_ir_claves) {
            Intent cambioActividad = new Intent(MostrarSetas.this, MostrarClaves.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_home) {
            Intent cambioActividad = new Intent(MostrarSetas.this, Lanzadora.class);
            startActivity(cambioActividad);
        }
        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_setas);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
