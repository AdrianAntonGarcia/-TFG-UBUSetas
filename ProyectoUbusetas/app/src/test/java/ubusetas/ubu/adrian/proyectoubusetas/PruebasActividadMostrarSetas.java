package ubusetas.ubu.adrian.proyectoubusetas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.ArrayList;

import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.tarjetassetas.TarjetaSeta;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static org.robolectric.Shadows.shadowOf;

/**
 * Clase que prueba la actividad mostrarSetas.
 *
 * @author Adrián Antón García
 * @name PruebasActividadMostrarSetas
 * @category clase
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PruebasActividadMostrarSetas {

    private MostrarSetas mostrarSetas;

    /**
     * Procedimiento que inicializa la actividad MostrarSetas en español
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
        mostrarSetas = Robolectric.buildActivity(MostrarSetas.class, intent).create().get();
    }

    /**
     * Procedimiento que inicializa la actividad MostrarSetas en inglés
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
        mostrarSetas = Robolectric.buildActivity(MostrarSetas.class, intent).create().get();
    }

    /**
     * Procedimiento que prueba selecciona todos los items de la lista y comprueba
     * que se cambie a la actividad mostrarInformacionSeta de forma correcta.
     *
     * @name seleccionarItems
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void seleccionarItems() {

        setupEs();
        ActivityController<MostrarSetas> activityController = Robolectric.buildActivity(MostrarSetas.class);
        activityController.create().start().visible();
        ShadowActivity shadowActivity = shadowOf(mostrarSetas);

        ShadowActivity myActivityShadow = shadowOf(activityController.get());
        //Compruebo que al pulsar todos los elementos se
        RecyclerView currentRecyclerView = ((RecyclerView) myActivityShadow.findViewById(R.id.recycler_view_lista_setas));

        currentRecyclerView.getChildAt(0).performClick();
        Intent expectedIntent = new Intent(mostrarSetas, MostrarInformacionSeta.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());

    }

    /**
     * Procedimiento que prueba que se hayan cargado las tarjetas correctamente.
     *
     * @name testInicializarTarjetas
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void testInicializarTarjetas() {
        setupEs();
        AccesoDatosExternos acceso = new AccesoDatosExternos(mostrarSetas);
        String[] nombres = mostrarSetas.getResources().getStringArray(R.array.nombres_setas);
        int[] colors = mostrarSetas.getResources().getIntArray(R.array.initial_colors_mostrar_setas);
        ArrayList<TarjetaSeta> listaTarjetas = mostrarSetas.listaTarjetaSetas;
        int i = 0;
        Bitmap bit;
        String path;
        for (TarjetaSeta tarjeta : listaTarjetas) {
            //Compruebo el nombre
            assertEquals(nombres[i], tarjeta.getName());
            //El color
            assertEquals(colors[i], tarjeta.getColorResource());
            path = "imagenesSetas/" + nombres[i].toLowerCase() + "/" + nombres[i].toLowerCase().trim() + " " + "(" + 1 + ")" + ".jpg";
            bit = acceso.accesoImagenPorPath(path);
            //La foro cargada
            assertEquals(bit.getPixel(12, 12), tarjeta.getImagenSeta().getPixel(12, 12));
            i++;
        }
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
        TextView texto = (TextView) mostrarSetas.findViewById(R.id.textView_contenido_mostrar_setas);
        assertEquals("Seleccione una tarjeta para mostrar la información de esa seta:", texto.getText());
        assertFalse("texto".equals(texto.getText()));
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
        TextView texto = (TextView) mostrarSetas.findViewById(R.id.textView_contenido_mostrar_setas);
        assertEquals("Select a card to display the information of that mushroom:", texto.getText());
        assertFalse("texto".equals(texto.getText()));
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
        ShadowActivity shadowActivity = shadowOf(mostrarSetas);
        NavigationView nav = (NavigationView) mostrarSetas.findViewById(R.id.nav_view);
        MenuItem MenuItemClasificar = nav.getMenu().getItem(0).getSubMenu().getItem(1);
        // Click menu
        mostrarSetas.onNavigationItemSelected(MenuItemClasificar);
        Intent expectedIntent = new Intent(mostrarSetas, RecogerFoto.class);
        Intent actual = shadowActivity.getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
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
        ShadowActivity shadowActivity = shadowOf(mostrarSetas);
        NavigationView nav = (NavigationView) mostrarSetas.findViewById(R.id.nav_view);
        MenuItem MenuItemMostrarSetas = nav.getMenu().getItem(0).getSubMenu().getItem(3);
        // Click menu
        mostrarSetas.onNavigationItemSelected(MenuItemMostrarSetas);
        Intent actual = shadowActivity.getNextStartedActivity();
        //No cambia la actividad
        assertNull(actual);
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
        ShadowActivity shadowActivity = shadowOf(mostrarSetas);
        NavigationView nav = (NavigationView) mostrarSetas.findViewById(R.id.nav_view);
        MenuItem MenuItemIrClaves = nav.getMenu().getItem(0).getSubMenu().getItem(2);
        // Click menu
        mostrarSetas.onNavigationItemSelected(MenuItemIrClaves);
        Intent expectedIntent = new Intent(mostrarSetas, MostrarClaves.class);
        Intent actual = shadowActivity.getNextStartedActivity();
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
        ShadowActivity shadowActivity = shadowOf(mostrarSetas);
        NavigationView nav = (NavigationView) mostrarSetas.findViewById(R.id.nav_view);
        MenuItem MenuItemCambiarIdioma = nav.getMenu().getItem(1).getSubMenu().getItem(0);
        // Click cambiar idioma
        mostrarSetas.onNavigationItemSelected(MenuItemCambiarIdioma);
        Intent expectedIntent = new Intent(mostrarSetas, MostrarSetas.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que el idioma se cambie al inglés
        assertEquals("es", actual.getExtras().getString("idioma"));
    }
}
