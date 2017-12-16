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
import ubusetas.ubu.adrian.proyectoubusetas.elegirclaves.ElegirClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;

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

    /*
    * @name: setupEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad mostrarInformaciónSetas en español
    * para ser usada por los demás tests
    * */

    public void setupEs() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "es");
        intent.putExtra("nombreSeta", "Agaricus urinascens");
        elegirClaves = Robolectric.buildActivity(ElegirClaves.class, intent).create().get();
    }

    /*
    * @name: setupEn
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad mostrarInformaciónSetas en inglés
    * para ser usada por los demás tests
    * */

    public void setupEn() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("idioma", "en");
        intent.putExtra("nombreSeta", "Agaricus urinascens");
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
        TextView texto1 = (TextView) elegirClaves.findViewById(R.id.textView_DescripcionSeta);
        assertEquals("Descripción seta:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));

        TextView texto2 = (TextView) elegirClaves.findViewById(R.id.textView_textoDescripcionSeta);
        assertEquals("\"Agaricus es un género de hongos que contiene tanto especies comestibles " +
                "como venenosas, con posiblemente más de 300 " +
                "miembros en todo el mundo. El género incluye el hongo común (\\\" botón \\ \") (Agaricus bisporus) y el" +
                " hongo de campo (Agaricus campestris), el cultivado dominante ", texto2.getText());
        assertFalse("texto".equals(texto2.getText()));

        TextView texto3 = (TextView) elegirClaves.findViewById(R.id.textView_GeneroSeta);
        assertEquals("Género seta:", texto3.getText());
        assertFalse("texto".equals(texto3.getText()));

        TextView texto4 = (TextView) elegirClaves.findViewById(R.id.textView_textoGeneroSeta);
        assertEquals("Agaricus", texto4.getText());
        assertFalse("texto".equals(texto4.getText()));

        TextView texto5 = (TextView) elegirClaves.findViewById(R.id.textView_ComestibilidadSeta);
        assertEquals("Comestibilidad seta:", texto5.getText());
        assertFalse("texto".equals(texto5.getText()));

        TextView texto6 = (TextView) elegirClaves.findViewById(R.id.textView_textoComestibilidadSeta);
        assertEquals("desconocido-", texto6.getText());
        assertFalse("texto".equals(texto6.getText()));

        TextView texto7 = (TextView) elegirClaves.findViewById(R.id.textView_EnlaceSeta);
        assertEquals("Enlace seta:", texto7.getText());
        assertFalse("texto".equals(texto7.getText()));

        TextView texto8 = (TextView) elegirClaves.findViewById(R.id.textView_textoEnlaceSeta);
        assertEquals("https://wikipedia.org/wiki?curid=1633043", texto8.getText().toString());
        assertFalse("texto".equals(texto8.getText()));
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
        TextView texto1 = (TextView) elegirClaves.findViewById(R.id.textView_DescripcionSeta);
        assertEquals("Mushroom description:", texto1.getText());
        assertFalse("texto".equals(texto1.getText()));

        TextView texto2 = (TextView) elegirClaves.findViewById(R.id.textView_textoDescripcionSeta);
        assertEquals("\"Agaricus is a genus of mushrooms containing both edible and poisonous " +
                "species, with possibly over 300 members worldwide. The genus includes the common " +
                "(\\\"button\\\") mushroom (Agaricus bisporus) and the field mushroom (Agaricus " +
                "campestris), the dominant cultivated mushrooms of the West. Members of Agaricus " +
                "are characterized by having a fleshy cap or pileus, from the underside of which " +
                "grow a number of radiating plates or gills on which are produced the naked spores. " +
                "They are distinguished from other members of their family, Agaricaceae, by their" +
                " chocolatebrown spores. Members of Agaricus also have a stem or stipe, which elevates" +
                " it above the object on which the mushroom grows, or substrate, and a partial veil, " +
                "which protects the developing gills and later forms a ring or annulus on the stalk.\"@en", texto2.getText());
        assertFalse("texto".equals(texto2.getText()));

        TextView texto3 = (TextView) elegirClaves.findViewById(R.id.textView_GeneroSeta);
        assertEquals("Mushroom genre:", texto3.getText());
        assertFalse("texto".equals(texto3.getText()));

        TextView texto4 = (TextView) elegirClaves.findViewById(R.id.textView_textoGeneroSeta);
        assertEquals("Agaricus", texto4.getText());
        assertFalse("texto".equals(texto4.getText()));

        TextView texto5 = (TextView) elegirClaves.findViewById(R.id.textView_ComestibilidadSeta);
        assertEquals("Mushroom edibility:", texto5.getText());
        assertFalse("texto".equals(texto5.getText()));

        TextView texto6 = (TextView) elegirClaves.findViewById(R.id.textView_textoComestibilidadSeta);
        //assertEquals("unknown-", texto6.getText());
        assertFalse("texto".equals(texto6.getText()));

        TextView texto7 = (TextView) elegirClaves.findViewById(R.id.textView_EnlaceSeta);
        assertEquals("Mushroom link:", texto7.getText());
        assertFalse("texto".equals(texto7.getText()));

        TextView texto8 = (TextView) elegirClaves.findViewById(R.id.textView_textoEnlaceSeta);
        assertEquals("https://wikipedia.org/wiki?curid=1633043", texto8.getText().toString());
        assertFalse("texto".equals(texto8.getText()));

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
        Intent expectedIntent = new Intent(elegirClaves, MostrarInformacionSeta.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que el idioma se cambie al inglés
        assertEquals("en", actual.getExtras().getString("idioma"));
    }
}
