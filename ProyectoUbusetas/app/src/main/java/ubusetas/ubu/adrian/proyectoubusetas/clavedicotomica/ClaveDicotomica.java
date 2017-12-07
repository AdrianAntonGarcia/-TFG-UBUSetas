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
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarResultados;

/*
* @name: ClaveDicotomica
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que implementa la funcionalidad relacionada
* con mostrar la clave dicotómica seleccionada
* */

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
    private FloatingActionButton boton_anterior;

    //actividad de la que vengo, 1 actividad elegir clave, 2 actividad mostrar clavess

    private int actividadPrevia;

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
        setContentView(R.layout.activity_clave_dicotomica);
        //Cargamos el idioma actual
        idioma = null;
        idioma = Locale.getDefault().getLanguage();


        //Inicializo los elementos de la interfaz

        TextViewClaveMostrada = (TextView) findViewById(R.id.TextView_ClaveMostrada);
        listViewClaveDicotomica = (ListView) findViewById(R.id.listView_claveDicotomica);
        boton_anterior = (FloatingActionButton) findViewById(R.id.boton_anterior);
        listViewClaveDicotomica.setOnItemClickListener(this);
        boton_anterior.setOnClickListener(this);

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

        if(idioma.equals("es")){
            claves = acceso.readFromFile();
        }else{
            claves = acceso.readFromFileEn();
        }

        resultados = datosRecibidos.getStringArrayList("resultados");
        NOMBRECLAVE = datosRecibidos.getString("nombreClave");
        //si no se ha especificado clave, se carga la ggeneral
        if (NOMBRECLAVE == null) {
            actividadPrevia=1;
            NOMBRECLAVE = "general";
            generosRecibidos = datosRecibidos.getStringArrayList("generosMarcados");
            Log.d("generosRecibidos", generosRecibidos.toString());
            if (generosRecibidos != null) {
                cargarClaveGeneral(generosRecibidos);
            }
        } else {
            actividadPrevia=2;
            //cargo la clave especifica pasada por paramatero
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
    }

    /*
    * @name: cargarClaveGeneral
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que carga la clave general desde el padre común a los géneros pasados como parametros.
    * @param: ArrayList<String>, géneros desde los que se va a filtrar la clave general.
    * */

    public void cargarClaveGeneral(ArrayList<String> generos) {

        //Elijo la que se tiene que ejecutar

        ArrayList<Object> mapas = claves.get("general");

        //cargo los 3 mapas de la clave seleccionada

        arbolNodos = (Map<String, ArrayList<String>>) mapas.get(0);
        contenidoNodos = (Map<String, ArrayList<String>>) mapas.get(1);
        generosNodos = (Map<String, String>) mapas.get(2);
        Log.d("arbolNodos", arbolNodos.toString());
        Log.d("contenidoNodos", contenidoNodos.toString());
        Log.d("generosNodos", generosNodos.toString());

        //Creo los arrays que contienen la lista de padres de cada genero

        HashMap<String, ArrayList<String>> arrayMapas = new HashMap<String, ArrayList<String>>();


        //Cargo los arrays con sus padres

        for (String genero : generos) {
            ArrayList<String> nodosPadres = new ArrayList<>();
            String nodoPadre = generosNodos.get(genero);
            nodosPadres.add(nodoPadre);
            int contador = 1;
            while (!nodoPadre.equals("1")) {
                Log.d("nodoPadre", nodoPadre);
                nodoPadre = contenidoNodos.get(nodoPadre).get(2);
                nodosPadres.add(nodoPadre);
            }
            arrayMapas.put(genero, nodosPadres);
        }
        Log.d("ARRAYS CARGADOS", arrayMapas.toString());

        //Ahora hay que seleccionar el padre comun mas bajo en el arbol

        //Saco el tamaño del array menor

        int tamMenor = 999;
        for (ArrayList<String> nodosPadres : arrayMapas.values()) {
            if (nodosPadres.size() < tamMenor) {
                tamMenor = nodosPadres.size();
            }
        }
        Log.d("TAM MENOR", "" + tamMenor);

        String nodoInicial = null;
        String nodoComprobar = null;
        String nodoActual = null;
        boolean cambio;
        for (int i = 0; i < tamMenor; ++i) {
            ArrayList<String> arrayPrimero = arrayMapas.get(generos.get(0));

            //Sacamos el ultimo padre menos pa posicion

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
        Log.d("NODO INICIAL", nodoInicial);
        NODOINICIAL = nodoInicial;
        NOMBRECLAVE = "general";

        //Cargamos la clave general desde el nuevo nodo inicial.

        cargarClaveEspecifica();
    }

    /*
    * @name: cargarClave
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que carga la clave dicotomica especificada en la variable NOMBREGENERAL.
    * */

    public void cargarClaveEspecifica() {

        //Elijo la que se tiene que ejecutar

        ArrayList<Object> mapas = claves.get(NOMBRECLAVE);

        //cargo los 3 mapas de la clave seleccionada

        arbolNodos = (Map<String, ArrayList<String>>) mapas.get(0);
        contenidoNodos = (Map<String, ArrayList<String>>) mapas.get(1);
        generosNodos = (Map<String, String>) mapas.get(2);

        //cargo los nodos hijos del nodo principal

        preguntas = new ArrayList<String>();
        preguntas.clear();
        hijosArbol = arbolNodos.get(NODOINICIAL);
        nodoPadre = NODOINICIAL;

        //cargo las preguntas de cada hijo

        for (String hijo : hijosArbol) {
            if (contenidoNodos.containsKey(hijo)) {
                preguntas.add(contenidoNodos.get(hijo).get(0));
            }
        }

        //relleno la lista con las preguntas

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);
        listViewClaveDicotomica.setAdapter(adaptador);
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
            intent.putExtra("nombreClave",NOMBRECLAVE);
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
    * @name: onItemClick
    * @Author: Adrián Antón García
    * @category: procedure
    * @Description: Procedimiento que se activa cuando se pulsa sobre un elemento de la lista.
    * @Param: AdapterView<?>, Vista padre del elemento pulsado.
    * @Param: View, Vista del elemento pulsado.
    * @Param: int, posición en la lista del elemento pulsado.
    * @Param: id, identificador del elemento pulsado.
    * */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //recojo el nodo pulsado

        String hijoPulsado = hijosArbol.get(position);
        nodoPadre = contenidoNodos.get(hijoPulsado).get(2);

        //Si el hijo pulsado tiene hijos, es decir, es un nodo intermedio

        if (arbolNodos.containsKey(hijoPulsado)) {

            //recojo los hijos de ese nuevo nodo

            hijosArbol = arbolNodos.get(hijoPulsado);

            //recojo sus preguntas si el nodo no es una hoja

            preguntas.clear();
            for (String hijo : hijosArbol) {
                preguntas.add(contenidoNodos.get(hijo).get(0));
            }

            //relleno la lista con las preguntas

            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);
            listViewClaveDicotomica.setAdapter(adaptador);

        } else {

            //si el hijo pulsado es una hoja

            preguntas.clear();
            String solucion = contenidoNodos.get(hijoPulsado).get(3);
            preguntas.add(solucion);
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);

            listViewClaveDicotomica.setAdapter(adaptador);
        }
    }

    /*
     * @name: onClick
     * @Author: Adrián Antón García
     * @category: procedimiento
     * @Description: Procedimiento que se ejecuta cuando se pulsa sobre algún botón.
     * @Param: View, Vista del botón pulsado.
     * */

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
                        preguntas.add(contenidoNodos.get(hijo).get(0));
                    }
                }

                //relleno la lista con las preguntas

                adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);
                listViewClaveDicotomica.setAdapter(adaptador);
                break;
        }
    }
    /*
    * @name: onCreateOptionsMenu
    * @Author: Adrián Antón García
    * @category: método
    * @Description: Método que es llamado para rellenar el menú superior
    * @param: Menu, El menú superior
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opciones, menu);
        return true;
    }
    /*
    * @name: onOptionsItemSelected
    * @Author: Adrián Antón García
    * @category: método
    * @Description: Método que es llamado cuando se pulsa algún elemento del menú superior
    * @param: MenuItem, el menu item
    * */

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

    /*
    * @name: onCreate
    * @Author: Adrián Antón García
    * @category: Procedimiento
    * @Description: Procedimiento que se ejectua cuando se pulsa el boton volver del movil.
    * */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_clave_dicotomica);

        //si el menu esta abierto

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            //lo cerramos

            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Actividad elegir clave
            if(actividadPrevia==1){
                Intent intent = new Intent(ClaveDicotomica.this,ElegirClaves.class);
                intent.putExtra("idioma", idioma);
                intent.putExtra("resultados",resultados);
                this.startActivity(intent);
                //si el menu esta cerrado llamamos al constructor padre
                finish();
            }else{
                Intent intent = new Intent(ClaveDicotomica.this,MostrarClaves.class);
                intent.putExtra("idioma", idioma);
                this.startActivity(intent);
                //si el menu esta cerrado llamamos al constructor padre
                finish();
            }
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
            if (Locale.getDefault().getLanguage().equals("es")){
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
            intent.putExtra("nombreClave",NOMBRECLAVE);
            intent.putExtra("idioma", idioma);
            //llamamos a la actividad
            this.startActivity(intent);
            //finalizamos la actividad actual
            this.finish();
        }else if( id == R.id.menu_ayuda){
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
