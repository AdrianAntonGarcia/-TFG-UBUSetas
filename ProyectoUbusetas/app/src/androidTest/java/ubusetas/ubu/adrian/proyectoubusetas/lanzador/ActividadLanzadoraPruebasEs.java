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

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Clase que MostrarResultadosPulsarItems que los textos de la actividad lanzadora se muestren correctamente en español,
 * tanto los de la actividad, como los de la ayuda y los del menú.
 *
 * @author Adrián Antón García
 * @name ActividadLanzadoraPruebasEs
 * @category clase
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActividadLanzadoraPruebasEs {

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
     * Procedimiento que MostrarResultadosPulsarItems que los textos de la actividad lanzadora se muestren correctamente en español,
     * tanto los de la actividad, como los de la ayuda y los del menú.
     *
     * @name probarTextosEs
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void probarTextosEs() {


        ViewInteraction clasificar = onView(withId(R.id.textView_clasificar));

        clasificar.check(matches(withText("Clasificar")));

        ViewInteraction irSetas = onView(withId(R.id.textView_ir_setas));

        irSetas.check(matches(withText("Mostrar Setas")));

        ViewInteraction irClaves = onView(withId(R.id.textView_ir_claves));

        irClaves.check(matches(withText("Ir claves")));


        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Ayuda"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView0), withText("Ayuda de la actividad principal:"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Ayuda de la actividad principal:")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView1), withText("Para clasificar una foto pulse sobre el icono de la lupa."),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Para clasificar una foto pulse sobre el icono de la lupa.")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView1),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView2), withText("Para mostrar un listado de las setas disponibles pulse el icono de información."),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        textView3.check(matches(withText("Para mostrar un listado de las setas disponibles pulse el icono de información.")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.imageView2),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView3), withText("Para mostrar un listado de las claves dicotómicas disponibles pulse el siguiente botón."),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        textView4.check(matches(withText("Para mostrar un listado de las claves dicotómicas disponibles pulse el siguiente botón.")));

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.imageView3),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                6),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView4), withText("Para mostrar el menú de la aplicación pulse el siguiente botón."),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                7),
                        isDisplayed()));
        textView5.check(matches(withText("Para mostrar el menú de la aplicación pulse el siguiente botón.")));

        ViewInteraction imageView4 = onView(
                allOf(withId(R.id.imageView4),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                8),
                        isDisplayed()));
        imageView4.check(matches(isDisplayed()));

        ViewInteraction imageView5 = onView(
                allOf(withId(R.id.imageView4),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                8),
                        isDisplayed()));
        imageView5.check(matches(isDisplayed()));
        mDevice.waitForIdle(5000);
        mDevice.click(300, 1548);

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_lanzadora),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withText("Herramientas"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("Herramientas")));

        ViewInteraction checkedTextView = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        2),
                                0),
                        isDisplayed()));
        checkedTextView.check(matches(isDisplayed()));

        ViewInteraction checkedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        3),
                                0),
                        isDisplayed()));
        checkedTextView2.check(matches(isDisplayed()));

        ViewInteraction checkedTextView3 = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        4),
                                0),
                        isDisplayed()));
        checkedTextView3.check(matches(isDisplayed()));

        ViewInteraction checkedTextView4 = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        5),
                                0),
                        isDisplayed()));
        checkedTextView4.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withText("Opciones"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                7),
                        isDisplayed()));
        textView7.check(matches(withText("Opciones")));

        ViewInteraction checkedTextView5 = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        8),
                                0),
                        isDisplayed()));
        checkedTextView5.check(matches(isDisplayed()));

        ViewInteraction checkedTextView6 = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        9),
                                0),
                        isDisplayed()));
        checkedTextView6.check(matches(isDisplayed()));
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
