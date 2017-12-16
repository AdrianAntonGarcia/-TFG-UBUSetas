package ubusetas.ubu.adrian.proyectoubusetas;

/**
 * Created by adrit on 11/12/2017.
 */

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;

import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


/*
* @name: PruebasActividadLanzadora
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que prueba la actividad lanzadora
* */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PruebasActividadLanzadora {


    private Lanzadora lanzadora;

       /*
    * @name: setupEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad lanzadora en español
    * para ser usada por los demás tests
    * */

    public void setupEs() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "es");
        lanzadora = Robolectric.buildActivity(Lanzadora.class, intent).create().get();
    }

    /*
    * @name: setupEn
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad lanzadora en inglés
    * para ser usada por los demás tests
    * */

    public void setupEn() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "en");
        lanzadora = Robolectric.buildActivity(Lanzadora.class, intent).create().get();
    }
    /*
    * @name: pulsarClasificar
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de clasificar y comprueba que la aplicación
    * haya cambiado a la actividad recogerFoto
    * */

    @Test
    public void pulsarClasificar() {
        setupEs();
        lanzadora.findViewById(R.id.boton_clasificar_lanzadora).performClick();
        Intent expectedIntent = new Intent(lanzadora, RecogerFoto.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());

    }

    /*
    * @name: pulsarMenuClasificar
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de clasificar del menú y comprueba que la aplicación
    * haya cambiado a la actividad recogerFoto
    * */
    @Test
    public void pulsarMenuClasificar(){
        setupEs();
        ShadowActivity shadowActivity = Shadows.shadowOf(lanzadora);

        NavigationView nav = (NavigationView) lanzadora.findViewById(R.id.nav_view);
        MenuItem MenuItemClasificar = nav.getMenu().getItem(0).getSubMenu().getItem(1);
        // Click menu
        lanzadora.onNavigationItemSelected(MenuItemClasificar);
        Intent expectedIntent = new Intent(lanzadora, RecogerFoto.class);
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
    public void pulsarMenuIrClaves(){
        setupEs();
        ShadowActivity shadowActivity = Shadows.shadowOf(lanzadora);
        NavigationView nav = (NavigationView) lanzadora.findViewById(R.id.nav_view);
        MenuItem MenuItemIrClaves = nav.getMenu().getItem(0).getSubMenu().getItem(2);
        // Click menu
        lanzadora.onNavigationItemSelected(MenuItemIrClaves);
        Intent expectedIntent = new Intent(lanzadora, MostrarClaves.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    /*
    * @name: pulsarMenuMostrarSetas
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón del menú de mostrar setas y comprueba que la aplicación
    * haya cambiado a la actividad mostrar setas
    * */
    @Test
    public void pulsarMenuMostrarSetas(){
        setupEs();
        ShadowActivity shadowActivity = Shadows.shadowOf(lanzadora);
        NavigationView nav = (NavigationView) lanzadora.findViewById(R.id.nav_view);
        MenuItem MenuItemMostrarSetas = nav.getMenu().getItem(0).getSubMenu().getItem(3);
        // Click menu
        lanzadora.onNavigationItemSelected(MenuItemMostrarSetas);
        Intent expectedIntent = new Intent(lanzadora, MostrarSetas.class);
        Intent actual = shadowActivity.getNextStartedActivity();
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
    public void pulsarMenuCambiarIdioma(){
        setupEs();
        ShadowActivity shadowActivity = Shadows.shadowOf(lanzadora);
        NavigationView nav = (NavigationView) lanzadora.findViewById(R.id.nav_view);
        MenuItem MenuItemCambiarIdioma = nav.getMenu().getItem(1).getSubMenu().getItem(0);
        // Click cambiar idioma
        lanzadora.onNavigationItemSelected(MenuItemCambiarIdioma);
        Intent expectedIntent = new Intent(lanzadora, Lanzadora.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que el idioma se cambie al inglés
        assertEquals("en",actual.getExtras().getString("idioma"));
        setupEs();
    }

    /*
    * @name: pulsarMostrarSetas
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de mostrar setas y comprueba que la aplización
    * haya cambiado de actividad
    * */

    @Test
    public void pulsarMostrarSetas() {
        setupEs();
        lanzadora.findViewById(R.id.boton_ir_setas_lanzadora).performClick();
        Intent expectedIntent = new Intent(lanzadora, MostrarSetas.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

        /*
    * @name: pulsarIrClaves
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el botón de ir claves y comprueba que la aplicación
    * haya cambiado de actividad
    * */

    @Test
    public void pulsarIrClaves() {
        setupEs();
        lanzadora.findViewById(R.id.boton_ir_claves_lanzadora).performClick();
        Intent expectedIntent = new Intent(lanzadora, MostrarClaves.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    /*
    * @name: comprobarTextosEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que comprueba que los textos se muestren es español
    * si la aplicación se ejecuta en ese idioma
    * */

    @Test
    @Config(qualifiers="es")
    public void comprobarTextosEs() {
        setupEs();
        Lanzadora lanzadora = Robolectric.buildActivity(Lanzadora.class).create().get();
        //Textos de la página contenedora
        TextView textoClasificar = (TextView) lanzadora.findViewById(R.id.textView_clasificar);
        assertEquals(textoClasificar.getText(),"Clasificar");
        TextView textoMostrarSetas = (TextView) lanzadora.findViewById(R.id.textView_ir_setas);
        assertEquals(textoMostrarSetas.getText(),"Mostrar Setas");
        TextView textoIrClaves = (TextView) lanzadora.findViewById(R.id.textView_ir_claves);
        assertEquals(textoIrClaves.getText(),"Ir claves");
        //Textos del menú
        NavigationView nav = (NavigationView) lanzadora.findViewById(R.id.nav_view);
        assertNotNull(nav.getMenu());
        Menu menu= nav.getMenu();
        MenuItem item= menu.getItem(0);
        assertNotNull(item);
        MenuItem MenuItemHome = item.getSubMenu().getItem(0);
        MenuItem MenuItemClasificar = item.getSubMenu().getItem(1);
        MenuItem MenuItemIrClaves= item.getSubMenu().getItem(2);
        MenuItem MenuItemMostrarSetas = item.getSubMenu().getItem(3);
        assertEquals(item.getTitle(),"Herramientas");
        assertEquals(MenuItemHome.getTitle(),"Home");
        assertEquals(MenuItemClasificar.getTitle(),"Clasificar");
        assertEquals(MenuItemIrClaves.getTitle(),"Ir claves dicotómicas");
        assertEquals(MenuItemMostrarSetas.getTitle(),"Información setas");
        item= menu.getItem(1);
        assertNotNull(item);
        MenuItem MenuItemIdioma = item.getSubMenu().getItem(0);
        MenuItem MenuItemAyuda = item.getSubMenu().getItem(1);
        assertEquals(item.getTitle(),"Opciones");
        assertEquals(MenuItemIdioma.getTitle(),"Cambiar idioma");
        assertEquals(MenuItemAyuda.getTitle(),"Ayuda");


    }

        /*
    * @name: comprobarTextosEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que comprueba que los textos se muestren es español
    * si la aplicación se ejecuta en ese idioma
    * */

    @Test
    @Config(qualifiers="en")
    public void comprobarTextosEn() {
        setupEn();
        //Textos de la página contenedora
        Lanzadora lanzadora = Robolectric.buildActivity(Lanzadora.class).create().get();
        TextView textoClasificar = (TextView) lanzadora.findViewById(R.id.textView_clasificar);
        assertEquals(textoClasificar.getText(),"Classify");
        TextView textoMostrarSetas = (TextView) lanzadora.findViewById(R.id.textView_ir_setas);
        assertEquals(textoMostrarSetas.getText(),"Show mushrooms");
        TextView textoIrClaves = (TextView) lanzadora.findViewById(R.id.textView_ir_claves);
        assertEquals(textoIrClaves.getText(),"Go keys");
        //Textos del menú
        NavigationView nav = (NavigationView) lanzadora.findViewById(R.id.nav_view);
        assertNotNull(nav.getMenu());
        Menu menu= nav.getMenu();
        MenuItem item= menu.getItem(0);
        assertNotNull(item);
        MenuItem MenuItemHome = item.getSubMenu().getItem(0);
        MenuItem MenuItemClasificar = item.getSubMenu().getItem(1);
        MenuItem MenuItemIrClaves= item.getSubMenu().getItem(2);
        MenuItem MenuItemMostrarSetas = item.getSubMenu().getItem(3);
        assertEquals(item.getTitle(),"Tools");
        assertEquals(MenuItemHome.getTitle(),"Home");
        assertEquals(MenuItemClasificar.getTitle(),"Classify");
        assertEquals(MenuItemIrClaves.getTitle(),"Go dichotomous keys");
        assertEquals(MenuItemMostrarSetas.getTitle(),"Mushroom information");
        item= menu.getItem(1);
        assertNotNull(item);
        MenuItem MenuItemIdioma = item.getSubMenu().getItem(0);
        MenuItem MenuItemAyuda = item.getSubMenu().getItem(1);
        assertEquals(item.getTitle(),"Options");
        assertEquals(MenuItemIdioma.getTitle(),"Change idiom");
        assertEquals(MenuItemAyuda.getTitle(),"Help");
    }
}
