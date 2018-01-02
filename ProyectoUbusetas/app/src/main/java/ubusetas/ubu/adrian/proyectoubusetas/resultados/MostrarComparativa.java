package ubusetas.ubu.adrian.proyectoubusetas.resultados;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Locale;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

/**
 * Clase que muestra la foto introducida por el usuario y la seleccionada
 *
 * @author Adrián Antón García
 * @name MostrarComparativa
 * @category clase
 */

public class MostrarComparativa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ImageView.OnClickListener {

    private AccesoDatosExternos acceso;

    //Elementos de la interfaz

    private ImageView imagenArriba;
    private ImageView imagenAbajo;

    private Bitmap imagenUsuario;
    private Bitmap imagenComparar;


    //Idioma de la aplicación
    private String idioma;

    //resultados clasificados
    ArrayList<String> resultados;

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
        setContentView(R.layout.activity_mostrar_comparativa);

        //Inicializamos el idioma de la aplicación
        idioma = null;
        idioma = Locale.getDefault().getLanguage();
        acceso = new AccesoDatosExternos(this);

        //Elementos de la interfaz
        imagenArriba = (ImageView) findViewById(R.id.ImageView_Comparativa_1);
        imagenAbajo = (ImageView) findViewById(R.id.ImageView_Comparativa_2);

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();

        //recibo la información que llega de mostrar resultados

        imagenComparar = (Bitmap) datosRecibidos.get("foto_seta");
        imagenUsuario = (Bitmap) datosRecibidos.get("fotoBitmap");
        resultados = (ArrayList<String>) datosRecibidos.get("resultados");

        //cargamos el idioma si se ha rotado la pantalla

        if (datosRecibidos.containsKey("idioma")) {
            idioma = datosRecibidos.getString("idioma");
        }

        //restauro los elementos necesarios si se ha rotado la pantalla
        restaurarCampos(savedInstanceState);

        //Asocio las imagenes

        imagenArriba.setImageBitmap(imagenComparar);
        imagenAbajo.setImageBitmap(imagenUsuario);
        imagenArriba.setOnClickListener(this);
        imagenAbajo.setOnClickListener(this);

        //parte del menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_mostrar_comparativa);
        //cargamos la nueva barra
        setSupportActionBar(toolbar);

        //cargamos el layout del menu y lo inicializamos
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_comparativa);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Procedimiento que se ejectua cuando se pulsa un imageView.
     *
     * @param Vista del imageView pulsado
     * @name onClick
     * @author Adrián Antón García
     * @category Procedimiento
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ImageView_Comparativa_1:
                final Dialog dialog = new Dialog(MostrarComparativa.this);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dialog.setContentView(R.layout.layout_full_image);
                TouchImageView bmImage = (TouchImageView) dialog.findViewById(R.id.img_receipt);
                bmImage.setImageBitmap(imagenComparar.copy(imagenComparar.getConfig(), true));
                bmImage.destroyDrawingCache();
                dialog.setCancelable(true);
                dialog.show();
                break;
            case R.id.ImageView_Comparativa_2:
                final Dialog dialogo = new Dialog(MostrarComparativa.this);
                dialogo.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dialogo.setContentView(R.layout.layout_full_image);
                TouchImageView bmImagen = (TouchImageView) dialogo.findViewById(R.id.img_receipt);
                bmImagen.setImageBitmap(imagenUsuario.copy(imagenUsuario.getConfig(), true));
                bmImagen.destroyDrawingCache();
                dialogo.setCancelable(true);
                dialogo.show();
                break;
        }
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
            intent.putExtra("foto_seta", imagenComparar);
            intent.putExtra("fotoBitmap", imagenUsuario);
            intent.putStringArrayListExtra("resultados", resultados);
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
            final Dialog dialog = new Dialog(MostrarComparativa.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_mostrar_comparativa);
            dialog.setCancelable(true);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Procedimiento que se ejectua cuando se pulsa el boton volver del movil.
     *
     * @name onBackPressed
     * @author Adrián Antón García
     * @category Procedimiento
     */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_comparativa);
        //si el menu esta abierto
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //lo cerramos
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(MostrarComparativa.this, MostrarResultados.class);
            intent.putExtra("idioma", idioma);
            intent.putExtra("fotoBitmap", imagenUsuario);
            intent.putStringArrayListExtra("resultados", resultados);
            this.startActivity(intent);
            //si el menu esta cerrado llamamos al constructor padre
            finish();
        }
    }

    /**
     * Método que se activa cuando pulsamos un botón del menú.
     *
     * @param MenuItem, Item pulsado del menú.
     * @name onNavigationItemSelected
     * @author Adrián Antón García
     * @category Método
     */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_clasificar) {
            Intent cambioActividad = new Intent(MostrarComparativa.this, RecogerFoto.class);
            startActivity(cambioActividad);

        } else if (id == R.id.menu_ir_claves) {
            Intent cambioActividad = new Intent(MostrarComparativa.this, MostrarClaves.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_informacion) {
            Intent cambioActividad = new Intent(MostrarComparativa.this, MostrarSetas.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_home) {
            Intent cambioActividad = new Intent(MostrarComparativa.this, Lanzadora.class);
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
            intent.putExtra("foto_seta", imagenComparar);
            intent.putExtra("fotoBitmap", imagenUsuario);
            intent.putStringArrayListExtra("resultados", resultados);
            //llamamos a la actividad
            this.startActivity(intent);
            //finalizamos la actividad actual
            this.finish();
        } else if (id == R.id.menu_ayuda) {
            //genero la ayuda del menú lateral
            final Dialog dialog = new Dialog(MostrarComparativa.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_menu);
            dialog.setCancelable(true);
            dialog.show();
        }

        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_comparativa);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
