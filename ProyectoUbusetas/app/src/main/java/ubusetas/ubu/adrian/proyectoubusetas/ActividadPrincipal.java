package ubusetas.ubu.adrian.proyectoubusetas;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
* @name: ActividadPrincipal
* @Author: Adrián Antón García
* @category: class
* @Description: Clase que implementa la funcionalidad relacionada con la toma y guardado de fotografías
* */

public class ActividadPrincipal extends AppCompatActivity implements View.OnClickListener {

    //Elementos

    private Button botonHacerFoto;
    private Button botonGaleria;
    private Button botonGuardarFoto;
    private Button botonClasificar;
    private ImageView imageViewMostrarFoto;
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
        setContentView(R.layout.activity_actividad_principal);

        //Relaciones entre los elementos con el xml

        botonHacerFoto = (Button) findViewById(R.id.boton_hacer_foto);
        botonGuardarFoto = (Button) findViewById(R.id.boton_guardar_foto);
        botonGaleria = (Button) findViewById(R.id.boton_galeria);
        botonClasificar = (Button) findViewById(R.id.boton_clasificar);
        imageViewMostrarFoto = (ImageView) findViewById(R.id.imageView_mostrar_imagen);
        textoImagen = (TextView) findViewById(R.id.textView_textoImagen);

        //activo los botones

        botonHacerFoto.setOnClickListener(this);
        botonGuardarFoto.setOnClickListener(this);
        botonGaleria.setOnClickListener(this);
        botonClasificar.setOnClickListener(this);

        inicializarClasificador();

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
                        imageViewMostrarFoto.buildDrawingCache();
                        Bitmap bmap = imageViewMostrarFoto.getDrawingCache();
                        bmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
                        fileOutStream.flush();
                        fileOutStream.close();
                        //notificamos que la creación ha sido correcta
                        Toast.makeText(this, "Imágen guardada en: " + directorioImagenes.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                        Toast.makeText(this, "Error en la creacion de la imagen, revisar permisos escritura", Toast.LENGTH_LONG).show();
                    }
                    //textoImagen.setText("En construcción");
                }
                ;
                break;

            case R.id.boton_clasificar://BOTÓN CLASIFICAR FOTO
                Toast.makeText(this, "Clasificando", Toast.LENGTH_SHORT).show();
                List<Classifier.Recognition> resultados = null;
                List<String> resultadosTexto = new ArrayList<String>();
                Bitmap bitmapClasificar = null;
                if (fotoPath == null) {
                    textoImagen.setText("Todavía no se ha tomado ninguna imágen");
                } else {

                    //creo el bitmap de la foto
                    imageViewMostrarFoto.buildDrawingCache();
                    bitmapClasificar = imageViewMostrarFoto.getDrawingCache();
                    bitmapClasificar = Bitmap.createScaledBitmap(bitmapClasificar, INPUT_SIZE, INPUT_SIZE, false);

                    imageViewMostrarFoto.setImageBitmap(bitmapClasificar);
                    //recojo los resultados del clasificador
                    resultados = classifier.recognizeImage(bitmapClasificar);

                    if (resultados != null) {
                        for (Classifier.Recognition e : resultados) {
                            resultadosTexto.add(e.toString());
                        }
                        //cambiamos de actividad para mostrar el resultado
                        Intent cambioActividad = new Intent(ActividadPrincipal.this, MostrarResultados.class);
                        cambioActividad.putStringArrayListExtra("resultados", (ArrayList<String>) resultadosTexto);
                        cambioActividad.putExtra("fotoBitmap", bitmapClasificar);
                        cambioActividad.putExtra("posImagenSeta",1);
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
                    Bitmap bitmapFoto = BitmapFactory.decodeFile(fotoPath);
                    //establecemos el bitmap en el imageview
                    imageViewMostrarFoto.setImageBitmap(bitmapFoto);
                    //reseteo la cache del bitmap
                    imageViewMostrarFoto.destroyDrawingCache();
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
                    //la muestro
                    imageViewMostrarFoto.setImageURI(uriImagen);
                    //reseteo la cache del bitmap
                    imageViewMostrarFoto.destroyDrawingCache();
                }
        }
    }
}

