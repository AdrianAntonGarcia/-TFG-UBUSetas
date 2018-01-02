package ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.elegirclaves.ElegirClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

/**
 * Clase que implementa la funcionalidad relacionada
 * con mostrar la clave dicotómica seleccionada
 *
 * @author Adrián Antón García
 * @name ClaveDicotomica
 * @category clase
 */

public class ClaveDicotomica extends AppCompatActivity implements Serializable, AdapterView.OnItemClickListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = ClaveDicotomica.class.getSimpleName();

    //Estructura donde se almacenan las claves

    private TreeMap<String, ArrayList<Object>> claves;
    private AccesoDatosExternos acceso;

    //resultados obtenidos por el clasificador

    private ArrayList<String> resultados;

    //mapas de la clave actual

    private Map<String, ArrayList<String>> arbolNodos;
    private Map<String, ArrayList<String>> contenidoNodos;
    private Map<String, String> generosNodos;

    //Hijos del nodo seleccionado

    private ArrayList<String> hijosArbol;

    //Preguntas de los nodos hijos

    private ArrayList<String> preguntas;

    //Nodo padre actual

    private String nodoPadre;

    //Adaptador para el ListView

    private ArrayAdapter<String> adaptador;

    //Idioma de la aplicación

    private String idioma;

    //Configurador de la clave

    private String NOMBRECLAVE = "general";
    private ArrayList<String> generosRecibidos;
    private String NODOINICIAL = "1";

    //Elementos de la interfaz

    private ListView listViewClaveDicotomica;
    private TextView TextViewClaveMostrada;
    private TextView TextViewPreguntaClave;
    private FloatingActionButton boton_anterior;
    private FloatingActionButton boton_clave;

    //actividad de la que vengo, 1 actividad elegir clave, 2 actividad mostrar clavess

    private int actividadPrevia;
    private int vuelta;

    //Solución de la clave general.
    private String solucion;
    private Boolean mostrarClave;

    /**
     * Procedimiento que se ejecuta cuando se carga la clase, inicializa los elementos
     * y los relaciona con el contexto.
     *
     * @param Bundle, Bundle donde se guardan los datos cuando se cierra la actividad.
     * @name onCreate
     * @author Adrián Antón García
     * @category procedimiento
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave_dicotomica);

        mostrarClave = false;

        //Cargamos el idioma actual
        idioma = null;
        idioma = Locale.getDefault().getLanguage();


        //Inicializo los elementos de la interfaz

        TextViewClaveMostrada = (TextView) findViewById(R.id.TextView_ClaveMostrada);
        listViewClaveDicotomica = (ListView) findViewById(R.id.listView_claveDicotomica);
        TextViewPreguntaClave = (TextView) findViewById(R.id.TextView_Pregunta_clave);
        boton_anterior = (FloatingActionButton) findViewById(R.id.boton_anterior);
        boton_clave = (FloatingActionButton) findViewById(R.id.boton_clave_especifica);
        boton_anterior.setOnClickListener(this);
        boton_clave.setOnClickListener(this);
        listViewClaveDicotomica.setOnItemClickListener(this);
        listViewClaveDicotomica.setVisibility(View.VISIBLE);

        NODOINICIAL = "1";
        //Leo todas las claves

        acceso = new AccesoDatosExternos(this);


        //recojo el nombre de la clave a cargar y los resultados

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();

        //cargamos el idioma si se ha rotado la pantalla

        if (datosRecibidos.containsKey("idioma")) {
            idioma = datosRecibidos.getString("idioma");
        }
        if (datosRecibidos.containsKey("mostrarClave")) {
            mostrarClave = datosRecibidos.getBoolean("mostrarClave");
        }
        if (datosRecibidos.containsKey("actividadPrevia")) {
            actividadPrevia = datosRecibidos.getInt("actividadPrevia");
        }
        if (datosRecibidos.containsKey("resultados")) {
            resultados = datosRecibidos.getStringArrayList("resultados");
        }
        if (datosRecibidos.containsKey("nombreClave")) {
            NOMBRECLAVE = datosRecibidos.getString("nombreClave");
        }
        if (datosRecibidos.containsKey("generosMarcados")) {
            generosRecibidos = datosRecibidos.getStringArrayList("generosMarcados");
        }
        if (datosRecibidos.containsKey("vuelta")) {
            vuelta = datosRecibidos.getInt("vuelta");
        }
        if (mostrarClave) {
            boton_clave.setVisibility(View.VISIBLE);
        } else {
            boton_clave.setVisibility(View.GONE);
        }

        if (idioma.equals("es")) {
            claves = acceso.readFromFile();
        } else {
            claves = acceso.readFromFileEn();
        }


        //si no se ha especificado clave, se carga la ggeneral
        if (actividadPrevia == 1) {
            vuelta = 1;
            NOMBRECLAVE = "general";
            generosRecibidos = datosRecibidos.getStringArrayList("generosMarcados");
            Log.d("generosRecibidos", generosRecibidos.toString());
            if (generosRecibidos != null) {
                cargarClaveGeneral(generosRecibidos);
            }
        } else {
            //cargo la clave especifica pasada por paramatero
            vuelta = 2;
            cargarClaveEspecifica();
        }


        //restauro los elementos necesarios si se ha rotado la pantalla
        restaurarCampos(savedInstanceState);

        //Muestro la clave cargada
        TextViewClaveMostrada.setText(TextViewClaveMostrada.getText() + NOMBRECLAVE);

        //parte del menu lateral

        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_clave_dicotomica);
        setSupportActionBar(toolbar);

        //cargamos el layout del menu y lo inicializamos

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_clave_dicotomica);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d("ACTIVIDAD PREVIA", actividadPrevia + "");
    }

    /**
     * Procedimiento que carga la clave general desde el padre común a los géneros pasados como parametros.
     *
     * @param ArrayList<String>, géneros desde los que se va a filtrar la clave general.
     * @name cargarClaveGeneral
     * @author Adrián Antón García
     * @category procedimiento
     */

    public void cargarClaveGeneral(ArrayList<String> generos) {

        //Elijo la que se tiene que ejecutar

        ArrayList<Object> mapas = claves.get("general");

        //cargo los 3 mapas de la clave seleccionada

        arbolNodos = (Map<String, ArrayList<String>>) mapas.get(0);
        contenidoNodos = (Map<String, ArrayList<String>>) mapas.get(1);
        generosNodos = (Map<String, String>) mapas.get(2);

        //Creo los arrays que contienen la lista de padres de cada genero

        HashMap<String, ArrayList<String>> arrayMapas = new HashMap<String, ArrayList<String>>();


        //Cargo los arrays con sus padres

        for (String genero : generos) {
            ArrayList<String> nodosPadres = new ArrayList<>();
            String nodoPadre = generosNodos.get(genero);
            nodosPadres.add(nodoPadre);
            int contador = 1;
            while (!nodoPadre.equals("1")) {
                nodoPadre = contenidoNodos.get(nodoPadre).get(2);
                nodosPadres.add(nodoPadre);
            }
            arrayMapas.put(genero, nodosPadres);
        }

        //Ahora hay que seleccionar el padre comun mas bajo en el arbol

        //Saco el tamaño del array menor

        int tamMenor = 999;
        for (ArrayList<String> nodosPadres : arrayMapas.values()) {
            if (nodosPadres.size() < tamMenor) {
                tamMenor = nodosPadres.size();
            }
        }

        String nodoInicial = null;
        String nodoComprobar = null;
        String nodoActual = null;
        boolean cambio;
        for (int i = 0; i < tamMenor; ++i) {
            ArrayList<String> arrayPrimero = arrayMapas.get(generos.get(0));

            //Sacamos el ultimo padre menos la posicion

            nodoComprobar = arrayPrimero.get(arrayPrimero.size() - i - 1);
            cambio = true;

            //Si todos los ultimos nodos de los arrays coinciden, es un nodo padre comun

            for (ArrayList<String> array : arrayMapas.values()) {
                nodoActual = array.get(array.size() - i - 1);
                if (!nodoActual.equals(nodoComprobar)) {
                    cambio = false;
                }
            }
            if (cambio) {
                nodoInicial = nodoComprobar;
            }
        }


        NODOINICIAL = nodoInicial;
        NOMBRECLAVE = "general";

        //Cargamos la clave general desde el nuevo nodo inicial.

        cargarClaveEspecifica();
    }

    /**
     * Procedimiento que carga la clave dicotomica especificada en la variable NOMBREGENERAL.
     *
     * @name cargarClave
     * @author Adrián Antón García
     * @category procedimiento
     */

    public void cargarClaveEspecifica() {
        listViewClaveDicotomica.setVisibility(View.VISIBLE);
        //Elijo la que se tiene que ejecutar
        Log.d("NOMBRECLAVE", NOMBRECLAVE);
        ArrayList<Object> mapas = claves.get(NOMBRECLAVE);


        arbolNodos = (Map<String, ArrayList<String>>) mapas.get(0);
        contenidoNodos = (Map<String, ArrayList<String>>) mapas.get(1);
        generosNodos = (Map<String, String>) mapas.get(2);

        //cargo los 3 mapas de la clave seleccionada
        Log.d("arbolNodosKeys", arbolNodos.keySet().toString());
        Log.d("contenidoNodosKeys", contenidoNodos.keySet().toString());
        Log.d("generosNodosKeys", generosNodos.keySet().toString());
        //cargo los nodos hijos del nodo principal

        preguntas = new ArrayList<String>();
        preguntas.clear();
        hijosArbol = arbolNodos.get(NODOINICIAL);
        nodoPadre = NODOINICIAL;

        //cargo las preguntas de cada hijo

        for (String hijo : hijosArbol) {
            if (contenidoNodos.containsKey(hijo)) {
                if (contenidoNodos.get(hijo).get(0).toString().equals("null1") == false) {
                    preguntas.add(contenidoNodos.get(hijo).get(0));
                }
            }
        }

        //relleno la lista con las preguntas

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);
        listViewClaveDicotomica.setAdapter(adaptador);
    }

    /**
     * Procedimiento que se restaura el bitmap al girar la pantalla.
     *
     * @param Bundle, Bundle donde se guardan los datos cuando se cierra la actividad.
     * @name restaurarCampos
     * @author Adrián Antón García
     * @category procedimiento
     */

    private void restaurarCampos(Bundle savedInstanceState) {

        // Si hay algo en el bundle
        if (savedInstanceState != null) {
            idioma = savedInstanceState.getString("idioma");
            acceso.actualizarIdioma(idioma);
            //hay que actualizar al cambiar el idioma
            Intent intent = new Intent();
            intent.setClass(this, this.getClass());
            intent.putExtra("idioma", idioma);
            intent.putExtra("nombreClave", NOMBRECLAVE);
            intent.putExtra("mostrarClave", mostrarClave);
            intent.putExtra("actividadPrevia", actividadPrevia);
            intent.putExtra("generosMarcados", generosRecibidos);
            intent.putExtra("vuelta", vuelta);
            //llamamos a la actividad
            this.startActivity(intent);
            this.finish();
        }
    }

    /**
     * Procedimiento que se ejecuta cuando se destruye la actividad.
     *
     * @param Bundle, Bundle donde se guardan los datos cuando se cierra la actividad.
     * @name onSaveInstanceState
     * @author Adrián Antón García
     * @category procedimiento
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //guardo el idioma
        outState.putString("idioma", idioma);
    }

    /**
     * Procedimiento que se activa cuando se pulsa sobre un elemento de la lista.
     *
     * @param AdapterView<?>, Vista padre del elemento pulsado.
     * @param View,           Vista del elemento pulsado.
     * @param int,            posición en la lista del elemento pulsado.
     * @param id,             identificador del elemento pulsado.
     * @name onItemClick
     * @author Adrián Antón García
     * @category procedure
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //recojo el nodo pulsado

        String hijoPulsado = hijosArbol.get(position);
        nodoPadre = contenidoNodos.get(hijoPulsado).get(2);

        //Si el hijo pulsado tiene hijos, es decir, es un nodo intermedio

        if (arbolNodos.containsKey(hijoPulsado)) {
            listViewClaveDicotomica.setVisibility(View.VISIBLE);
            //recojo los hijos de ese nuevo nodo

            hijosArbol = arbolNodos.get(hijoPulsado);

            //recojo sus preguntas si el nodo no es una hoja

            preguntas.clear();
            for (String hijo : hijosArbol) {
                if (contenidoNodos.containsKey(hijo)) {
                    if (contenidoNodos.get(hijo).get(0).toString().equals("null1") == false) {
                        preguntas.add(contenidoNodos.get(hijo).get(0));
                    }
                }
            }

            //relleno la lista con las preguntas

            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);
            listViewClaveDicotomica.setAdapter(adaptador);

        } else {

            //si el hijo pulsado es una hoja

            preguntas.clear();
            solucion = contenidoNodos.get(hijoPulsado).get(3);
            preguntas.add(solucion);
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);

            listViewClaveDicotomica.setAdapter(adaptador);
            listViewClaveDicotomica.setVisibility(View.GONE);
            if (NOMBRECLAVE.equals("general")) {
                TextViewPreguntaClave.setText(getString(R.string.texto_solucion_genero) + " " + solucion);
                Toast.makeText(this, getString(R.string.texto_solucion_genero) + " " + solucion, Toast.LENGTH_LONG).show();

                String[] names = getResources().getStringArray(R.array.nombres_claves);
                if (Arrays.asList(names).contains(solucion.toLowerCase())) {
                    boton_clave.setVisibility(View.VISIBLE);
                    mostrarClave = true;
                }

            } else {
                TextViewPreguntaClave.setText(getString(R.string.texto_solucion_especie) + " " + solucion);
                Toast.makeText(this, getString(R.string.texto_solucion_especie) + " " + solucion, Toast.LENGTH_LONG).show();

            }
        }
    }

    /**
     * Procedimiento que se ejecuta cuando se pulsa sobre algún botón.
     *
     * @param View, Vista del botón pulsado.
     * @name onClick
     * @author Adrián Antón García
     * @category procedimiento
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton_anterior:

                //recojo los hijos de ese nuevo nodo

                hijosArbol = arbolNodos.get(nodoPadre);
                nodoPadre = contenidoNodos.get(nodoPadre).get(2);

                //recojo sus preguntas, si el nodo no es una hoja

                preguntas.clear();
                for (String hijo : hijosArbol) {
                    if (contenidoNodos.containsKey(hijo)) {
                        if (contenidoNodos.get(hijo).get(0).toString().equals("null1") == false) {
                            preguntas.add(contenidoNodos.get(hijo).get(0));
                        }
                    }
                }

                //relleno la lista con las preguntas

                adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);
                listViewClaveDicotomica.setAdapter(adaptador);
                listViewClaveDicotomica.setVisibility(View.VISIBLE);
                TextViewPreguntaClave.setText(getString(R.string.texto_pregunta));
                if (mostrarClave = true) {
                    boton_clave.setVisibility(View.GONE);
                    mostrarClave = false;
                }
                break;
            case R.id.boton_clave_especifica:
                String[] names = getResources().getStringArray(R.array.nombres_claves);
                if (Arrays.asList(names).contains(solucion.toLowerCase())) {
                    Toast.makeText(this, "Abriendo clave dicotómica del género: " + solucion, Toast.LENGTH_LONG).show();
                    Intent mostrarSeta = new Intent(this, ClaveDicotomica.class);
                    mostrarSeta.putExtra("nombreClave", solucion.toLowerCase());
                    actividadPrevia = 3;
                    mostrarSeta.putExtra("actividadPrevia", actividadPrevia);
                    this.startActivity(mostrarSeta);
                }
                break;
        }
    }

    /**
     * Método que es llamado para rellenar el menú superior
     *
     * @param Menu, El menú superior
     * @name onCreateOptionsMenu
     * @author Adrián Antón García
     * @category método
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;
    }

    /**
     * Método que es llamado cuando se pulsa algún elemento del menú superior
     *
     * @param MenuItem, el menu item
     * @name onOptionsItemSelected
     * @author Adrián Antón García
     * @category método
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //genero la ayuda de la actividad
        if (id == R.id.action_settings) {
            final Dialog dialog = new Dialog(ClaveDicotomica.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_clave_dicotomica);
            dialog.setCancelable(true);
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Procedimiento que se ejectua cuando se pulsa el boton volver del movil.
     *
     * @name onCreate
     * @author Adrián Antón García
     * @category Procedimiento
     */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_clave_dicotomica);

        //si el menu esta abierto

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            //lo cerramos

            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Actividad elegir clave
            Log.d("ACTIVIDADPREVIABACK", actividadPrevia + "");
            switch (actividadPrevia) {
                case 1:
                    Intent intent = new Intent(ClaveDicotomica.this, ElegirClaves.class);
                    intent.putExtra("idioma", idioma);
                    intent.putExtra("resultados", resultados);
                    this.startActivity(intent);
                    //si el menu esta cerrado llamamos al constructor padre
                    finish();
                    break;
                case 2:
                    Intent intent2 = new Intent(ClaveDicotomica.this, MostrarClaves.class);
                    intent2.putExtra("idioma", idioma);
                    this.startActivity(intent2);
                    //si el menu esta cerrado llamamos al constructor padre
                    finish();
                    break;
                case 3:
                    Intent intent3 = new Intent(this, this.getClass());
                    if (vuelta == 1) {
                        actividadPrevia = 1;
                    } else {
                        actividadPrevia = 2;
                    }
                    intent3.putExtra("idioma", idioma);
                    intent3.putExtra("generosMarcados", generosRecibidos);
                    intent3.putExtra("actividadPrevia", actividadPrevia);
                    this.startActivity(intent3);
                    break;
            }
        }
    }

    /**
     * Método que se activa cuando pulsamos un botón del menú
     *
     * @param MenuItem, Item pulsado del menú.
     * @name onNavigationItemSelected
     * @author Adrián Antón García
     * @category Método
     * .
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_clasificar) {
            Intent cambioActividad = new Intent(ClaveDicotomica.this, RecogerFoto.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_ir_claves) {
            Intent cambioActividad = new Intent(ClaveDicotomica.this, MostrarClaves.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_informacion) {
            Intent cambioActividad = new Intent(ClaveDicotomica.this, MostrarSetas.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_home) {
            Intent cambioActividad = new Intent(ClaveDicotomica.this, Lanzadora.class);
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
            intent.putExtra("nombreClave", NOMBRECLAVE);
            intent.putExtra("idioma", idioma);
            intent.putExtra("mostrarClave", mostrarClave);
            intent.putExtra("actividadPrevia", actividadPrevia);
            intent.putExtra("generosMarcados", generosRecibidos);
            intent.putExtra("vuelta", vuelta);
            //llamamos a la actividad
            this.startActivity(intent);
            //finalizamos la actividad actual
            this.finish();
        } else if (id == R.id.menu_ayuda) {
            //genero la ayuda del menú lateral
            final Dialog dialog = new Dialog(ClaveDicotomica.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_menu);
            dialog.setCancelable(true);
            dialog.show();
        }

        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_clave_dicotomica);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
