package ubusetas.ubu.adrian.proyectoubusetas.informacion;

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
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.DBsetasManager;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

/*
* @name: DBsetasManager
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que muestra información relativa a la seta pulsada
* */

public class MostrarInformacionSeta extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private String nombreSeta;


    private String descripcionEs;
    private String comestibilidadEs;
    private String enlace;
    private String genero;


    //elementos
    private ImageView imageViewSetaDescrita;
    private TextView textViewTextoDescripcionSeta;
    private TextView textViewTextoGeneroSeta;
    private TextView textViewTextoComestibilidadSeta;
    private TextView textViewTextoEnlaceSeta;


    //Base de datos

    private DBsetasManager baseDatos;

    /*
    * @name: onCreate
    * @Author: Adrián Antón García
    * @category: procedure
    * @Description: Procedimiento que se ejecuta al iniciarse la actividad mostrarInformacionSeta,
    * inicializa todos las variables necesarias.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_informacion_seta);

        //inicializo los elements de la interfaz



        imageViewSetaDescrita = (ImageView) findViewById(R.id.imageView_setaDescrita);
        textViewTextoDescripcionSeta = (TextView) findViewById(R.id.textView_textoDescripcionSeta);
        textViewTextoGeneroSeta = (TextView) findViewById(R.id.textView_textoGeneroSeta);
        textViewTextoComestibilidadSeta = (TextView) findViewById(R.id.textView_textoComestibilidadSeta);
        textViewTextoEnlaceSeta = (TextView) findViewById(R.id.textView_textoEnlaceSeta);


        //recojo los datos provenientes de la actividad mostrar resultados

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();
        //recibo la información que llega de mostrar resultados
        nombreSeta= (String) datosRecibidos.get("nombreSeta");
        nombreSeta = nombreSeta.toLowerCase().trim();

        //Coloco la imagen de la seta en su imageview
        String path="imagenesSetas/" + nombreSeta.toLowerCase() + "/"+ nombreSeta.toLowerCase().trim() + " " + "(" + 1 + ")" + ".jpg";
        InputStream is = null;
        try {
            is = this.getResources().getAssets().open(path);
        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        Bitmap bit = BitmapFactory.decodeStream(is);
        imageViewSetaDescrita.setImageBitmap(bit);

        //Accedo a la base de datos

        baseDatos=new DBsetasManager(this);
        baseDatos.open();
        descripcionEs=baseDatos.getDescripcionEsp(nombreSeta);
        comestibilidadEs=baseDatos.getComestibilidadEs(nombreSeta);
        enlace = baseDatos.getEnlace(nombreSeta);
        genero = baseDatos.getGenero(nombreSeta);
        baseDatos.close();
        textViewTextoDescripcionSeta.setText(descripcionEs);
        textViewTextoGeneroSeta.setText(genero);
        textViewTextoComestibilidadSeta.setText(comestibilidadEs);
        textViewTextoEnlaceSeta.setText(enlace);

        //parte del menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_mostrar_informacion_setas);
        //cargamos la nueva barra
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



    //Al pulsar el boton de volver
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_informacion_setas);
        //si el menu esta abierto
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //lo cerramos
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //si el menu esta cerrado
            super.onBackPressed();
        }
    }
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
        }else if (id == R.id.menu_home) {
            Intent cambioActividad = new Intent(MostrarInformacionSeta.this, Lanzadora.class);
            startActivity(cambioActividad);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_informacion_setas);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
