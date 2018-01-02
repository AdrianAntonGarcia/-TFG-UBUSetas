package ubusetas.ubu.adrian.proyectoubusetas;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.TextView;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static org.robolectric.Shadows.shadowOf;

/**
 * Clase que prueba la actividad recogerFoto.
 *
 * @author Adrián Antón García
 * @name PruebasActividadMostrarSetas
 * @category clase
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PruebasActividadRecogerFoto {

    private RecogerFoto recogerFoto;

    /**
     * çProcedimiento que inicializa la actividad RecogerFoto en español
     * para ser usada por los demás tests.
     *
     * @name setupEs
     * @author Adrián Antón García
     * @category procedimiento test
     */

    public void setupEs() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "es");
        intent.putExtra("nombreSeta", "Agaricus urinascens");
        recogerFoto = Robolectric.buildActivity(RecogerFoto.class, intent).create().get();
    }

    /**
     * Procedimiento que inicializa la actividad RecogerFoto en inglés
     * para ser usada por los demás tests.
     *
     * @name setupEn
     * @author Adrián Antón García
     * @category procedimiento test
     */

    public void setupEn() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "en");
        intent.putExtra("nombreSeta", "Agaricus urinascens");
        recogerFoto = Robolectric.buildActivity(RecogerFoto.class, intent).create().get();
    }

    /**
     * Procedimiento que prueba los textos en español de la actividad mostrar setas y comprueba
     * que los resultados se visualicen de forma correcta en la actividad.
     *
     * @name probarTextosEs
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    @Config(qualifiers = "es")
    public void probarTextosEs() {
        setupEs();
        TextView texto1 = (TextView) recogerFoto.findViewById(R.id.TextView_elegir_opcion);
        assertEquals("Seleccione una opción:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
        TextView texto2 = (TextView) recogerFoto.findViewById(R.id.TextView_imagen_mostrada);
        assertEquals("Imagen tomada para clasificar:", texto2.getText());
        assertFalse("texto".equals(texto2.getText()));
    }

    /**
     * Procedimiento que prueba los textos en español de la actividad mostrar setas y comprueba
     * que los resultados se visualicen de forma correcta en la actividad.
     *
     * @name probarTextosEn
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    @Config(qualifiers = "en")
    public void probarTextosEn() {
        setupEn();
        TextView texto1 = (TextView) recogerFoto.findViewById(R.id.TextView_elegir_opcion);
        assertEquals("Choose an option:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
        TextView texto2 = (TextView) recogerFoto.findViewById(R.id.TextView_imagen_mostrada);
        assertEquals("Image taken to classify:", texto2.getText());
        assertFalse("texto".equals(texto2.getText()));
    }

    /**
     * Procedimiento que pulsa el botón de clasificar del menú y comprueba que la aplicación
     * haya cambiado a la actividad recogerFoto.
     *
     * @name pulsarMenuClasificar
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void pulsarMenuClasificar() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(recogerFoto);
        NavigationView nav = (NavigationView) recogerFoto.findViewById(R.id.nav_view);
        MenuItem MenuItemClasificar = nav.getMenu().getItem(0).getSubMenu().getItem(1);
        // Click menu
        recogerFoto.onNavigationItemSelected(MenuItemClasificar);
        Intent actual = shadowActivity.getNextStartedActivity();
        //No cambia la actividad
        assertNull(actual);
    }

    /**
     * Procedimiento que pulsa el botón de mostrar setas del menú y comprueba que la aplicación
     * haya cambiado a la actividad recogerFoto.
     *
     * @name pulsarMenuMostrarSetas
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void pulsarMenuMostrarSetas() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(recogerFoto);
        NavigationView nav = (NavigationView) recogerFoto.findViewById(R.id.nav_view);
        MenuItem MenuItemMostrarSetas = nav.getMenu().getItem(0).getSubMenu().getItem(3);
        // Click menu
        recogerFoto.onNavigationItemSelected(MenuItemMostrarSetas);
        Intent expectedIntent = new Intent(recogerFoto, MostrarSetas.class);
        Intent actual = shadowActivity.getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    /**
     * Procedimiento que pulsa el botón de ir claves y comprueba que la aplicación
     * haya cambiado a la actividad mostrar claves.
     *
     * @name pulsarMenuIrClaves
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void pulsarMenuIrClaves() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(recogerFoto);
        NavigationView nav = (NavigationView) recogerFoto.findViewById(R.id.nav_view);
        MenuItem MenuItemIrClaves = nav.getMenu().getItem(0).getSubMenu().getItem(2);
        // Click menu
        recogerFoto.onNavigationItemSelected(MenuItemIrClaves);
        Intent actual = shadowActivity.getNextStartedActivity();
        Intent expectedIntent = new Intent(recogerFoto, MostrarClaves.class);

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    /**
     * Procedimiento que pulsa el botón de cambiar el idioma del menú
     * desde la aplicación en español y comprueba que envie el idioma correcto.
     *
     * @name pulsarMenuCambiarIdioma
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    @Config(qualifiers = "es")
    public void pulsarMenuCambiarIdioma() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(recogerFoto);
        NavigationView nav = (NavigationView) recogerFoto.findViewById(R.id.nav_view);
        MenuItem MenuItemCambiarIdioma = nav.getMenu().getItem(1).getSubMenu().getItem(0);
        // Click cambiar idioma
        recogerFoto.onNavigationItemSelected(MenuItemCambiarIdioma);
        Intent expectedIntent = new Intent(recogerFoto, RecogerFoto.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que el idioma se cambie al inglés
        assertEquals("en", actual.getExtras().getString("idioma"));
        setupEs();
    }
}
