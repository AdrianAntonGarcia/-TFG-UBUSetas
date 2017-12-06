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

/*
* @name: MostrarComparativa
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que muestra la foto introducida por el usuario y la seleccionada
* */

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

    /*
    * @name: onClick
    * @Author: Adrián Antón García
    * @category: Procedimiento
    * @Description: Procedimiento que se ejectua cuando se pulsa un imageView.
    * @param: Vista del imageView pulsado
    * */

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
            intent.putExtra("foto_seta",imagenComparar);
            intent.putExtra("fotoBitmap",imagenUsuario);
            intent.putStringArrayListExtra("resultados",resultados);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_comparativa);
        //si el menu esta abierto
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //lo cerramos
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(MostrarComparativa.this,MostrarResultados.class);
            intent.putExtra("idioma", idioma);
            intent.putExtra("fotoBitmap",imagenUsuario);
            intent.putStringArrayListExtra("resultados",resultados);
            this.startActivity(intent);
            //si el menu esta cerrado llamamos al constructor padre
            finish();
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
            intent.putExtra("foto_seta",imagenComparar);
            intent.putExtra("fotoBitmap",imagenUsuario);
            intent.putStringArrayListExtra("resultados",resultados);
            //llamamos a la actividad
            this.startActivity(intent);
            //finalizamos la actividad actual
            this.finish();
        }
        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_comparativa);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
