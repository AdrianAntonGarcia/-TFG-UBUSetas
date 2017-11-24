package ubusetas.ubu.adrian.proyectoubusetas.clasificador;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarResultados;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

/*
* @name: RecogerFoto
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que implementa la funcionalidad relacionada con la toma y guardado de fotografías
* */

public class RecogerFoto extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = RecogerFoto.class.getSimpleName();

    private Bitmap bmap =null;
    //Elementos

    private FloatingActionButton botonHacerFoto;
    private FloatingActionButton botonGaleria;
    private FloatingActionButton botonGuardarFoto;
    private FloatingActionButton botonClasificar;
    private ImageView imageViewMostrarFoto;
    private Bitmap bitmapImagen;
    private TextView textoImagen;

    //Codigos de peticiones

    static final int CODIGO_CAMARA = 101;
    static final int CODIGO_GALERIA = 102;

    //path de la foto capturada

    String fotoPath;

    //MODELOS

    //inception

    /*private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";
    private static final String MODEL_FILE = "file:///android_asset/inception-v3/inception.pb";
    private static final String LABEL_FILE = "file:///android_asset/inception-v3/output_labels.txt";*/

    //mobilenet-224

    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 224;
    private static final float IMAGE_STD = 224;
    private static final String OUTPUT_NAME = "final_result";
    private static final String INPUT_NAME = "input";

    private static final String MODEL_FILE = "file:///android_asset/mobilenet-224/output_graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/mobilenet-224/output_labels.txt";

    //Clasificador

    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();

    /*
    * @name: onCreate
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Metodo que se ejecuta cuando se carga la clase, inicializa los elementos
    * y los relaciona con el contexto
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_foto);

        //Relaciones entre los elementos con el xml

        botonHacerFoto = (FloatingActionButton) findViewById(R.id.boton_hacer_foto);
        botonGuardarFoto = (FloatingActionButton) findViewById(R.id.boton_guardar_foto);
        botonGaleria = (FloatingActionButton) findViewById(R.id.boton_galeria);
        botonClasificar = (FloatingActionButton) findViewById(R.id.boton_clasificar);
        imageViewMostrarFoto = (ImageView) findViewById(R.id.imageView_mostrar_imagen);
        textoImagen = (TextView) findViewById(R.id.textView_textoImagen);

        botonGuardarFoto.hide();
        botonClasificar.hide();
        //activo los botones

        botonHacerFoto.setOnClickListener(this);
        botonGuardarFoto.setOnClickListener(this);
        botonGaleria.setOnClickListener(this);
        botonClasificar.setOnClickListener(this);

        inicializarClasificador();
        restaurarCampos(savedInstanceState);

        //parte del menu lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_recoger_foto);
        //cargamos la nueva barra
        setSupportActionBar(toolbar);

        //cargamos el layout del menu y lo inicializamos
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_recoger_foto);
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
     * @category: procedure
     * @Description: Metodo que se restaura el bitmap al girar la pantalla
     * */

    private void restaurarCampos(Bundle savedInstanceState) {

        // Si hay algo en el bundle
        if (savedInstanceState != null) {
            //Recupero la foto
            bmap = savedInstanceState.getParcelable("bmap");
            fotoPath = savedInstanceState.getString("fotoPath");
            if (bmap != null) {
                imageViewMostrarFoto.setImageBitmap(bmap);
            } else {
                Log.d(TAG, "No restaura el campo");
            }
        }
    }

    /*
    * @name: onSaveInstanceState
    * @Author: Adrián Antón García
    * @category: procedure
    * @Description: Metodo que se ejecuta cuando se destruye la actividad
    * */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bmap != null) {
            // Metemos en el bundle lo que queremos conservar
            outState.putParcelable("bmap", bmap);
            outState.putString("fotoPath", fotoPath);
        }
    }

    /*
    * @name: onClick
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Método que es llamado cuándo se hace click en cualquier botón
    * */

    @Override
    public void onClick(View v) {

        //con el switch se diferencia entre los diferentes botones que pueden llamar a este método

        switch (v.getId()) {

            case R.id.boton_hacer_foto: //BOTÓN HACER FOTO
                //creamos un archivo temporal
                File tempFile = null;
                //lo guardamos en la cache
                try {
                    tempFile = File.createTempFile("fotoSeta", ".jpg", getExternalCacheDir());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //guardamos el path
                fotoPath = tempFile.getAbsolutePath();
                //sacamos la uri de la foto temporal
                Uri uri = Uri.fromFile(tempFile);
                //llamamos al sistema para que capture una imágen desde la cámara
                //intent implicita
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //donde debe guardar la foto
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //iniciamos la petición
                startActivityForResult(intent, CODIGO_CAMARA);
                break;

            case R.id.boton_guardar_foto: //BOTÓN GUARDAR FOTO

                if (fotoPath == null) {
                    textoImagen.setText("Todavía no se ha tomado ninguna imágen");
                } else {
                    OutputStream fileOutStream = null;
                    Uri uriIm;
                    try {
                        //creamos el directorio imagenesSetas que es donde se van a almacenar las imágenes
                        File file = new File(Environment.getExternalStorageDirectory(), "imagenesSetas");
                        file.mkdirs();
                        //ponemos la fecha como distintivo en las fotos
                        String fecha = getCurrentDateAndTime();
                        File directorioImagenes = new File(file, "fotoSeta" + fecha + ".jpg");
                        uriIm = Uri.fromFile(directorioImagenes);
                        fileOutStream = new FileOutputStream(directorioImagenes);
                        //creamos un bitmao del imageview previamente cargado
                        bmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                        fileOutStream.flush();
                        fileOutStream.close();
                        //notificamos que la creación ha sido correcta
                        Toast.makeText(this, "Imágen guardada en: " + directorioImagenes.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                        Toast.makeText(this, "Error en la creacion de la imagen, revisar permisos escritura", Toast.LENGTH_LONG).show();
                    }
                }
                ;
                break;

            case R.id.boton_clasificar://BOTÓN CLASIFICAR FOTO
                Toast.makeText(this, "Clasificando", Toast.LENGTH_SHORT).show();
                List<Classifier.Recognition> resultados = null;
                List<String> resultadosTexto = new ArrayList<String>();
                Bitmap bitmap;
                if (fotoPath == null) {
                    textoImagen.setText("Todavía no se ha tomado ninguna imágen");
                } else {

                    //creo el bitmap de la foto
                    //imageViewMostrarFoto.buildDrawingCache();
                    //bitmap = imageViewMostrarFoto.getDrawingCache();
                    //bitmapClasificar=Bitmap.createBitmap(INPUT_SIZE,INPUT_SIZE,bitmapClasificar.getConfig());
                    Bitmap bitmapClasificar = Bitmap.createScaledBitmap(bmap, 224, 224, false);

                    //imageViewMostrarFoto.setImageBitmap(bitmapClasificar);
                    //recojo los resultados del clasificador
                    resultados = classifier.recognizeImage(bitmapClasificar);

                    if (resultados != null) {
                        for (Classifier.Recognition e : resultados) {
                            resultadosTexto.add(e.toString());
                        }
                        //cambiamos de actividad para mostrar el resultado
                        Intent cambioActividad = new Intent(RecogerFoto.this, MostrarResultados.class);
                        cambioActividad.putStringArrayListExtra("resultados", (ArrayList<String>) resultadosTexto);
                        cambioActividad.putExtra("fotoBitmap", bitmapClasificar);
                        cambioActividad.putExtra("posImagenSeta", 1);
                        startActivity(cambioActividad);
                    } else {
                        textoImagen.setText("No se ha podido clasificar la imágen");
                    }
                }
                ;
                break;

            case R.id.boton_galeria:
                Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galeria, CODIGO_GALERIA);
                break;
        }
    }

    /*
    * @name: inicializarClasificador
    * @Author: Adrián Antón García
    * @category: Procedimiento
    * @Description: Procedimiento que inicializa el clasificador
    * */

    private void inicializarClasificador() {
        //inicializo el clasificador
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_FILE,
                            LABEL_FILE,
                            INPUT_SIZE,
                            IMAGE_MEAN,
                            IMAGE_STD,
                            INPUT_NAME,
                            OUTPUT_NAME);
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    /*
    * @name: getCurrentDateAndTime
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Método que devuelve un String que contiene la fecha actual
    * */

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /*
    * @name: onActivityResult
    * @Author: Adrián Antón García
    * @category: method
    * @Description: Método que es llamado cuando se termina una peticion que habíamos
    * realizado al sistema, por ejemplo cuando llamamos a la cámara
    * */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Compruebo si la peticion viene de la cámara y se ha realizado correctamente
        switch (requestCode) {
            case CODIGO_CAMARA: //si la petición ha sido llamada por la cámara
                if (resultCode == RESULT_OK) {
                    //creamos un bitmap a partir de la imágen creada
                    bmap = BitmapFactory.decodeFile(fotoPath);
                    //establecemos el bitmap en el imageview
                    imageViewMostrarFoto.setImageBitmap(bmap);
                    botonGuardarFoto.show();
                    botonClasificar.show();
                } else {
                    textoImagen.setText("ERROR EN LA CREACIÓN DE LA IMÁGEN");
                }
                break;
            case CODIGO_GALERIA: //si la petición ha sido llamada por la galería de imágenes
                if (resultCode == RESULT_OK) {
                    //recogemos la dirección de la imágen
                    Uri uriImagen = data.getData();
                    //guardo su path
                    fotoPath = uriImagen.getPath();
                    //botonGuardarFoto.show();
                    botonClasificar.show();
                    //la decodifico en un bitmap
                    try {
                        bmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uriImagen);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageViewMostrarFoto.setImageBitmap(bmap);
                }
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_recoger_foto);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_ir_claves) {
            Intent cambioActividad = new Intent(RecogerFoto.this, MostrarClaves.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_informacion) {
            Intent cambioActividad = new Intent(RecogerFoto.this, MostrarSetas.class);
            startActivity(cambioActividad);
        } else if (id == R.id.menu_home) {
            Intent cambioActividad = new Intent(RecogerFoto.this, Lanzadora.class);
            startActivity(cambioActividad);
        }
        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_recoger_foto);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

