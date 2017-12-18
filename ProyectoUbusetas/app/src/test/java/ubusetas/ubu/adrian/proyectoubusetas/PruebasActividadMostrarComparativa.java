package ubusetas.ubu.adrian.proyectoubusetas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.ArrayList;

import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarComparativa;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.robolectric.Shadows.shadowOf;

/*
* @name: PruebasActividadMostrarSetas
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que prueba la actividad mostrarComparativa
* */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PruebasActividadMostrarComparativa {

    private MostrarComparativa mostrarComparativa;
    private Bitmap bitArriba;
    private Bitmap bitAbajo;
    /*
    * @name: setupEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad MostrarComparativa en español
    * para ser usada por los demás tests
    * */


    public void setupEs() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "es");

        ArrayList<String> resultados = new ArrayList<String>();
        resultados.add("[117] chalciporus piperatus (35,1%)");
        resultados.add(" [87] chroogomphus rutilus (31,5%)");
        resultados.add(" [67] tricholoma ustale (17,7%)");
        resultados.add(" [103] lactarius rubidus (7,4%)");
        resultados.add(" [93] suillus pungens (2,2%)");

        intent.putExtra("resultados", resultados);
        Lanzadora lanzadora = Robolectric.buildActivity(Lanzadora.class).create().get();
        AccesoDatosExternos acceso = new AccesoDatosExternos(lanzadora);
        bitArriba = acceso.accesoImagenPorPath("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (1).jpg");
        intent.putExtra("foto_seta", bitArriba);
        bitAbajo = acceso.accesoImagenPorPath("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (2).jpg");
        intent.putExtra("fotoBitmap", bitAbajo);
        mostrarComparativa = Robolectric.buildActivity(MostrarComparativa.class, intent).create().get();
    }

    /*
    * @name: setupEn
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad MostrarComparativa en inglés
    * para ser usada por los demás tests
    * */

    public void setupEn() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "en");

        ArrayList<String> resultados = new ArrayList<String>();
        resultados.add("[117] chalciporus piperatus (35,1%)");
        resultados.add(" [87] chroogomphus rutilus (31,5%)");
        resultados.add(" [67] tricholoma ustale (17,7%)");
        resultados.add(" [103] lactarius rubidus (7,4%)");
        resultados.add(" [93] suillus pungens (2,2%)");

        intent.putExtra("resultados", resultados);
        Lanzadora lanzadora = Robolectric.buildActivity(Lanzadora.class).create().get();
        AccesoDatosExternos acceso = new AccesoDatosExternos(lanzadora);
        bitArriba = acceso.accesoImagenPorPath("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (1).jpg");
        intent.putExtra("foto_seta", bitArriba);
        bitAbajo = acceso.accesoImagenPorPath("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (2).jpg");
        intent.putExtra("fotoBitmap", bitAbajo);
        mostrarComparativa = Robolectric.buildActivity(MostrarComparativa.class, intent).create().get();
    }


    /*
    * @name: probarTextosEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que prueba los textos en español de la actividad mostrar setas y comprueba
    * que los resultados se visualicen de forma correcta en la actividad
    * */

    @Test
    @Config(qualifiers = "es")
    public void probarTextosEs() {
        setupEs();
        TextView texto1 = (TextView) mostrarComparativa.findViewById(R.id.TextView_Comparativa);
        assertEquals("Comparativa entre las imágenes:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
    }


    /*
    * @name: probarTextosEn
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que prueba los textos en español de la actividad mostrar setas y comprueba
    * que los resultados se visualicen de forma correcta en la actividad
    * */

    @Test
    @Config(qualifiers = "en")
    public void probarTextosEn() {
        setupEn();
        TextView texto1 = (TextView) mostrarComparativa.findViewById(R.id.TextView_Comparativa);
        assertEquals("Comparision between images:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
    }

    /*
    * @name: pulsarMenuClasificar
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de clasificar del menú y comprueba que la aplicación
    * haya cambiado a la actividad mostrarComparativa
    * */
    @Test
    public void pulsarMenuClasificar() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(mostrarComparativa);
        NavigationView nav = (NavigationView) mostrarComparativa.findViewById(R.id.nav_view);
        MenuItem MenuItemClasificar = nav.getMenu().getItem(0).getSubMenu().getItem(1);
        // Click menu
        mostrarComparativa.onNavigationItemSelected(MenuItemClasificar);
        Intent expectedIntent = new Intent(mostrarComparativa, RecogerFoto.class);
        Intent actual = shadowActivity.getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    /*
    * @name: pulsarMenuMostrarSetas
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de mostrar setas del menú y comprueba que la aplicación
    * haya cambiado a la actividad mostrarComparativa
    * */

    @Test
    public void pulsarMenuMostrarSetas() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(mostrarComparativa);
        NavigationView nav = (NavigationView) mostrarComparativa.findViewById(R.id.nav_view);
        MenuItem MenuItemMostrarSetas = nav.getMenu().getItem(0).getSubMenu().getItem(3);
        // Click menu
        mostrarComparativa.onNavigationItemSelected(MenuItemMostrarSetas);
        Intent expectedIntent = new Intent(mostrarComparativa, MostrarSetas.class);
        Intent actual = shadowActivity.getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    /*
    * @name: pulsarMenuIrClaves
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de ir claves y comprueba que la aplicación
    * haya cambiado a la actividad mostrar claves
    * */
    @Test
    public void pulsarMenuIrClaves() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(mostrarComparativa);
        NavigationView nav = (NavigationView) mostrarComparativa.findViewById(R.id.nav_view);
        MenuItem MenuItemIrClaves = nav.getMenu().getItem(0).getSubMenu().getItem(2);
        // Click menu
        mostrarComparativa.onNavigationItemSelected(MenuItemIrClaves);
        Intent actual = shadowActivity.getNextStartedActivity();
        Intent expectedIntent = new Intent(mostrarComparativa, MostrarClaves.class);

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    /*
    * @name: pulsarMenuCambiarIdioma
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de cambiar el idioma del menú
    * desde la aplicación en español y comprueba que envie el idioma correcto
    * */
    @Test
    @Config(qualifiers = "es")
    public void pulsarMenuCambiarIdioma() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(mostrarComparativa);
        NavigationView nav = (NavigationView) mostrarComparativa.findViewById(R.id.nav_view);
        MenuItem MenuItemCambiarIdioma = nav.getMenu().getItem(1).getSubMenu().getItem(0);
        // Click cambiar idioma
        mostrarComparativa.onNavigationItemSelected(MenuItemCambiarIdioma);
        Intent expectedIntent = new Intent(mostrarComparativa, MostrarComparativa.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que el idioma se cambie al inglés
        assertEquals("en", actual.getExtras().getString("idioma"));
    }
}
