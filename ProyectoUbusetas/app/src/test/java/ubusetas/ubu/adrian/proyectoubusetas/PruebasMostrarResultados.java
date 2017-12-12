package ubusetas.ubu.adrian.proyectoubusetas;

/**
 * Created by adrit on 11/12/2017.
 */
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowIntent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarResultados;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThat;


/*
* @name: PruebasActividadLanzadora
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que prueba la actividad lanzadora
* */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PruebasMostrarResultados {

        /*
    * @name: pulsarClasificar
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de clasificar y comprueba que la aplicación
    * haya cambiado a la actividad recogerFoto
    * */

    @Test
    public void probarTextos() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma","es");

        ArrayList<String> resultados = new ArrayList<String>();
        resultados.add("[117] chalciporus piperatus (35,1%)");
        resultados.add(" [87] chroogomphus rutilus (31,5%)");
        resultados.add(" [67] tricholoma ustale (17,7%)");
        resultados.add(" [103] lactarius rubidus (7,4%)");
        resultados.add(" [93] suillus pungens (2,2%)");

        intent.putExtra("resultados",resultados);
        Lanzadora lanzadora = Robolectric.buildActivity(Lanzadora.class).create().get();
        AccesoDatosExternos acceso= new AccesoDatosExternos(lanzadora);
        Bitmap bit = acceso.accesoImagenPorPath("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (1).jpg");

        MostrarResultados mostrarResultados = Robolectric.buildActivity(MostrarResultados.class, intent).create().get();
        TextView texto= (TextView) mostrarResultados.findViewById(R.id.TextView_ResultadosClasificador);
        assertEquals("Resultados:", texto.getText());

    }
}
