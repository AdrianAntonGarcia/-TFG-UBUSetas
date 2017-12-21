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
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/*
* @name: MostrarInformacionSetaPruebasEn
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que prueba que se carguen todos los textos en inglés
* de la actividad MostrarInformacionSeta,
* tanto de la ayuda, como del menú y el propio contenido de la actividad.
* */


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MostrarInformacionSetaPruebasEn {

    @Rule
    public ActivityTestRule<Lanzadora> mActivityTestRule = new ActivityTestRule<>(Lanzadora.class);
    private UiDevice mDevice;

    /*
    * @name: setup
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que inicializa el mDevice para tenecer acceso a los elementos
    * externos a la aplicación.
    * */

    @Before
    public void setup() throws UiObjectNotFoundException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    /*
    * @name: mostrarInformacionSetaPruebasEn
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que prueba que se carguen todos los textos en inglés
    * de la actividad MostrarInformacionSeta,
    * tanto de la ayuda, como del menú y el propio contenido de la actividad.
    * */

    @Test
    public void mostrarInformacionSetaPruebasEn() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.boton_ir_setas_lanzadora),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                4),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_lista_setas),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_mostrar_informacion_setas),
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

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView_DescripcionSeta), withText("Mushroom description:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ScrollView01),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Mushroom description:")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView_textoDescripcionSeta), withText(" Agrocybe is a genus of mushrooms in the family Strophariaceae. Some species are poisonous. The genus has a widespread distribution, and contains about 100 species. @en"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ScrollView01),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText(" Agrocybe is a genus of mushrooms in the family Strophariaceae. Some species are poisonous. The genus has a widespread distribution, and contains about 100 species. @en")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView_GeneroSeta), withText("Mushroom genre:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ScrollView01),
                                        0),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("Mushroom genre:")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView_textoGeneroSeta), withText("Agrocybe"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ScrollView01),
                                        0),
                                3),
                        isDisplayed()));
        textView4.check(matches(withText("Agrocybe")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView_ComestibilidadSeta), withText("Mushroom edibility:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ScrollView01),
                                        0),
                                4),
                        isDisplayed()));
        textView5.check(matches(withText("Mushroom edibility:")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView_textoComestibilidadSeta), withText("unkonwn "),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ScrollView01),
                                        0),
                                5),
                        isDisplayed()));
        textView6.check(matches(withText("unkonwn ")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textView_EnlaceSeta), withText("Mushroom link:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ScrollView01),
                                        0),
                                6),
                        isDisplayed()));
        textView7.check(matches(withText("Mushroom link:")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.textView_textoEnlaceSeta), withText("https://wikipedia.org/wiki?curid=7264818"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ScrollView01),
                                        0),
                                7),
                        isDisplayed()));
        textView8.check(matches(withText("https://wikipedia.org/wiki?curid=7264818")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView_setaDescrita),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ScrollView01),
                                        0),
                                8),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Help"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.textView0), withText("Activity help show information:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView9.check(matches(withText("Activity help show information:")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.textView1), withText("The page will show information corresponding to the mushroom, a description, the genre, the edibility, a link to the wikipedia and      an image of this mushroom that can be enlarged if you click on it."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView10.check(matches(withText("The page will show information corresponding to the mushroom, a description, the genre, the edibility, a link to the wikipedia and      an image of this mushroom that can be enlarged if you click on it.")));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.textView2), withText("To show the application menu press the following button."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        textView11.check(matches(withText("To show the application menu press the following button.")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.imageView2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        mDevice.waitForIdle(5000);
        mDevice.click(171, 155);

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_mostrar_informacion_setas),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction textView12 = onView(
                allOf(withText("Tools"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()));
        textView12.check(matches(withText("Tools")));

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

        ViewInteraction textView13 = onView(
                allOf(withText("Options"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                7),
                        isDisplayed()));
        textView13.check(matches(withText("Options")));

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
