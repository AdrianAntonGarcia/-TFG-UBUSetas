package ubusetas.ubu.adrian.proyectoubusetas.lanzador;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ubusetas.ubu.adrian.proyectoubusetas.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Clase que MostrarResultadosPulsarItems que se cargue una imágen correctamente desde la galería
 * del movil en la actividad RecogerFoto.
 *
 * @author Adrián Antón García
 * @name RecogerFotoPruebaGaleria
 * @category clase
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecogerFotoPruebaGaleria {

    @Rule
    public ActivityTestRule<Lanzadora> mActivityTestRule = new ActivityTestRule<>(Lanzadora.class);
    private UiDevice mDevice;

    /**
     * Procedimiento que inicializa el mDevice para tenecer acceso a los elementos
     * externos a la aplicación.
     *
     * @name setup
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Before
    public void setup() throws UiObjectNotFoundException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    /**
     * Procedimiento que MostrarResultadosPulsarItems que se cargue una imágen correctamente desde la galería
     * del movil en la actividad RecogerFoto.
     *
     * @name recogerFotoGaleria
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void recogerFotoGaleria() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.boton_clasificar_lanzadora),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                3),
                        isDisplayed()));
        floatingActionButton.perform(click());


        //Botón desde la galería

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.boton_galeria),
                        childAtPosition(
                                allOf(withId(R.id.LinearLayout_botones_recoger_foto),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        mDevice.waitForIdle(5000);
        mDevice.click(300, 1548);
        mDevice.waitForIdle(5000);
        mDevice.click(282, 1720);
        mDevice.waitForIdle(5000);
        mDevice.click(156, 324);

        //Botón clasificar

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.boton_clasificar),
                        childAtPosition(
                                allOf(withId(R.id.LinearLayout_botones_recoger_foto),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                1)),
                                3),
                        isDisplayed()));
        floatingActionButton4.perform(click());


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
