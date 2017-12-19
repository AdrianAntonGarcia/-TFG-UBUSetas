package ubusetas.ubu.adrian.proyectoubusetas.lanzador;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ubusetas.ubu.adrian.proyectoubusetas.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


/*
* @name: MostrarClavesPruebasPulsarTarjetas
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que pulsa las tarjetas de la actividad Mostrar claves y comprueba que se acceda a las actividades correctas,
* prueba que funcione correctamente en ambos idiomas de la aplicación.
* */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MostrarClavesPruebasPulsarTarjetas {

    @Rule
    public ActivityTestRule<Lanzadora> mActivityTestRule = new ActivityTestRule<>(Lanzadora.class);

    /*
     * @name: mostrarClavesPruebasPulsarTarjetas
     * @Author: Adrián Antón García
     * @category: procedimiento test
     * @Description: Procedimiento que pulsa las tarjetas de la actividad Mostrar claves y comprueba que se acceda a las actividades correctas,
     * prueba que funcione correctamente en ambos idiomas de la aplicación.
     * */

    @Test
    public void mostrarClavesPruebasPulsarTarjetas() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.boton_ir_claves_lanzadora),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                5),
                        isDisplayed()));
        floatingActionButton.perform(click());

        for(int i = 0; i<40;++i) {
            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.recycler_view_lista_claves),
                            childAtPosition(
                                    withClassName(is("android.widget.RelativeLayout")),
                                    1)));
            recyclerView.perform(actionOnItemAtPosition(i, click()));
            pressBack();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_mostrar_claves),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        8),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        for(int i = 0; i<40;++i) {
            ViewInteraction recyclerView = onView(
                    allOf(withId(R.id.recycler_view_lista_claves),
                            childAtPosition(
                                    withClassName(is("android.widget.RelativeLayout")),
                                    1)));
            recyclerView.perform(actionOnItemAtPosition(i, click()));
            pressBack();
        }

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
