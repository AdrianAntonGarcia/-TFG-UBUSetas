package ubusetas.ubu.adrian.proyectoubusetas.elegirclaves;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.ClaveDicotomica;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarResultados;

/*
* @name: ElegirClaves
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que muestra los generos a elegir para filtrar la clave dicotómica general.
* */

public class ElegirClaves extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //Estructura donde se almacenan las claves

    private TreeMap<String, ArrayList<Object>> claves;
    private AccesoDatosExternos acceso;

    //Elementos de la interfaz

    private RecyclerView selector;
    private TextView textoSeleccionados;
    private Button boton_obtener;
    private Button boton_ir_clave;

    //Arrays donde se guardan los resultados obtenidos por el clasificador

    private ArrayList<String> resultados;
    private LinkedList itemsSelector;
    private AdaptadorSelector adaptador;

    //géneros marcados por el usuario
    private ArrayList<String> marcados;
    //Bitmap del usuario
    private Bitmap bitmapImagen;
    private ArrayList<String> resultadosAdevolver;
    //Idioma de la aplicación
    private String idioma;
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
        setContentView(R.layout.activity_elegir_claves);

        //Inicializamos el idioma de la aplicación
        idioma = null;
        idioma = Locale.getDefault().getLanguage();

        //Inicialización de las variables

        resultados = new ArrayList<String>();
        acceso = new AccesoDatosExternos(this);
        marcados = new ArrayList<String>();

        Log.d("IDIOMA", Locale.getDefault().getCountry());
        //Interfaz

        selector = (RecyclerView) findViewById(R.id.recycler_view_lista_seleccion_claves);
        textoSeleccionados = (TextView) findViewById(R.id.textView_generos_selecionados);
        boton_obtener = (Button) findViewById(R.id.boton_obtener);
        boton_ir_clave = (Button) findViewById(R.id.boton_ir_clave);

        //recojo los datos provenientes de la actividad mostrar resultados

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();
        resultados = datosRecibidos.getStringArrayList("resultados");
        bitmapImagen = datosRecibidos.getParcelable("fotoBitmap");
        resultadosAdevolver = datosRecibidos.getStringArrayList("resultadosAdevolver");
        //cargamos el idioma si se ha rotado la pantalla
        if (datosRecibidos.containsKey("idioma")) {
            idioma = datosRecibidos.getString("idioma");
        }
        //restauro los elementos necesarios si se ha rotado la pantalla
        restaurarCampos(savedInstanceState);
        Log.d("resultados", resultados.toString());

        //Cargo las claves

        //Inicializo la lista con los resultados obtenidos que están dentro de la clave general
        cargarLista();

        //Si el número de items contenidos en la clave de generos es menor que 2 pasamos directamente a mostrar la clave completa

        if (itemsSelector.size() < 2) {
            Intent cambioActividad = new Intent(ElegirClaves.this, ClaveDicotomica.class);
            cambioActividad.putExtra("nombreClave", "general");
            startActivity(cambioActividad);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        selector.setLayoutManager(layoutManager);
        adaptador = new AdaptadorSelector(this, itemsSelector);
        selector.setAdapter(adaptador);

        //Activamos los botones
        boton_obtener.setOnClickListener(this);
        boton_ir_clave.setOnClickListener(this);
        boton_ir_clave.setVisibility(View.GONE);

        //parte del menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_elegir_claves);
        //cargamos la nueva barra
        setSupportActionBar(toolbar);

        //cargamos el layout del menu y lo inicializamos
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_elegir_claves);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*
    * @name: cargarLista
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que convierte la lista de especies en una de generos
    * */

    public void convertirAGeneros() {
        for (int i = 0; i < resultados.size(); i++)
            //Cambiamos la especie de la lista por su genero
            resultados.set(i, resultados.get(i).split(" ")[0].trim());
    }

    /*
    * @name: cargarLista
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que carga la lista de items del selector a partir de los resultados obtenidos
    * */

    public void cargarLista() {

        convertirAGeneros();
        /*Compruebo que generos de los obtenidos en los resultados estan contenidos dentro de la clave general y solo muestro esos*/
        claves = acceso.readFromFile();
        ArrayList<Object> mapas = claves.get("general");
        Map<String, String> generosNodos = (Map<String, String>) mapas.get(2);
        String genero = null;
        itemsSelector = new LinkedList();
        for (int i = 0; i < resultados.size(); i++) {
            genero = resultados.get(i).trim();
            //Cargo solo aquellos generos que estan en la clave general
            if (generosNodos.containsKey(genero)) {
                itemsSelector.add(new ItemSelector(genero));
            }
        }
    }
    /*
     * @name: restaurarCampos
     * @Author: Adrián Antón García
     * @category: procedimiento
     * @Description: Procedimiento que se restaura el bitmap al girar la pantalla.
     * @param: Bundle, Bundle donde se guardan los datos cuando se cierra la actividad.
     * */

    private void restaurarCampos(Bundle savedInstanceState) {

        // Si hay algo en el bundle
        if (savedInstanceState != null) {
            idioma = savedInstanceState.getString("idioma");
            acceso.actualizarIdioma(idioma);
            //hay que actualizar al cambiar el idioma
            Intent intent = new Intent();
            intent.setClass(this, this.getClass());
            intent.putExtra("idioma", idioma);
            intent.putStringArrayListExtra("resultados", resultados);
            intent.putStringArrayListExtra("resultadosAdevolver", resultadosAdevolver);
            intent.putExtra("fotoBitmap", bitmapImagen);
            //llamamos a la actividad
            this.startActivity(intent);
            this.finish();
        }
    }

    /*
    * @name: onSaveInstanceState
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que se ejecuta cuando se destruye la actividad.
    * @param: Bundle, Bundle donde se guardan los datos cuando se cierra la actividad.
    * */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //guardo el idioma
        outState.putString("idioma", idioma);
    }
    /*
    * @name: onBackPressed
    * @Author: Adrián Antón García
    * @category: Procedimiento
    * @Description: Procedimiento que se ejectua cuando se pulsa el boton volver del movil.
    * */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_elegir_claves);
        //si el menu esta abierto
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //lo cerramos
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(ElegirClaves.this, MostrarResultados.class);
            intent.putExtra("idioma", idioma);
            intent.putStringArrayListExtra("resultados", resultadosAdevolver);
            intent.putExtra("fotoBitmap", bitmapImagen);
            this.startActivity(intent);
            //si el menu esta cerrado llamamos al constructor padre
            finish();
        }
    }

    /*
    * @name: onClick
    * @Author: Adrián Antón García
    * @category: Procedimiento
    * @Description: Procedimiento que es llamado cuándo se hace click en cualquier botón.
    * @param: View, Vista del botón pulsado.
    * */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton_obtener:
                marcados = adaptador.obtenerSeleccionados();
                if (Locale.getDefault().getCountry().equals("ES")) {
                    textoSeleccionados.setText("Géneros seleccionados: " + marcados.toString());
                } else {
                    textoSeleccionados.setText("Selected genres: " + marcados.toString());
                }
                if (marcados.size() < 2) {
                    if (Locale.getDefault().getCountry().equals("ES")) {
                        textoSeleccionados.setText(textoSeleccionados.getText() + " Todavía no se han seleccionado 2 o mas géneros.");
                    } else {
                        textoSeleccionados.setText(textoSeleccionados.getText() + " Two or more genera have not yet been selected.");
                    }
                }
                if (marcados.size() < 2) {
                    boton_ir_clave.setVisibility(View.GONE);
                } else {
                    boton_ir_clave.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.boton_ir_clave:
                if (marcados.size() >= 2) {
                    Intent cambioActividad = new Intent(ElegirClaves.this, ClaveDicotomica.class);
                    cambioActividad.putStringArrayListExtra("generosMarcados", marcados);
                    cambioActividad.putStringArrayListExtra("resultados", resultados);
                    startActivity(cambioActividad);
                }
                break;
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
        int id = item.getItemId();
        if (id == R.id.menu_clasificar) {
            Intent cambioActividad = new Intent(ElegirClaves.this, RecogerFoto.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_ir_claves) {
            Intent cambioActividad = new Intent(ElegirClaves.this, MostrarClaves.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_informacion) {
            Intent cambioActividad = new Intent(ElegirClaves.this, MostrarSetas.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_home) {
            Intent cambioActividad = new Intent(ElegirClaves.this, Lanzadora.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_idioma) {
            if (Locale.getDefault().getLanguage().equals("es")) {
                acceso.actualizarIdioma("en");
                idioma = "en";
                Toast.makeText(this, "Language changed", Toast.LENGTH_LONG).show();
            } else {
                acceso.actualizarIdioma("es");
                idioma = "es";
                Toast.makeText(this, "Idioma cambiado", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent();
            intent.setClass(this, this.getClass());
            intent.putExtra("idioma", idioma);
            intent.putStringArrayListExtra("resultados", resultados);
            intent.putStringArrayListExtra("resultadosAdevolver", resultadosAdevolver);
            intent.putExtra("fotoBitmap", bitmapImagen);
            //llamamos a la actividad
            this.startActivity(intent);
            //finalizamos la actividad actual
            this.finish();
        }
        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_elegir_claves);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
