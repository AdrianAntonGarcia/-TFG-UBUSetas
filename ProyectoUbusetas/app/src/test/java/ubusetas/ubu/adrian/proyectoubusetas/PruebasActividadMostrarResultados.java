package ubusetas.ubu.adrian.proyectoubusetas;

/**
 * Created by adrit on 11/12/2017.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;

import ubusetas.ubu.adrian.proyectoubusetas.basedatos.AccesoDatosExternos;
import ubusetas.ubu.adrian.proyectoubusetas.clasificador.RecogerFoto;
import ubusetas.ubu.adrian.proyectoubusetas.clavedicotomica.MostrarClaves;
import ubusetas.ubu.adrian.proyectoubusetas.elegirclaves.ElegirClaves;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarInformacionSeta;
import ubusetas.ubu.adrian.proyectoubusetas.informacion.MostrarSetas;
import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarComparativa;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.MostrarResultados;
import ubusetas.ubu.adrian.proyectoubusetas.resultados.SetasLista;

import static android.view.HapticFeedbackConstants.LONG_PRESS;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;


/*
* @name: PruebasActividadLanzadora
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que prueba la actividad lanzadora
* */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PruebasActividadMostrarResultados {
    private MostrarResultados mostrarResultados;
    private Bitmap bit;

    /*
    * @name: setup
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa la actividad mostrar resultados
    * para ser usada por los demás tests
    * */

    @Before
    public void setup() {
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
        bit = acceso.accesoImagenPorPath("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (1).jpg");
        intent.putExtra("fotoBitmap", bit);
        mostrarResultados = Robolectric.buildActivity(MostrarResultados.class, intent).create().get();
    }

    /*
    * @name: probarTextosEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que prueba los textos en españolde la actividad mostrar resultados y comprueba
    * que los resultados se visualicen de forma correcta en la actividad
    * */

    @Test
    @Config(qualifiers = "es")
    public void probarTextosEs() {

        TextView texto = (TextView) mostrarResultados.findViewById(R.id.TextView_ResultadosClasificador);
        assertEquals("Resultados:", texto.getText());

        ListView ListaResultados = (ListView) mostrarResultados.findViewById(R.id.listView_lista_resultados);
        assertEquals("chalciporus piperatus (35,1%)", ((SetasLista) ListaResultados.getAdapter().getItem(0)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(0)).getNombre()));
        assertEquals("chroogomphus rutilus (31,5%)", ((SetasLista) ListaResultados.getAdapter().getItem(1)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(1)).getNombre()));
        assertEquals("tricholoma ustale (17,7%)", ((SetasLista) ListaResultados.getAdapter().getItem(2)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(2)).getNombre()));
        assertEquals("lactarius rubidus (7,4%)", ((SetasLista) ListaResultados.getAdapter().getItem(3)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(3)).getNombre()));
        assertEquals("suillus pungens (2,2%)", ((SetasLista) ListaResultados.getAdapter().getItem(4)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(4)).getNombre()));
    }

        /*
    * @name: probarTextos
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que prueba los textos en inglés de la actividad mostrar resultados y comprueba
    * que los resultados se visualicen de forma correcta en la actividad
    * */

    @Test
    @Config(qualifiers = "en")
    public void probarTextosEn() {

        TextView texto = (TextView) mostrarResultados.findViewById(R.id.TextView_ResultadosClasificador);
        assertEquals("Results:", texto.getText());

        ListView ListaResultados = (ListView) mostrarResultados.findViewById(R.id.listView_lista_resultados);
        assertEquals("chalciporus piperatus (35,1%)", ((SetasLista) ListaResultados.getAdapter().getItem(0)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(0)).getNombre()));
        assertEquals("chroogomphus rutilus (31,5%)", ((SetasLista) ListaResultados.getAdapter().getItem(1)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(1)).getNombre()));
        assertEquals("tricholoma ustale (17,7%)", ((SetasLista) ListaResultados.getAdapter().getItem(2)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(2)).getNombre()));
        assertEquals("lactarius rubidus (7,4%)", ((SetasLista) ListaResultados.getAdapter().getItem(3)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(3)).getNombre()));
        assertEquals("suillus pungens (2,2%)", ((SetasLista) ListaResultados.getAdapter().getItem(4)).getNombre());
        assertFalse("texto".equals(((SetasLista) ListaResultados.getAdapter().getItem(4)).getNombre()));
    }

        /*
    * @name: pulsarClaveFiltrada
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el boton de ir a claves y comprueba que el cambio de
    * actividad sea correcto enviando la información correcta.
    * */

    @Test
    public void pulsarClaveFiltrada() {

        mostrarResultados.findViewById(R.id.boton_clave).performClick();
        Intent expectedIntent = new Intent(mostrarResultados, ElegirClaves.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        assertEquals("[chalciporus piperatus , chroogomphus rutilus , tricholoma ustale , lactarius rubidus , suillus pungens ]", actual.getExtras().getStringArrayList("resultados").toString());
        assertFalse("texto".equals(actual.getExtras().getStringArrayList("resultados").toString()));
        assertEquals("[[117] chalciporus piperatus (35,1%),  [87] chroogomphus rutilus (31,5%),  [67] tricholoma ustale (17,7%),  [103] lactarius rubidus (7,4%),  [93] suillus pungens (2,2%)]", actual.getExtras().getStringArrayList("resultadosAdevolver").toString());
        assertFalse("texto".equals(actual.getExtras().getStringArrayList("resultados").toString()));
        assertEquals(bit, actual.getExtras().getParcelable("fotoBitmap"));
    }

    /*
    * @name: probarCargarListas
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que comprueba que el metodo cargarListas funciona correctamente
    * */

    @Test
    public void probarCargarListas() {
        assertEquals("[chalciporus piperatus (35,1%), chroogomphus rutilus (31,5%), tricholoma ustale (17,7%), lactarius rubidus (7,4%), suillus pungens (2,2%)]", mostrarResultados.resultadosSinNum.toString());
        assertFalse("texto".equals(mostrarResultados.resultadosSinNum.toString()));
        assertEquals("[chalciporus piperatus , chroogomphus rutilus , tricholoma ustale , lactarius rubidus , suillus pungens ]", mostrarResultados.nombresSetas.toString());
        assertFalse("texto".equals(mostrarResultados.resultadosSinNum));
    }

    /*
    * @name: probarCargarListaElementos
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que comprueba que el metodo cargarListaElementos funciona correctamente, comprobando
    * si los array de listas se guardan correctamente.
    * */

    @Test
    public void probarCargarListaElementos() {
        //Elemento 1
        assertEquals("chalciporus piperatus (35,1%)", mostrarResultados.listaSetas.get(0).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/chalciporus piperatus/chalciporus piperatus (1).jpg", mostrarResultados.listaSetas.get(0).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        //Elemento 2
        assertEquals("chroogomphus rutilus (31,5%)", mostrarResultados.listaSetas.get(1).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (1).jpg", mostrarResultados.listaSetas.get(1).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        //Elemento 3
        assertEquals("tricholoma ustale (17,7%)", mostrarResultados.listaSetas.get(2).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/tricholoma ustale/tricholoma ustale (1).jpg", mostrarResultados.listaSetas.get(2).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        //Elemento 4
        assertEquals("lactarius rubidus (7,4%)", mostrarResultados.listaSetas.get(3).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/lactarius rubidus/lactarius rubidus (1).jpg", mostrarResultados.listaSetas.get(3).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        //Elemento 5
        assertEquals("suillus pungens (2,2%)", mostrarResultados.listaSetas.get(4).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/suillus pungens/suillus pungens (1).jpg", mostrarResultados.listaSetas.get(4).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));

    }

    /*
    * @name: pulsarBotonRefrescar
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa el boton de refrescar y comprueba los elementos de la lista
    * se actualicen correctamente
    * */

    @Test
    public void pulsarBotonRefrescar() {

        mostrarResultados.findViewById(R.id.boton_refrescar_resultados).performClick();
        Intent expectedIntent = new Intent(mostrarResultados, MostrarResultados.class);
        assertEquals(mostrarResultados.posImagenSeta, 2);

        //Elemento 1
        assertEquals("chalciporus piperatus (35,1%)", mostrarResultados.listaSetas.get(0).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/chalciporus piperatus/chalciporus piperatus (2).jpg", mostrarResultados.listaSetas.get(0).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        //Elemento 2
        assertEquals("chroogomphus rutilus (31,5%)", mostrarResultados.listaSetas.get(1).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/chroogomphus rutilus/chroogomphus rutilus (2).jpg", mostrarResultados.listaSetas.get(1).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        //Elemento 3
        assertEquals("tricholoma ustale (17,7%)", mostrarResultados.listaSetas.get(2).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/tricholoma ustale/tricholoma ustale (2).jpg", mostrarResultados.listaSetas.get(2).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        //Elemento 4
        assertEquals("lactarius rubidus (7,4%)", mostrarResultados.listaSetas.get(3).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/lactarius rubidus/lactarius rubidus (2).jpg", mostrarResultados.listaSetas.get(3).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        //Elemento 5
        assertEquals("suillus pungens (2,2%)", mostrarResultados.listaSetas.get(4).getNombre());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
        assertEquals("imagenesSetas/suillus pungens/suillus pungens (2).jpg", mostrarResultados.listaSetas.get(4).getPath());
        assertFalse("texto".equals(mostrarResultados.listaSetas.get(0).getNombre()));
    }

    /*
    * @name: pulsarItemLista
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que comprueba que se cambie de actividad a mostrar comparativa al pulsar cada item de la lista
    * */

    @Test
    public void pulsarItemLista() {
        Intent expectedIntent = new Intent(mostrarResultados, MostrarComparativa.class);
        ListView ListaResultados = (ListView) mostrarResultados.findViewById(R.id.listView_lista_resultados);
        clickItem(ListaResultados, 0);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        clickItem(ListaResultados, 1);
        actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        clickItem(ListaResultados, 2);
        actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        clickItem(ListaResultados, 3);
        actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        clickItem(ListaResultados, 4);
        actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        assertEquals("[[117] chalciporus piperatus (35,1%),  [87] chroogomphus rutilus (31,5%),  [67] tricholoma ustale (17,7%),  [103] lactarius rubidus (7,4%),  [93] suillus pungens (2,2%)]", actual.getExtras().getStringArrayList("resultados").toString());
        assertFalse("texto".equals(actual.getExtras().getStringArrayList("resultados").toString()));
        assertEquals("[[117] chalciporus piperatus (35,1%),  [87] chroogomphus rutilus (31,5%),  [67] tricholoma ustale (17,7%),  [103] lactarius rubidus (7,4%),  [93] suillus pungens (2,2%)]", actual.getExtras().getStringArrayList("resultadosAdevolver").toString());
        assertFalse("texto".equals(actual.getExtras().getStringArrayList("resultadosAdevolver").toString()));
    }

        /*
    * @name: mantenerPulsadoItemLista
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que comprueba que se cambie de actividad a mostrar Informacion Seta
    * al mantener pulsado cada item de la lista
    * */

    @Test
    public void mantenerPulsadoItemLista() {
        mostrarResultados.findViewById(R.id.boton_refrescar_resultados).performClick();
        Intent expectedIntent = new Intent(mostrarResultados, MostrarInformacionSeta.class);
        ListView ListaResultados = (ListView) mostrarResultados.findViewById(R.id.listView_lista_resultados);
        longClickItem(ListaResultados, 0);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        longClickItem(ListaResultados, 1);
        actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        longClickItem(ListaResultados, 2);
        actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        longClickItem(ListaResultados, 3);
        actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        longClickItem(ListaResultados, 4);
        actual = ShadowApplication.getInstance().getNextStartedActivity();
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que haya cambiado a la actividad mostrarComparativa
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        assertEquals(1, actual.getExtras().get("actMostrarResultados"));
        assertFalse("texto".equals(actual.getExtras().getStringArrayList("resultados").toString()));
        assertEquals("[[117] chalciporus piperatus (35,1%),  [87] chroogomphus rutilus (31,5%),  [67] tricholoma ustale (17,7%),  [103] lactarius rubidus (7,4%),  [93] suillus pungens (2,2%)]", actual.getExtras().getStringArrayList("resultados").toString());
        assertFalse("texto".equals(actual.getExtras().getStringArrayList("resultados").toString()));
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
        ShadowActivity shadowActivity = Shadows.shadowOf(mostrarResultados);
        NavigationView nav = (NavigationView) mostrarResultados.findViewById(R.id.nav_view);
        MenuItem MenuItemClasificar = nav.getMenu().getItem(0).getSubMenu().getItem(1);
        // Click menu
        mostrarResultados.onNavigationItemSelected(MenuItemClasificar);
        Intent expectedIntent = new Intent(mostrarResultados, RecogerFoto.class);
        Intent actual = shadowActivity.getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
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

        ShadowActivity shadowActivity = Shadows.shadowOf(mostrarResultados);
        NavigationView nav = (NavigationView) mostrarResultados.findViewById(R.id.nav_view);
        MenuItem MenuItemMostrarSetas = nav.getMenu().getItem(0).getSubMenu().getItem(3);
        // Click menu
        mostrarResultados.onNavigationItemSelected(MenuItemMostrarSetas);
        Intent expectedIntent = new Intent(mostrarResultados, MostrarSetas.class);
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
        ShadowActivity shadowActivity = Shadows.shadowOf(mostrarResultados);
        NavigationView nav = (NavigationView) mostrarResultados.findViewById(R.id.nav_view);
        MenuItem MenuItemIrClaves = nav.getMenu().getItem(0).getSubMenu().getItem(2);
        // Click menu
        mostrarResultados.onNavigationItemSelected(MenuItemIrClaves);
        Intent expectedIntent = new Intent(mostrarResultados, MostrarClaves.class);
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
    public void pulsarMenuCambiarIdioma() {
        ShadowActivity shadowActivity = Shadows.shadowOf(mostrarResultados);
        NavigationView nav = (NavigationView) mostrarResultados.findViewById(R.id.nav_view);
        MenuItem MenuItemCambiarIdioma = nav.getMenu().getItem(1).getSubMenu().getItem(0);
        // Click cambiar idioma
        mostrarResultados.onNavigationItemSelected(MenuItemCambiarIdioma);
        Intent expectedIntent = new Intent(mostrarResultados, MostrarResultados.class);
        Intent actual = shadowActivity.getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        //Compruebo que el idioma se cambie al inglés
        assertEquals("en", actual.getExtras().getString("idioma"));
        setup();
    }

    /*
    * @name: clickItem
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que pulsa un elemento de la lista
    * */
    public static void clickItem(AbsListView listView, int position) {
        ListAdapter adapter = listView.getAdapter();
        View itemView = adapter.getView(position, null, listView);
        listView.performItemClick(itemView, position, adapter.getItemId(position));
    }

    /*
    * @name: longClickItem
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que mantiene pulsado un elemento de la lista
    * */

    public static void longClickItem(AbsListView listView, int position) {
        if (!listView.isLongClickable())
            return;
        AdapterView.OnItemLongClickListener listener = listView.getOnItemLongClickListener();
        if (listener == null)
            return;
        ListAdapter adapter = listView.getAdapter();
        View itemView = adapter.getView(position, null, listView);
        listener.onItemLongClick(listView, itemView, position, adapter.getItemId(position));
        listView.performHapticFeedback(LONG_PRESS);
    }
}
