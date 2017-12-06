package ubusetas.ubu.adrian.proyectoubusetas.lanzador;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Locale;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarComparativa;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.TouchImageView;

/*
* @name: Lanzadora
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que arranca la aplicación.
* */

public class Lanzadora extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //Elementos de la interfaz

    private FloatingActionButton botonClasificar;
    private FloatingActionButton botonIrSetas;
    private FloatingActionButton botonIrClaves;
    private AccesoDatosExternos acceso;

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
        setContentView(R.layout.activity_lanzadora);

        //Inicializamos el idioma de la aplicación
        idioma = null;
        idioma = Locale.getDefault().getLanguage();
        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();

        if (datosRecibidos != null) {
            idioma = datosRecibidos.getString("idioma");
        }

        acceso = new AccesoDatosExternos(this);
        //restauro los elementos necesarios
        restaurarCampos(savedInstanceState);
        //Inicialización elementos de la interfaz

        botonClasificar = (FloatingActionButton) findViewById(R.id.boton_clasificar_lanzadora);
        botonIrSetas = (FloatingActionButton) findViewById(R.id.boton_ir_setas_lanzadora);
        botonIrClaves = (FloatingActionButton) findViewById(R.id.boton_ir_claves_lanzadora);

        botonClasificar.setOnClickListener(this);
        botonIrSetas.setOnClickListener(this);
        botonIrClaves.setOnClickListener(this);

        //parte del menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_lanzadora);
        //cargamos la nueva barra
        setSupportActionBar(toolbar);

        //cargamos el layout del menu y lo inicializamos
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_lanzadora);
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
            final Dialog dialog = new Dialog(Lanzadora.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_lanzadora);
            dialog.setCancelable(true);
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
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
        Intent cambioActividad;
        switch (v.getId()) {
            case R.id.boton_clasificar_lanzadora:
                cambioActividad = new Intent(Lanzadora.this, RecogerFoto.class);
                startActivity(cambioActividad);
                break;
            case R.id.boton_ir_claves_lanzadora:
                cambioActividad = new Intent(Lanzadora.this, MostrarClaves.class);
                startActivity(cambioActividad);
                break;
            case R.id.boton_ir_setas_lanzadora:
                cambioActividad = new Intent(Lanzadora.this, MostrarSetas.class);
                startActivity(cambioActividad);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_lanzadora);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
        int id = item.getItemId();
        if (id == R.id.menu_clasificar) {
            Intent cambioActividad = new Intent(Lanzadora.this, RecogerFoto.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_ir_claves) {
            Intent cambioActividad = new Intent(Lanzadora.this, MostrarClaves.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_informacion) {
            Intent cambioActividad = new Intent(Lanzadora.this, MostrarSetas.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_idioma) {
            //recargo la actividad con el nuevo idioma
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
            //llamamos a la actividad
            this.startActivity(intent);
            this.finish();
        } else if( id == R.id.menu_ayuda){
            //genero la ayuda del menú lateral
            final Dialog dialog = new Dialog(Lanzadora.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_menu);
            dialog.setCancelable(true);
            dialog.show();
        }

        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_lanzadora);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
