package ubusetas.ubu.adrian.proyectoubusetas.clasificador;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ubusetas.ubu.adrian.proyectoubusetas.R;
import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarResultados;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

/*
* @name: RecogerFoto
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que implementa la funcionalidad relacionada con la toma y guardado de fotografías.
* */

public class RecogerFoto extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = RecogerFoto.class.getSimpleName();

    //Bitmap y path donde guardamos la foto introducida por el usuario

    private Bitmap bmap = null;
    String fotoPath;

    //Elementos de la interfaz

    private FloatingActionButton botonHacerFoto;
    private FloatingActionButton botonGaleria;
    private FloatingActionButton botonGuardarFoto;
    private FloatingActionButton botonClasificar;
    private ImageView imageViewMostrarFoto;
    private AccesoDatosExternos acceso;

    //Idioma de la aplicación
    private String idioma;

    //Codigos de peticiones

    private static final int CODIGO_CAMARA = 101;
    private static final int CODIGO_GALERIA = 102;

    //Nos da la orientación de la pantalla
    ExifInterface ei;

    //Nos dice si hay que ocultar los botones

    boolean ocultarClasificar;
    boolean ocultarGuardar;
    //MODELOS, CLASIFICADORES

    //inception

    /*private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";
    private static final String MODEL_FILE = "file:///android_asset/inception-v3/inception.pb";
    private static final String LABEL_FILE = "file:///android_asset/inception-v3/output_labels.txt";*/

    //mobilenet-224

    //Parámetros del modelo

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
    * @category: procedimiento
    * @Description: Procedimiento que se ejecuta cuando se carga la clase, inicializa los elementos
    * y los relaciona con el contexto.
    * @param: Bundle, Bundle donde se guardan los datos cuando se cierra la actividad.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoger_foto);

        //Cargamos el idioma si se rota la pantalla
        idioma = null;
        idioma = Locale.getDefault().getLanguage();
        Intent intentRecibidos = getIntent();
        Bundle datosRecibidos = intentRecibidos.getExtras();

        if (datosRecibidos != null) {
            idioma = datosRecibidos.getString("idioma");
        }
        acceso = new AccesoDatosExternos(this);


        //Relaciones entre los elementos con el xml

        botonHacerFoto = (FloatingActionButton) findViewById(R.id.boton_hacer_foto);
        botonGuardarFoto = (FloatingActionButton) findViewById(R.id.boton_guardar_foto);
        botonGaleria = (FloatingActionButton) findViewById(R.id.boton_galeria);
        botonClasificar = (FloatingActionButton) findViewById(R.id.boton_clasificar);
        imageViewMostrarFoto = (ImageView) findViewById(R.id.imageView_mostrar_imagen);

        ocultarGuardar = true;
        ocultarClasificar = true;
        botonGuardarFoto.hide();
        botonClasificar.hide();
        //activo los botones

        botonHacerFoto.setOnClickListener(this);
        botonGuardarFoto.setOnClickListener(this);
        botonGaleria.setOnClickListener(this);
        botonClasificar.setOnClickListener(this);

        inicializarClasificador();
        restaurarCampos(savedInstanceState);

        if (ocultarGuardar == false) {
            botonGuardarFoto.show();
        }
        if (ocultarClasificar == false) {
            botonClasificar.show();
        }
        //parte del menu lateral

        Toolbar toolbar = (Toolbar) findViewById(R.id.barra_recoger_foto);
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
     * @category: procedimiento
     * @Description: Procedimiento que se restaura el bitmap al girar la pantalla.
     * @param: Bundle, Bundle donde se guardan los datos cuando se cierra la actividad.
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

            ocultarClasificar = savedInstanceState.getBoolean("ocultarClasificar");
            ocultarGuardar = savedInstanceState.getBoolean("ocultarGuardar");

            //reestablecemos el idioma
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
        if (bmap != null) {
            // Metemos en el bundle lo que queremos conservar
            outState.putParcelable("bmap", bmap.copy(Bitmap.Config.ARGB_8888, false));
            outState.putString("fotoPath", fotoPath);
        }
        //guardo el estado de los botones
        outState.putBoolean("ocultarClasificar", ocultarClasificar);
        outState.putBoolean("ocultarGuardar", ocultarGuardar);
        //guardo el idioma
        outState.putString("idioma", idioma);
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

        //con el switch se diferencia entre los diferentes botones que pueden llamar a este método

        switch (v.getId()) {

            case R.id.boton_hacer_foto: //BOTÓN HACER FOTO

                //creamos un archivo temporal

                File tempFile = null;

                //lo guardamos en la cache

                try {
                    tempFile = File.createTempFile("foto_seta", ".jpg", getExternalCacheDir());
                } catch (IOException e) {
                    Log.e("Error caché foto", e.getMessage());
                    e.printStackTrace();
                }

                //guardamos el path temporal

                fotoPath = tempFile.getAbsolutePath();
                Uri uri = Uri.fromFile(tempFile);

                //llamamos al sistema para que capture una imágen desde la cámara

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CODIGO_CAMARA);
                break;

            case R.id.boton_guardar_foto: //BOTÓN GUARDAR FOTO

                if (fotoPath != null) {
                    OutputStream fileOutStream;
                    try {
                        //creamos el directorio imagenesSetas que es donde se van a almacenar las imágenes

                        File file = new File(Environment.getExternalStorageDirectory(), "imagenesSetas");
                        file.mkdirs();

                        //ponemos la fecha como distintivo en las fotos

                        String fecha = getCurrentDateAndTime();
                        File directorioImagenes = new File(file, "foto_seta" + fecha + ".jpg");
                        fileOutStream = new FileOutputStream(directorioImagenes);

                        //creamos un bitmao del imageview previamente cargado

                        bmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                        fileOutStream.flush();
                        fileOutStream.close();

                        //notificamos que la creación ha sido correcta

                        Toast.makeText(this, "Imágen guardada en: " + directorioImagenes.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "La aplicación necesita permisos de lectura/escritura: ", Toast.LENGTH_LONG).show();
                        Log.e("Error guardado foto", e.getMessage());
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.boton_clasificar://BOTÓN CLASIFICAR FOTO
                Toast.makeText(this, "Clasificando", Toast.LENGTH_SHORT).show();
                List<Classifier.Recognition> resultados = null;
                List<String> resultadosTexto = new ArrayList<String>();
                Bitmap bitmap;
                if (fotoPath != null) {

                    //creo el bitmap de la foto

                    Bitmap bitmapClasificar = Bitmap.createScaledBitmap(bmap, 224, 224, false);

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
                    }
                }
                ;
                break;

            case R.id.boton_galeria: //BOTÓN ACCEDER A GALERÍA
                Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galeria, CODIGO_GALERIA);
                break;
        }
    }

    /*
    * @name: inicializarClasificador
    * @Author: Adrián Antón García
    * @category: Procedimiento
    * @Description: Procedimiento que inicializa el clasificador.
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
    * @category: método
    * @Description: Método que devuelve un String que contiene la fecha actual.
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
    * @category: procedimiento
    * @Description: Procedimiento que es llamado cuando se termina una peticion que habíamos
    * realizado al sistema, por ejemplo cuando llamamos a la cámara.
    * */

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Compruebo si la peticion viene de la cámara y se ha realizado correctamente
        switch (requestCode) {
            case CODIGO_CAMARA: //si la petición ha sido llamada por la cámara
                if (resultCode == RESULT_OK) {
                    //creamos un bitmap a partir de la imágen creada
                    bmap = null;
                    bmap = BitmapFactory.decodeFile(fotoPath);

                    //Rotamos la imágen para que se muestre correctamente

                    try {
                        ei = new ExifInterface(fotoPath);
                    } catch (IOException e) {
                        Log.e("Error rotar foto", e.getMessage());
                        e.printStackTrace();
                    }
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            bmap = RotateBitmap(bmap, 90);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            bmap = RotateBitmap(bmap, 180);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            bmap = RotateBitmap(bmap, 270);
                            break;
                    }

                    //establecemos el bitmap en el imageview
                    imageViewMostrarFoto.setImageBitmap(bmap);

                    ocultarGuardar = false;
                    ocultarGuardar = false;
                    botonGuardarFoto.show();
                    botonClasificar.show();
                }
                break;
            case CODIGO_GALERIA: //si la petición ha sido llamada por la galería de imágenes
                if (resultCode == RESULT_OK) {
                    //recogemos la dirección de la imágen
                    Uri uriImagen = data.getData();
                    //guardo su path
                    fotoPath = uriImagen.getPath();
                    //botonGuardarFoto.show();
                    ocultarClasificar = false;
                    ocultarGuardar = true;
                    botonGuardarFoto.hide();
                    botonClasificar.show();
                    //la decodifico en un bitmap
                    try {
                        bmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImagen);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imageViewMostrarFoto.setImageBitmap(bmap);
                }
        }
    }

        /*
    * @name: RotateBitmap
    * @Author: https://stackoverflow.com/questions/4166917/android-how-to-rotate-a-bitmap-on-a-center-point
    * @category: método
    * @Description: Nétodo que rota un bitmap.
    * @param: Bitmap, el bitmap a girar.
    * @param: float, los grados a girar el bitmap.
    * @return: Bitmap, el Bitmap ya girado.
    * */

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

        /*
    * @name: onCreate
    * @Author: Adrián Antón García
    * @category: procedimiento
    * @Description: Procedimiento que se ejectua cuando se pulsa el botón volver del móvil.
    * */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_recoger_foto);
        //si el menu esta abierto
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //lo cerramos
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(RecogerFoto.this, Lanzadora.class);
            intent.putExtra("idioma", idioma);
            this.startActivity(intent);
            //si el menu esta cerrado llamamos al constructor padre
            finish();
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
            final Dialog dialog = new Dialog(RecogerFoto.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_recoger_foto);
            dialog.setCancelable(true);
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    /*
   * @name: onNavigationItemSelected
   * @Author: Adrián Antón García
   * @category: método
   * @Description: Metodo que se activa cuando pulsamos un botón del menú.
   * @param: MenuItem, item seleccionado por el usuario del menú.
   * */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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

            //llamamos a la actividad
            this.startActivity(intent);
            intent.putExtra("idioma", idioma);
            //finalizamos la actividad actual
            this.finish();
        } else if (id == R.id.menu_ayuda) {
            //genero la ayuda del menú lateral
            final Dialog dialog = new Dialog(RecogerFoto.this);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(R.layout.ayuda_menu);
            dialog.setCancelable(true);
            dialog.show();
        }

        //Cerramos el menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_recoger_foto);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

