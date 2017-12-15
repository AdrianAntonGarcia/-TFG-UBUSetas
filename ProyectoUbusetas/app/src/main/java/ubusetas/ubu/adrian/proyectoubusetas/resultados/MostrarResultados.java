package ubusetas.ubu.adrian.proyectoubusetas.resultados;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.elegirclaves.ElegirClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;
import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

/*
* @name: MostrarResultados
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que iplementa la funcionalidad de la actividad que muestra los resultados obtenidos tras
* clasificar una foto
* */

public class MostrarResultados extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, NavigationView.OnNavigationItemSelectedListener {

    private AccesoDatosExternos acceso;
    //widgets
    //ImageView imageViewImagenResultado1;

    FloatingActionButton botonRefrescarResultados;
    FloatingActionButton boton_clave;
    ListView listViewListaResultados;
    //lista de las especies clasificadas para esa foto
    public ArrayList<String> resultados;
    //lista de las especies clasificadas para esa foto sin id
    public List<String> resultadosSinNum;
    //lista con los nombres de las carpetas de las especies clasificadas para esa foto
    public List<String> nombresSetas;
    public List<SetasLista> listaSetas;
    public Bitmap bitmapImagen;
    //Idioma de la aplicación
    private String idioma;
    //Paramatro que indica la foto cargada
    public int posImagenSeta = 1;

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
        setContentView(R.layout.activity_mostrar_resultados);

        acceso = new AccesoDatosExternos(this);

        //inicializo los widgets

        botonRefrescarResultados = (FloatingActionButton) findViewById(R.id.boton_refrescar_resultados);
        boton_clave = (FloatingActionButton) findViewById(R.id.boton_clave);
        botonRefrescarResultados.setOnClickListener(this);
        boton_clave.setOnClickListener(this);
        listViewListaResultados = (ListView) findViewById(R.id.listView_lista_resultados);

        //inicializo las listas

        resultadosSinNum = new ArrayList<String>();
        nombresSetas = new ArrayList<String>();

        //recojo los datos provenientes de la actividad principal

        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();
        idioma = null;
        idioma = Locale.getDefault().getLanguage();
        //cargamos el idioma si se ha rotado la pantalla
        if (datosRecibidos.containsKey("idioma")) {
            idioma = datosRecibidos.getString("idioma");
        }
        //cargo la imágen introducida por el usuario

        bitmapImagen = (Bitmap) datosRecibidos.get("fotoBitmap");

        //cargo los resultados obtenidos en las listas

        resultados = (ArrayList<String>) datosRecibidos.get("resultados");

        //restauro los elementos necesarios si se ha rotado la pantalla
        restaurarCampos(savedInstanceState);
        //cargo las listas de resultados
        Log.d("RESULTADOS--------", "resultados" + resultados.toString());
        cargarListas(resultados);
        //cargo el listview
        inflarLista();

        //activo que la lista este pendiente de ser pulsada y de pulsaciones largas
        listViewListaResultados.setOnItemClickListener(this);
        listViewListaResultados.setOnItemLongClickListener(this);

        //parte del menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_mostrar_resultados);
        //cargamos la nueva barra
        setSupportActionBar(toolbar);

        //cargamos el layout del menu y lo inicializamos
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_resultados);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.d("resultadosSinNum", "resultadosSinNum" + resultadosSinNum.toString());
        Log.d("nombresSetas", "nombresSetas" + nombresSetas.toString());
    }

    /*
     * @name: cargarListas
     * @Author: Adrián Antón García
     * @category: procedimiento
     * @Description: Procedimiento que carga las diferentes listas con los resultados recibidos.
     * @param: List<String>, resultados a cargar en las listas.
     * */

    public void cargarListas(List<String> res) {
        for (String e : res) {

            //Elimino el identificador de especie

            e = e.split("]")[1];
            e = e.trim();
            resultadosSinNum.add(e);

            //elimino el porcentaje de acierto

            e = e.split("\\(")[0];
            e.trim();
            nombresSetas.add(e);
        }
    }

    /*
     * @name: cargarListaElementos
     * @Author: Adrián Antón García
     * @category: procedure
     * @Description: Metodo que carga la lista que se le va a pasar al adaptador para que cargue los resultados
     * en el listView, usa el numFoto para cargar la imagen correspondiente al número.
     * @param: int, número de la foto a cargar (1,2,3,4,5)
     * */

    public void cargarListaElementos(int numFoto) {
        listaSetas = new ArrayList<>();
        int i = 0;
        for (String nombre : resultadosSinNum) {
            //path + nombre
            listaSetas.add(new SetasLista("imagenesSetas/" + nombresSetas.get(i).trim() + "/" + nombresSetas.get(i) + "(" + numFoto + ")" + ".jpg", nombre));
            i++;
        }
    }

    /*
     * @name: inflarLista
     * @Author: Adrián Antón García
     * @category: procedimiento
     * @Description: Procedimiento que carga los resultados en la lista
     * */

    public void inflarLista() {
        //cargamos la la lista con el número de foto correspondiente
        cargarListaElementos(posImagenSeta);
        AdapatadorSetasLista adaptador = new AdapatadorSetasLista(this, R.layout.listview_item_row, listaSetas);
        listViewListaResultados.destroyDrawingCache();
        listViewListaResultados.setAdapter(adaptador);
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
     * @name: onItemClick
     * @Author: Adrián Antón García
     * @category: procedimiento
     * @Description: Procedimiento que se ejecuta cuando se clica sobre algún elemento del listView.
     * @Param: AdapterView<?>, Vista padre del elemento pulsado.
     * @Param: View, Vista del elemento pulsado.
     * @Param: int, posición en la lista del elemento pulsado.
     * @Param: id, identificador del elemento pulsado.
     * */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //busco el icono de la imagen pulsada
        String path = listaSetas.get(position).path;
        //cargo la imagen asociada a ese path
        if (path != null) {
            InputStream is = null;
            try {
                is = this.getResources().getAssets().open(path);
            } catch (IOException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
            Bitmap bit = BitmapFactory.decodeStream(is);
            if (bit != null) {
                //asocio el bitmap al imageview
                Intent mostrarComparativa = new Intent(MostrarResultados.this, MostrarComparativa.class);
                mostrarComparativa.putExtra("fotoBitmap", bitmapImagen);
                mostrarComparativa.putExtra("foto_seta", bit);
                mostrarComparativa.putStringArrayListExtra("resultadosAdevolver", resultados);
                //mostrarComparativa.putExtra("posImagenSeta", posImagenSeta);
                mostrarComparativa.putStringArrayListExtra("resultados", (ArrayList<String>) resultados);
                startActivity(mostrarComparativa);
            } else {
                Toast.makeText(this, "bitmap null", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error en la carga de la imágen", Toast.LENGTH_LONG).show();
        }
    }

    /*
     * @name: onItemLongClick
     * @Author: Adrián Antón García
     * @category: procedure
     * @Description: Procedimiento que se ejecuta cuando se mantiene pulsado sobre algún elemento del listView
     * @Param: AdapterView<?>, Vista padre del elemento pulsado.
     * @Param: View, Vista del elemento pulsado.
     * @Param: int, posición en la lista del elemento pulsado.
     * @Param: id, identificador del elemento pulsado.
     * */

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String path = listaSetas.get(position).path;
        //cargo la imagen asociada a ese path
        if (path != null) {
            InputStream is = null;
            try {
                is = this.getResources().getAssets().open(path);
            } catch (IOException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
            Bitmap bit = BitmapFactory.decodeStream(is);
            if (bit != null) {
                //asocio el bitmap al imageview
                Intent mostrarInfoSeta = new Intent(MostrarResultados.this, MostrarInformacionSeta.class);
                mostrarInfoSeta.putExtra("nombreSeta", nombresSetas.get(position));
                mostrarInfoSeta.putExtra("actMostrarResultados", 1);
                mostrarInfoSeta.putExtra("fotoBitmap", bitmapImagen);
                mostrarInfoSeta.putStringArrayListExtra("resultados", resultados);
                startActivity(mostrarInfoSeta);
            } else {
                Toast.makeText(this, "bitmap null", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error en la carga de la imágen", Toast.LENGTH_LONG).show();
        }
        return true;
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
            case R.id.boton_refrescar_resultados: //Refrescar
                posImagenSeta++;
                if (posImagenSeta == 6) {
                    posImagenSeta = 1;
                }
                inflarLista();
                break;
            case R.id.boton_clave:
                Intent clave = new Intent(MostrarResultados.this, ElegirClaves.class);
                //Envio los resultados a la actividad ElegirClaves
                clave.putStringArrayListExtra("resultados", (ArrayList<String>) nombresSetas);
                clave.putStringArrayListExtra("resultadosAdevolver", resultados);
                clave.putExtra("fotoBitmap", bitmapImagen);
                finish();
                startActivity(clave);
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
            final Dialog dialog = new Dialog(MostrarResultados.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_mostrar_resultados);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_resultados);
        //si el menu esta abierto
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //lo cerramos
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(MostrarResultados.this, RecogerFoto.class);
            intent.putExtra("idioma", idioma);
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
            Intent cambioActividad = new Intent(MostrarResultados.this, RecogerFoto.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_ir_claves) {
            Intent cambioActividad = new Intent(MostrarResultados.this, MostrarClaves.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_informacion) {
            Intent cambioActividad = new Intent(MostrarResultados.this, MostrarSetas.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_home) {
            Intent cambioActividad = new Intent(MostrarResultados.this, Lanzadora.class);
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
            intent.putStringArrayListExtra("resultados", resultados);
            intent.putExtra("fotoBitmap", bitmapImagen);
            intent.putExtra("idioma", idioma);
            //llamamos a la actividad
            this.startActivity(intent);
            //finalizamos la actividad actual
            this.finish();
        } else if (id == R.id.menu_ayuda) {
            //genero la ayuda del menú lateral
            final Dialog dialog = new Dialog(MostrarResultados.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_menu);
            dialog.setCancelable(true);
            dialog.show();
        }

        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_mostrar_resultados);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
