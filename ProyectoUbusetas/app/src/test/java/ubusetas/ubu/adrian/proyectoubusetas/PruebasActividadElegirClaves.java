package ubusetas.ubu.adrian.proyectoubusetas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.junit.AfterClass;
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
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.ClaveDicotomica;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.elegirclaves.ElegirClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.robolectric.Shadows.shadowOf;

/*
* @name: PruebasActividadMostrarSetas
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que prueba la actividad elegirClaves
* */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PruebasActividadElegirClaves {

    private ElegirClaves elegirClaves;
    private Bitmap bit;

    /*
    * @name: setupEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad ElegirClaves en español
    * para ser usada por los demás tests
    * */

    public void setupEs() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ArrayList<String> resultados = new ArrayList<String>();
        resultados.add("chalciporus piperatus ");
        resultados.add("chroogomphus rutilus ");
        resultados.add("tricholoma ustale ");
        resultados.add("lactarius rubidus ");
        resultados.add("suillus pungens ");
        intent.putExtra("idioma", "es");
        //resultados
        intent.putStringArrayListExtra("resultados", resultados);
        Lanzadora lanzadora = Robolectric.buildActivity(Lanzadora.class).create().get();
        AccesoDatosExternos acceso = new AccesoDatosExternos(lanzadora);
        bit = acceso.accesoImagenPorPath("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (1).jpg");
        intent.putExtra("fotoBitmap", bit);
        elegirClaves = Robolectric.buildActivity(ElegirClaves.class, intent).create().get();
    }

    /*
    * @name: setupEn
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad ElegirClaves en inglés
    * para ser usada por los demás tests
    * */

    public void setupEn() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ArrayList<String> resultados = new ArrayList<String>();
        resultados.add("chalciporus piperatus ");
        resultados.add("chroogomphus rutilus ");
        resultados.add("tricholoma ustale ");
        resultados.add("lactarius rubidus ");
        resultados.add("suillus pungens ");
        intent.putExtra("idioma", "en");
        //resultados
        intent.putStringArrayListExtra("resultados", resultados);
        Lanzadora lanzadora = Robolectric.buildActivity(Lanzadora.class).create().get();
        AccesoDatosExternos acceso = new AccesoDatosExternos(lanzadora);
        bit = acceso.accesoImagenPorPath("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (1).jpg");
        intent.putExtra("fotoBitmap", bit);
        elegirClaves = Robolectric.buildActivity(ElegirClaves.class, intent).create().get();
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
        TextView texto1 = (TextView) elegirClaves.findViewById(R.id.textView_elegir_dos_claves);
        assertEquals("Los siguientes géneros se encuentran en la clave dicotómica. Seleccione " +
                "los géneros sobre los que aplicar la clave dicotómica:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
        Button botonSeleccionar = (Button) elegirClaves.findViewById(R.id.boton_obtener);
        String textoSeleccionar = botonSeleccionar.getText().toString();
        assertEquals("Seleccionar", textoSeleccionar);
        assertFalse("texto".equals(textoSeleccionar));
    }

        /*
    * @name: seleccionarItems
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que prueba el correcto funcionamiento al pulsar sobre los items de la
    * lista
    * */

    @Test
    public void seleccionarItems() {
        setupEs();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        ArrayList<String> resultados = new ArrayList<String>();
        resultados.add("chalciporus piperatus ");
        resultados.add("chroogomphus rutilus ");
        resultados.add("tricholoma ustale ");
        resultados.add("lactarius rubidus ");
        resultados.add("suillus pungens ");
        intent.putExtra("idioma", "es");
        //resultados
        intent.putStringArrayListExtra("resultados", resultados);
        Lanzadora lanzadora = Robolectric.buildActivity(Lanzadora.class).create().get();
        AccesoDatosExternos acceso = new AccesoDatosExternos(lanzadora);
        bit = acceso.accesoImagenPorPath("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (1).jpg");
        intent.putExtra("fotoBitmap", bit);
        ActivityController<ElegirClaves> activityController = Robolectric.buildActivity(ElegirClaves.class,intent);
        activityController.create().start().visible();
        ShadowActivity shadowActivity = shadowOf(elegirClaves);

        ShadowActivity myActivityShadow = shadowOf(activityController.get());
        //Compruebo que al pulsar todos los elementos se
        RecyclerView currentRecyclerView = ((RecyclerView) myActivityShadow.findViewById(R.id.recycler_view_lista_seleccion_claves));

        currentRecyclerView.getChildAt(0).performClick();
        Button botonSeleccionar = (Button) elegirClaves.findViewById(R.id.boton_obtener);
        botonSeleccionar.performClick();
        TextView texto1 = (TextView) elegirClaves.findViewById(R.id.textView_generos_selecionados);
        assertEquals("Géneros seleccionados: [] Todavía no se han seleccionado 2 o mas géneros.", texto1.getText());
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
        TextView texto1 = (TextView) elegirClaves.findViewById(R.id.textView_elegir_dos_claves);
        assertEquals("The following genres are in the dichotomous key. Select the genres on which to apply the dichotomous key:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));
        Button botonSeleccionar = (Button) elegirClaves.findViewById(R.id.boton_obtener);
        String textoSeleccionar = botonSeleccionar.getText().toString();
        assertEquals("Select", textoSeleccionar);
        assertFalse("texto".equals(textoSeleccionar));
    }

    /*
    * @name: pulsarMenuClasificar
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de clasificar del menú y comprueba que la aplicación
    * haya cambiado a la actividad elegirClaves
    * */
    @Test
    public void pulsarMenuClasificar() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(elegirClaves);
        NavigationView nav = (NavigationView) elegirClaves.findViewById(R.id.nav_view);
        MenuItem MenuItemClasificar = nav.getMenu().getItem(0).getSubMenu().getItem(1);
        // Click menu
        elegirClaves.onNavigationItemSelected(MenuItemClasificar);
        Intent expectedIntent = new Intent(elegirClaves, RecogerFoto.class);
        Intent actual = shadowActivity.getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    /*
    * @name: pulsarMenuMostrarSetas
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de mostrar setas del menú y comprueba que la aplicación
    * haya cambiado a la actividad elegirClaves
    * */

    @Test
    public void pulsarMenuMostrarSetas() {
        setupEs();
        ShadowActivity shadowActivity = shadowOf(elegirClaves);
        NavigationView nav = (NavigationView) elegirClaves.findViewById(R.id.nav_view);
        MenuItem MenuItemMostrarSetas = nav.getMenu().getItem(0).getSubMenu().getItem(3);
        // Click menu
        elegirClaves.onNavigationItemSelected(MenuItemMostrarSetas);
        Intent expectedIntent = new Intent(elegirClaves, MostrarSetas.class);
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
        ShadowActivity shadowActivity = shadowOf(elegirClaves);
        NavigationView nav = (NavigationView) elegirClaves.findViewById(R.id.nav_view);
        MenuItem MenuItemIrClaves = nav.getMenu().getItem(0).getSubMenu().getItem(2);
        // Click menu
        elegirClaves.onNavigationItemSelected(MenuItemIrClaves);
        Intent actual = shadowActivity.getNextStartedActivity();
        Intent expectedIntent = new Intent(elegirClaves, MostrarClaves.class);

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
        ShadowActivity shadowActivity = shadowOf(elegirClaves);
        NavigationView nav = (NavigationView) elegirClaves.findViewById(R.id.nav_view);
        MenuItem MenuItemCambiarIdioma = nav.getMenu().getItem(1).getSubMenu().getItem(0);
        // Click cambiar idioma
        elegirClaves.onNavigationItemSelected(MenuItemCambiarIdioma);
        Intent expectedIntent = new Intent(elegirClaves, ElegirClaves.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que el idioma se cambie al inglés
        assertEquals("en", actual.getExtras().getString("idioma"));
    }
}