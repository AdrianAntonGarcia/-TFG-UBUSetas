package ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.interfaces.MostrarResultados;

/*
* @name: ClaveDicotomica
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que iplementa la funcionalidad para visualizar las claves dicotomicas de las setas
* */

public class ClaveDicotomica extends AppCompatActivity implements Serializable, AdapterView.OnItemClickListener,View.OnClickListener {
    private static final String TAG = ClaveDicotomica.class.getSimpleName();

    //Estructura donde se almacenan las claves
    private TreeMap<String, ArrayList<Object>> claves;

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
    private static final String NODOINICIAL = "1";
    private static final String NOMBREFICHERO = "claves.dat";

    //Elementos que vienen de mostrar Resultados y que hay que devolver al volver
    private Bitmap bitmapImagen;
    private int posImagenSeta;
    private List<String> resultados;

    //Elementos de la interfaz
    private ListView listViewClaveDicotomica;
    private TextView TextViewClaveMostrada;
    private TextView TextViewPregunta;
    private TextView TextViewInformacionClave;
    private Button boton_volver_clave;
    private Button boton_anterior;

    //Generos obtenidos en los resultados
    private ArrayList<String> generosDeLaClaveGeneral;
    private ArrayList<String> nombresSetasResultados;
    private String generoActual="general";

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

        NOMBRECLAVE="general";

        //recojo los elementos que tengo que devolver a la actividad mostrarResultados al volver

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();

        bitmapImagen = (Bitmap) datosRecibidos.get("fotoBitmap");
        posImagenSeta = (int) datosRecibidos.get("posImagenSeta");
        resultados = (List<String>) datosRecibidos.get("resultados");

        //Recojo los nombres de las setas resultados del clasificador

        nombresSetasResultados = (ArrayList<String>) datosRecibidos.get("nombresSetas");

        //Inicializo los elementos de la interfaz

        TextViewClaveMostrada =(TextView) findViewById(R.id.TextView_ClaveMostrada);
        TextViewPregunta = (TextView) findViewById(R.id.TextView_Pregunta);
        TextViewInformacionClave=(TextView) findViewById(R.id.TextView_InformacionClave);
        listViewClaveDicotomica = (ListView) findViewById(R.id.listView_claveDicotomica);
        boton_volver_clave=(Button) findViewById(R.id.boton_volver_clave);
        boton_anterior=(Button) findViewById(R.id.boton_anterior);
        listViewClaveDicotomica.setOnItemClickListener(this);
        boton_volver_clave.setOnClickListener(this);
        boton_anterior.setOnClickListener(this);

        //Leo todas las claves
        claves = readFromFile(this, NOMBREFICHERO);

        //cargo los generos recogidos por la clave
        generosDeLaClaveGeneral=new ArrayList<String>();
        for(String g:claves.keySet()){
            generosDeLaClaveGeneral.add(g);
        }
        //Saco el genero del primer resultado
        generoActual=nombresSetasResultados.get(0).split(" ")[0].trim();


        //cargo la clave
        ArrayList<String> generosDeLaClaveGeneral=new ArrayList<>();

        comprobarGenero(generoActual);
        cargarClave();
        TextViewClaveMostrada.setText("Clave: "+NOMBRECLAVE +"Genero:"+generoActual);
    }



    public void comprobarGenero(String generoComprobar){
        if(generosDeLaClaveGeneral.contains(generoComprobar)){
            TextViewInformacionClave.setText("La clave dicotomica general contempla este genero. ");
            Log.d(TAG,"CODIGO DE LA CLAVE: "+claves.get(generoComprobar));
            if(claves.get(generoComprobar).get(0).equals("vacio")==false){
                TextViewInformacionClave.setText(TextViewInformacionClave.getText()+"Además existe una clave especifica para ese genero");
                NOMBRECLAVE=generoActual;
                TextViewClaveMostrada.setText("Clave: "+NOMBRECLAVE);
            }else{
                TextViewInformacionClave.setText(TextViewInformacionClave.getText()+"No existe una clave especifica para ese genero");
            }
        }else{
            TextViewInformacionClave.setText("La clave dicotomica no contiene el genero clasificado");
        }
    }

    /*
    * @name: cargarClave
    * @Author: Adrián Antón García
    * @category: procedure
    * @Description: Procedimiento que carga la clave dicotomica
    * */

    public void cargarClave() {
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
        nodoPadre=NODOINICIAL;
        //cargo las preguntas de cada hijo

        for (String hijo : hijosArbol) {
            if(contenidoNodos.containsKey(hijo)) {
                preguntas.add(contenidoNodos.get(hijo).get(0));
            }
        }

        //relleno la lista con las preguntas

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);
        listViewClaveDicotomica.setAdapter(adaptador);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //recojo el nodo pulsado

        String hijoPulsado = hijosArbol.get(position);
        nodoPadre=contenidoNodos.get(hijoPulsado).get(2);

        Toast.makeText(this, hijoPulsado, Toast.LENGTH_LONG).show();
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
      * @name: readFromFile
      * @Author: Adrián Antón García
      * @category: Metodo
      * @Description: Metodo que lee la estructura de las claves del archivo claves.dat
      * */

    public static TreeMap<String, ArrayList<Object>> readFromFile(Context context, String nombreClave) {
        TreeMap<String, ArrayList<Object>> createResumeForm = null;
        try {
            InputStream fileInputStream = context.getAssets().open("claves/" + nombreClave);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            createResumeForm = (TreeMap<String, ArrayList<Object>>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return createResumeForm;
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
            case R.id.boton_volver_clave:
                //envio todos los elementos necesarios para que la actividad mostrar resultados se inicialice correctamente
                Intent mostrarResultados = new Intent(ClaveDicotomica.this, MostrarResultados.class);
                mostrarResultados.putStringArrayListExtra("resultados", (ArrayList<String>) resultados);
                mostrarResultados.putExtra("fotoBitmap", bitmapImagen);
                mostrarResultados.putExtra("posImagenSeta", posImagenSeta);
                finish();
                startActivity(mostrarResultados);
                break;
            case R.id.boton_anterior:
                //recojo los hijos de ese nuevo nodo
                hijosArbol = arbolNodos.get(nodoPadre);
                nodoPadre = contenidoNodos.get(nodoPadre).get(2);

                //recojo sus preguntas, si el nodo no es una hoja
                preguntas.clear();
                for (String hijo : hijosArbol) {
                    if(contenidoNodos.containsKey(hijo)) {
                        preguntas.add(contenidoNodos.get(hijo).get(0));
                    }
                }
                //relleno la lista con las preguntas
                adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preguntas);
                listViewClaveDicotomica.setAdapter(adaptador);
                break;
        }
    }
}
