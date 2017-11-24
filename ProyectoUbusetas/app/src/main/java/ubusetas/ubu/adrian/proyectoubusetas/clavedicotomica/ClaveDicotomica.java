package ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica;

import android.content.Context;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

/*
* @name: ClaveDicotomica
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que iplementa la funcionalidad para visualizar las claves dicotomicas de las setas
* */

public class ClaveDicotomica extends AppCompatActivity implements Serializable, AdapterView.OnItemClickListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = ClaveDicotomica.class.getSimpleName();

    //Estructura donde se almacenan las claves
    private TreeMap<String, ArrayList<Object>> claves;
    AccesoDatosExternos acceso;

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

    //Configurador de la clave
    //Clave por defecto
    private static String NOMBRECLAVE = "general";
    private ArrayList<String> generosRecibidos;
    private static String NODOINICIAL = "1";
    //private static final String NOMBREFICHERO = "claves.dat";

    //Elementos de la interfaz
    private ListView listViewClaveDicotomica;
    private TextView TextViewClaveMostrada;
    private FloatingActionButton boton_anterior;

    //Generos obtenidos en los resultados
    private ArrayList<String> generosDeLaClaveGeneral;


    /*
   * @name: onCreate
   * @Author: Adrián Antón García
   * @category: procedure
   * @Description: Metodo que se ejecuta cuando se inicia la actividad ClaveDicotomica
   * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave_dicotomica);

        //Inicializo el nombre de la clave

        NOMBRECLAVE = "";

        //Inicializo los elementos de la interfaz

        TextViewClaveMostrada = (TextView) findViewById(R.id.TextView_ClaveMostrada);
        listViewClaveDicotomica = (ListView) findViewById(R.id.listView_claveDicotomica);
        boton_anterior = (FloatingActionButton) findViewById(R.id.boton_anterior);
        listViewClaveDicotomica.setOnItemClickListener(this);
        boton_anterior.setOnClickListener(this);

        //Leo todas las claves
        acceso = new AccesoDatosExternos(this);
        claves = acceso.readFromFile();

        //recojo el nombre de la clave a cargar

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();
        NOMBRECLAVE = datosRecibidos.getString("nombreClave");
        if (NOMBRECLAVE == null) {
            NOMBRECLAVE = "general";
            generosRecibidos = datosRecibidos.getStringArrayList("generosMarcados");
            Log.d("generosRecibidos",generosRecibidos.toString());
            if (generosRecibidos != null) {
                cargarClaveGeneral(generosRecibidos);
            }
        }else{
            //cargo la clave especifica pasada por paramatero
            cargarClaveEspecifica();
        }

        TextViewClaveMostrada.setText("Clave: " + NOMBRECLAVE);

        //parte del menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_clave_dicotomica);
        //cargamos la nueva barra
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
    * @Description: Procedimiento que carga la clave general desde el padre común a los géneros pasados como parametros
    * */

    public void cargarClaveGeneral(ArrayList<String> generos) {
        //Elijo la que se tiene que ejecutar
        ArrayList<Object> mapas = claves.get("general");

        //cargo los 3 mapas de la clave seleccionada

        arbolNodos = (Map<String, ArrayList<String>>) mapas.get(0);
        contenidoNodos = (Map<String, ArrayList<String>>) mapas.get(1);
        generosNodos = (Map<String, String>) mapas.get(2);
        Log.d("arbolNodos",arbolNodos.toString());
        Log.d("contenidoNodos",contenidoNodos.toString());
        Log.d("generosNodos",generosNodos.toString());

        //Creo los arrays que contienen la lista de padres de cada genero

        HashMap<String, ArrayList<String>> arrayMapas = new HashMap<String, ArrayList<String>>();


        //Cargo los arrays con sus padres

        for(String genero : generos){
            ArrayList<String> nodosPadres = new ArrayList<>();
            String nodoPadre=generosNodos.get(genero);
            nodosPadres.add(nodoPadre);
            int contador = 1;
            while(!nodoPadre.equals("1")){
                Log.d("nodoPadre",nodoPadre);
                nodoPadre=contenidoNodos.get(nodoPadre).get(2);
                nodosPadres.add(nodoPadre);
            }
            arrayMapas.put(genero,nodosPadres);
        }
        Log.d("ARRAYS CARGADOS",arrayMapas.toString());

        //Ahora hay que seleccionar el padre comun mas bajo en el arbol

        //Saco el tamaño del array menor
        int tamMenor=999;
        for(ArrayList<String> nodosPadres : arrayMapas.values()){
            if (nodosPadres.size()<tamMenor){
                tamMenor=nodosPadres.size();
            }
        }
        Log.d("TAM MENOR",""+tamMenor);

        String nodoInicial=null;
        String nodoComprobar=null;
        String nodoActual=null;
        boolean cambio;
        for(int i=0;i<tamMenor;++i){
            ArrayList<String> arrayPrimero=arrayMapas.get(generos.get(0));
            //Sacamos el ultimo padre menos pa posicion
            nodoComprobar=arrayPrimero.get(arrayPrimero.size()-i-1);
            cambio=true;
            //Si todos los ultimos nodos de los arrays coinciden, es un nodo padre comun
            for(ArrayList<String> array:arrayMapas.values()){
                nodoActual=array.get(array.size()-i-1);
                if(!nodoActual.equals(nodoComprobar)){
                    cambio=false;
                }
            }
            if(cambio){
                nodoInicial=nodoComprobar;
            }
        }
        Log.d("NODO INICIAL",nodoInicial);
        NODOINICIAL=nodoInicial;
        NOMBRECLAVE="general";
        cargarClaveEspecifica();
    }
    /*
    * @name: cargarClave
    * @Author: Adrián Antón García
    * @category: procedure
    * @Description: Procedimiento que carga la clave dicotomica
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
    * @name: onItemClick
    * @Author: Adrián Antón García
    * @category: procedure
    * @Description: Procedimiento que se activa cuando se pulsa sobre un elemento de la lista
    * */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //recojo el nodo pulsado

        String hijoPulsado = hijosArbol.get(position);
        nodoPadre = contenidoNodos.get(hijoPulsado).get(2);

        //Toast.makeText(this, hijoPulsado, Toast.LENGTH_LONG).show();
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

            //si el hijo pulsado es una hoja
        } else {
            preguntas.clear();
            String solucion = contenidoNodos.get(hijoPulsado).get(3);
            preguntas.add(solucion);
            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);

            listViewClaveDicotomica.setAdapter(adaptador);
        }
    }



    /*
     * @name: onClickClick
     * @Author: Adrián Antón García
     * @category: procedure
     * @Description: Procedimiento que se ejecuta cuando se clica sobre algún boton
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
        }
        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_clave_dicotomica);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
