package ubusetas.ubu.adrian.proyectoubusetas;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.ClaveDicotomica;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.robolectric.Shadows.shadowOf;

/**
 * Clase que prueba la actividad claveDicotomica
 *
 * @author Adrián Antón García
 * @name PruebasActividadMostrarSetas
 * @category clase
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PruebasActividadClaveDicotomica {

    private ClaveDicotomica claveDicotomica;

    /**
     * Procedimiento que inicializa la actividad ClaveDicotomica en español
     * para ser usada por los demás tests.
     *
     * @name setupEs
     * @author Adrián Antón García
     * @category procedimiento test
     */

    public void setupEs() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "es");
        intent.putExtra("nombreClave", "general");
        claveDicotomica = Robolectric.buildActivity(ClaveDicotomica.class, intent).create().get();
    }

    /**
     * Procedimiento que inicializa la actividad ClaveDicotomica en inglés
     * para ser usada por los demás tests.
     *
     * @name setupEn
     * @author Adrián Antón García
     * @category procedimiento test
     */

    public void setupEn() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "en");
        intent.putExtra("nombreClave", "general");
        claveDicotomica = Robolectric.buildActivity(ClaveDicotomica.class, intent).create().get();
    }

    /**
     * Procedimiento que prueba los textos en español de la actividad ClaveDicotomica y comprueba
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
        TextView texto1 = (TextView) claveDicotomica.findViewById(R.id.TextView_ClaveMostrada);
        assertEquals("general", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
        TextView texto2 = (TextView) claveDicotomica.findViewById(R.id.TextView_Pregunta_clave);
        assertEquals("Seleccione la clave que mas se adecue a la seta:", texto2.getText());
        assertFalse("texto".equals(texto2.getText()));
    }

    /**
     * Procedimiento que prueba los textos en español de la actividad ClaveDicotomica y comprueba
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
        TextView texto1 = (TextView) claveDicotomica.findViewById(R.id.TextView_ClaveMostrada);
        assertEquals("general", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
        TextView texto2 = (TextView) claveDicotomica.findViewById(R.id.TextView_Pregunta_clave);
        assertEquals("Select the key that most suits the mushroom:", texto2.getText());
        assertFalse("texto".equals(texto2.getText()));
    }

    /**
     * Procedimiento que prueba el correcto funcionamiento al pulsar sobre los items de la
     * lista.
     *
     * @name seleccionarItems
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void seleccionarItems() {
        setupEs();
        ListView ListaResultados = (ListView) claveDicotomica.findViewById(R.id.listView_claveDicotomica);
        FloatingActionButton retorno = (FloatingActionButton) claveDicotomica.findViewById(R.id.boton_anterior);
        clickItem(ListaResultados, 0);
        clickItem(ListaResultados, 0);
        clickItem(ListaResultados, 0);
        TextView texto2 = (TextView) claveDicotomica.findViewById(R.id.TextView_Pregunta_clave);
        assertEquals("Género clasificado: Cantharellus", texto2.getText());
        assertFalse("Seleccione la clave que mas se adecue a la seta:".equals(texto2.getText()));
        retorno.performClick();
        texto2 = (TextView) claveDicotomica.findViewById(R.id.TextView_Pregunta_clave);
        assertEquals("Seleccione la clave que mas se adecue a la seta:", texto2.getText());
        assertFalse("Género clasificado: Cantharellus".equals(texto2.getText()));
        retorno.performClick();
        clickItem(ListaResultados, 1);
        clickItem(ListaResultados, 0);
        texto2 = (TextView) claveDicotomica.findViewById(R.id.TextView_Pregunta_clave);
        assertEquals("Género clasificado: Auriscalpium", texto2.getText());
        assertFalse("Seleccione la clave que mas se adecue a la seta:".equals(texto2.getText()));
    }

    /**
     * Procedimiento que pulsa el botón de clasificar del menú y comprueba que la aplicación
     * haya cambiado a la actividad claveDicotomica.
     *
     * @name pulsarMenuClasificar
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void pulsarMenuClasificar() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(claveDicotomica);
        NavigationView nav = (NavigationView) claveDicotomica.findViewById(R.id.nav_view);
        MenuItem MenuItemClasificar = nav.getMenu().getItem(0).getSubMenu().getItem(1);
        // Click menu
        claveDicotomica.onNavigationItemSelected(MenuItemClasificar);
        Intent expectedIntent = new Intent(claveDicotomica, RecogerFoto.class);
        Intent actual = shadowActivity.getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    /**
     * Procedimiento que pulsa el botón de mostrar setas del menú y comprueba que la aplicación
     * haya cambiado a la actividad claveDicotomica.
     *
     * @name pulsarMenuMostrarSetas
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void pulsarMenuMostrarSetas() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(claveDicotomica);
        NavigationView nav = (NavigationView) claveDicotomica.findViewById(R.id.nav_view);
        MenuItem MenuItemMostrarSetas = nav.getMenu().getItem(0).getSubMenu().getItem(3);
        // Click menu
        claveDicotomica.onNavigationItemSelected(MenuItemMostrarSetas);
        Intent expectedIntent = new Intent(claveDicotomica, MostrarSetas.class);
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
        ShadowActivity shadowActivity = shadowOf(claveDicotomica);
        NavigationView nav = (NavigationView) claveDicotomica.findViewById(R.id.nav_view);
        MenuItem MenuItemIrClaves = nav.getMenu().getItem(0).getSubMenu().getItem(2);
        // Click menu
        claveDicotomica.onNavigationItemSelected(MenuItemIrClaves);
        Intent actual = shadowActivity.getNextStartedActivity();
        Intent expectedIntent = new Intent(claveDicotomica, MostrarClaves.class);

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
        ShadowActivity shadowActivity = shadowOf(claveDicotomica);
        NavigationView nav = (NavigationView) claveDicotomica.findViewById(R.id.nav_view);
        MenuItem MenuItemCambiarIdioma = nav.getMenu().getItem(1).getSubMenu().getItem(0);
        // Click cambiar idioma
        claveDicotomica.onNavigationItemSelected(MenuItemCambiarIdioma);
        Intent expectedIntent = new Intent(claveDicotomica, ClaveDicotomica.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que el idioma se cambie al inglés
        assertEquals("en", actual.getExtras().getString("idioma"));
    }

    /**
     * Procedimiento que pulsa un elemento de la lista.
     *
     * @name clickItem
     * @author Adrián Antón García
     * @category procedimiento test
     */

    public static void clickItem(AbsListView listView, int position) {
        ListAdapter adapter = listView.getAdapter();
        View itemView = adapter.getView(position, null, listView);
        listView.performItemClick(itemView, position, adapter.getItemId(position));
    }
}
