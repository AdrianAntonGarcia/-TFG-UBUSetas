package ubusetas.ubu.adrian.proyectoubusetas;



import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ubusetas.ubu.adrian.proyectoubusetas.lanzador.Lanzadora;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class PruebasExpressoActividadLanzadora {

    @Rule
    public ActivityTestRule<Lanzadora> lanzadora = new ActivityTestRule<>(Lanzadora.class);

    @Test
    public void comprobarAyudaLanzadora() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout_lanzadora))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open()); // Open Drawer

        // Start the screen of your activity.
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.menu_ayuda));

        onView(withText("Para clasificar una foto pulse sobre el icono de la lupa.")).check(matches(isDisplayed()));
        onView(withText("Para mostrar un listado de las setas disponibles pulse el icono de información.")).check(matches(isDisplayed()));
        onView(withText("Para mostrar un listado de las claves dicotómicas disponibles pulse el siguiente botón.")).check(matches(isDisplayed()));

    }

    @Test
    public void comprobarClasificarLanzadora() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout_lanzadora))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open()); // Open Drawer

        // Start the screen of your activity.
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.menu_clasificar));

        onView(withText("Seleccione una opción:")).check(matches(isDisplayed()));

    }

}