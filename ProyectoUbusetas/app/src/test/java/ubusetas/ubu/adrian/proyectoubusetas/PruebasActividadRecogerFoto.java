package ubusetas.ubu.adrian.proyectoubusetas;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import org.junit.Before;
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

/*
* @name: PruebasActividadMostrarSetas
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que prueba la actividad recogerFoto
* */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PruebasActividadRecogerFoto {

    private RecogerFoto recogerFoto;

    /*
    * @name: setup
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad mostrar setas
    * para ser usada por los demás tests
    * */

    @Before
    @Config(qualifiers = "es")
    public void setup() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        recogerFoto = Robolectric.buildActivity(RecogerFoto.class, intent).create().get();
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
        TextView texto1 = (TextView) recogerFoto.findViewById(R.id.TextView_elegir_opcion);
        assertEquals("Seleccione una opción:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
        TextView texto2 = (TextView) recogerFoto.findViewById(R.id.TextView_imagen_mostrada);
        assertEquals("Imagen tomada para clasificar:", texto2.getText());
        assertFalse("texto".equals(texto2.getText()));
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
        TextView texto1 = (TextView) recogerFoto.findViewById(R.id.TextView_elegir_opcion);
        assertEquals("Choose an option:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
        TextView texto2 = (TextView) recogerFoto.findViewById(R.id.TextView_imagen_mostrada);
        assertEquals("Image taken to classify:", texto2.getText());
        assertFalse("texto".equals(texto2.getText()));
    }
    /*
    * @name: pulsarMenuClasificar
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de clasificar del menú y comprueba que la aplicación
    * haya cambiado a la actividad recogerFoto
    * */
    @Test
    public void pulsarMenuClasificar() {
        ShadowActivity shadowActivity = shadowOf(recogerFoto);
        NavigationView nav = (NavigationView) recogerFoto.findViewById(R.id.nav_view);
        MenuItem MenuItemClasificar = nav.getMenu().getItem(0).getSubMenu().getItem(1);
        // Click menu
        recogerFoto.onNavigationItemSelected(MenuItemClasificar);
        Intent actual = shadowActivity.getNextStartedActivity();
        //No cambia la actividad
        assertNull(actual);
    }

    /*
    * @name: pulsarMenuMostrarSetas
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de mostrar setas del menú y comprueba que la aplicación
    * haya cambiado a la actividad recogerFoto
    * */

    @Test
    public void pulsarMenuMostrarSetas() {

        ShadowActivity shadowActivity = shadowOf(recogerFoto);
        NavigationView nav = (NavigationView) recogerFoto.findViewById(R.id.nav_view);
        MenuItem MenuItemMostrarSetas = nav.getMenu().getItem(0).getSubMenu().getItem(3);
        // Click menu
        recogerFoto.onNavigationItemSelected(MenuItemMostrarSetas);
        Intent expectedIntent = new Intent(recogerFoto, MostrarSetas.class);
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
        ShadowActivity shadowActivity = shadowOf(recogerFoto);
        NavigationView nav = (NavigationView) recogerFoto.findViewById(R.id.nav_view);
        MenuItem MenuItemIrClaves = nav.getMenu().getItem(0).getSubMenu().getItem(2);
        // Click menu
        recogerFoto.onNavigationItemSelected(MenuItemIrClaves);
        Intent actual = shadowActivity.getNextStartedActivity();
        Intent expectedIntent = new Intent(recogerFoto, MostrarClaves.class);

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
        setup();
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
    }
}
