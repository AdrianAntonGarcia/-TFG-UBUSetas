package ubusetas.ubu.adrian.proyectoubusetas.informacion;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.DBsetasManager;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarResultados;

/*
* @name: DBsetasManager
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que muestra información relativa a la seta pulsada
* */

public class MostrarInformacionSeta extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AccesoDatosExternos acceso;

    private String nombreSeta;
    private String descripcionEs;
    private String comestibilidadEs;
    private String descripcionEn;
    private String comestibilidadEn;
    private String enlace;
    private String genero;

    //Idioma de la aplicación
    private String idioma;

    //elementos de la interfaz

    private ImageView imageViewSetaDescrita;
    private TextView textViewTextoDescripcionSeta;
    private TextView textViewTextoGeneroSeta;
    private TextView textViewTextoComestibilidadSeta;
    private TextView textViewTextoEnlaceSeta;

    //Base de datos

    private DBsetasManager baseDatos;

    private Bitmap bitmapUsuario;
    private ArrayList<String> resultados;

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
        setContentView(R.layout.activity_mostrar_informacion_seta);
        //Cargamos el idioma si se rota la pantalla
        idioma = null;
        idioma = Locale.getDefault().getLanguage();
        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();

        if (datosRecibidos.containsKey("idioma")) {
            idioma = datosRecibidos.getString("idioma");
        }

        actividadPrevia = 0;
        if (datosRecibidos.containsKey("actMostrarResultados")) {
            actividadPrevia = datosRecibidos.getInt("actMostrarResultados");
            bitmapUsuario = (Bitmap) datosRecibidos.get("fotoBitmap");
            resultados = (ArrayList<String>) datosRecibidos.get("resultados");
        }
        acceso = new AccesoDatosExternos(this);
        //inicializo los elements de la interfaz

        imageViewSetaDescrita = (ImageView) findViewById(R.id.imageView_setaDescrita);
        textViewTextoDescripcionSeta = (TextView) findViewById(R.id.textView_textoDescripcionSeta);
        textViewTextoGeneroSeta = (TextView) findViewById(R.id.textView_textoGeneroSeta);
        textViewTextoComestibilidadSeta = (TextView) findViewById(R.id.textView_textoComestibilidadSeta);
        textViewTextoEnlaceSeta = (TextView) findViewById(R.id.textView_textoEnlaceSeta);
        textViewTextoEnlaceSeta.setMovementMethod(LinkMovementMethod.getInstance());
        //recibo la información que llega de mostrar resultados

        nombreSeta = (String) datosRecibidos.get("nombreSeta");
        nombreSeta = nombreSeta.toLowerCase().trim();

        //restauro los elementos necesarios
        restaurarCampos(savedInstanceState);

        //Coloco la imagen de la seta en su imageview

        String path = "imagenesSetas/" + nombreSeta.toLowerCase() + "/" + nombreSeta.toLowerCase().trim() + " " + "(" + 1 + ")" + ".jpg";
        InputStream is = null;
        try {
            is = this.getResources().getAssets().open(path);
        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        Bitmap bit = BitmapFactory.decodeStream(is);
        imageViewSetaDescrita.setImageBitmap(bit);

        //Accedo a la base de datos y muestro la información

        baseDatos = new DBsetasManager(this);
        baseDatos.open();
        descripcionEs = baseDatos.getDescripcionEsp(nombreSeta);
        comestibilidadEs = baseDatos.getComestibilidadEs(nombreSeta);
        descripcionEn = baseDatos.getDescripcionEn(nombreSeta);
        comestibilidadEn = baseDatos.getComestibilidadEn(nombreSeta);
        enlace = baseDatos.getEnlace(nombreSeta);
        genero = baseDatos.getGenero(nombreSeta);
        baseDatos.close();
        if (idioma.equals("es")) {
            textViewTextoDescripcionSeta.setText(descripcionEs);
            textViewTextoComestibilidadSeta.setText(comestibilidadEs);
        } else {
            textViewTextoDescripcionSeta.setText(descripcionEn);
            textViewTextoComestibilidadSeta.setText(comestibilidadEn);
        }
        textViewTextoGeneroSeta.setText(genero);

        textViewTextoEnlaceSeta.setText(enlace);

        //parte del menu lateral

        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_mostrar_informacion_setas);
        setSupportActionBar(toolbar);

        //cargamos el layout del menu y lo inicializamos
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_informacion_setas);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            intent.putExtra("nombreSeta", nombreSeta);
            //si la actividad previa era mostrar resultados añadimos también estos campos
            if (actividadPrevia == 1) {
                intent.putExtra("actMostrarResultados", actividadPrevia);
                intent.putExtra("fotoBitmap", bitmapUsuario);
                intent.putStringArrayListExtra("resultados", resultados);
            }
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
            final Dialog dialog = new Dialog(MostrarInformacionSeta.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_mostrar_informacion);
            dialog.setCancelable(true);
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * @name: onBackPressed
    * @Author: Adrián Antón García
    * @category: Procedimiento
    * @Description: Procedimiento que se ejectua cuando se pulsa el boton volver del movil.
    * */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_informacion_setas);
        //si el menu esta abierto
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //lo cerramos
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //si la actividad previa era mostrar setas
            if (actividadPrevia == 0) {
                Intent intent = new Intent(MostrarInformacionSeta.this, MostrarSetas.class);
                intent.putExtra("idioma", idioma);
                this.startActivity(intent);
                //si el menu esta cerrado llamamos al constructor padre
                finish();
            } else {
                //si la actividad previa era mostrar resultados
                Intent intent = new Intent(MostrarInformacionSeta.this, MostrarResultados.class);
                intent.putExtra("idioma", idioma);
                intent.putStringArrayListExtra("resultados", resultados);
                intent.putExtra("fotoBitmap", bitmapUsuario);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_clasificar) {
            Intent cambioActividad = new Intent(MostrarInformacionSeta.this, RecogerFoto.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_ir_claves) {
            Intent cambioActividad = new Intent(MostrarInformacionSeta.this, MostrarClaves.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_informacion) {
            Intent cambioActividad = new Intent(MostrarInformacionSeta.this, MostrarSetas.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_home) {
            Intent cambioActividad = new Intent(MostrarInformacionSeta.this, Lanzadora.class);
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
            intent.putExtra("nombreSeta", nombreSeta);
            //si la actividad previa era mostrar resultados añadimos también estos campos
            if (actividadPrevia == 1) {
                intent.putExtra("actMostrarResultados", actividadPrevia);
                intent.putExtra("fotoBitmap", bitmapUsuario);
                intent.putStringArrayListExtra("resultados", resultados);
            }
            //llamamos a la actividad
            this.startActivity(intent);
            //finalizamos la actividad actual
            this.finish();
        } else if (id == R.id.menu_ayuda) {
            //genero la ayuda del menú lateral
            final Dialog dialog = new Dialog(MostrarInformacionSeta.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_menu);
            dialog.setCancelable(true);
            dialog.show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_informacion_setas);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
