package ubusetas.ubu.adrian.proyectoubusetas.lanzador;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DataInteraction;
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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ubusetas.ubu.adrian.proyectoubusetas.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
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
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

/*
* @name: ClaveDicotomicaPruebasEs
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que prueba que los textos de la actividad ClaveDicotomica se muestren correctamente en español,
* así como los textos de la clave según se vaya avanzando a través de ella.
* */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ClaveDicotomicaPruebasEs {

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
    * @name: claveDicotomicaPruebasEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que prueba que los textos de la actividad ClaveDicotomica se muestren correctamente en español,
    * así como los textos de la clave según se vaya avanzando a través de ella.
    * */

    @Test
    public void claveDicotomicaPruebasEs() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.boton_ir_claves_lanzadora),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                5),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_lista_claves),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.TextView_ClaveMostrada), withText("general"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("general")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.TextView_Pregunta_clave), withText("Seleccione la clave que mas se adecue a la seta:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Seleccione la clave que mas se adecue a la seta:")));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("Seta con forma típica de paraguas (pie y sombrero circular y aplanado):"),
                        childAtPosition(
                                allOf(withId(R.id.listView_claveDicotomica),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                2)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Seta con forma típica de paraguas (pie y sombrero circular y aplanado):")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("Seta sin forma típica, si es con sombrero, este es irregular :"),
                        childAtPosition(
                                allOf(withId(R.id.listView_claveDicotomica),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                2)),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("Seta sin forma típica, si es con sombrero, este es irregular :")));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.boton_anterior),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                3),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView_claveDicotomica),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(0);
        appCompatTextView.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.boton_anterior),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                3),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView_claveDicotomica),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(1);
        appCompatTextView2.perform(click());

        DataInteraction appCompatTextView3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView_claveDicotomica),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(1);
        appCompatTextView3.perform(click());

        DataInteraction appCompatTextView4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView_claveDicotomica),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(1);
        appCompatTextView4.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.TextView_ClaveMostrada), withText("general"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("general")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.TextView_Pregunta_clave), withText("Género clasificado: Helvella"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("Género clasificado: Helvella")));

        ViewInteraction imageButton3 = onView(
                allOf(withId(R.id.boton_anterior),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                3),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.boton_anterior),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                3),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.TextView_Pregunta_clave), withText("Seleccione la clave que mas se adecue a la seta:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("Seleccione la clave que mas se adecue a la seta:")));

        ViewInteraction imageButton4 = onView(
                allOf(withId(R.id.boton_anterior),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                3),
                        isDisplayed()));
        imageButton4.check(matches(isDisplayed()));

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.title), withText("Ayuda"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.textView0), withText("Ayuda de la actividad clave dicotómica:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView8.check(matches(withText("Ayuda de la actividad clave dicotómica:")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.textView1), withText("La página mostrará una sucesión de dilemas, deberá ir eligiendo el que considere que más se adecua a la seta hasta llegar a un género."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView9.check(matches(withText("La página mostrará una sucesión de dilemas, deberá ir eligiendo el que considere que más se adecua a la seta hasta llegar a un género.")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.textView2), withText("Para volver a la pregunta anterior pulse el siguiente botón."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        textView10.check(matches(withText("Para volver a la pregunta anterior pulse el siguiente botón.")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.textView3), withText("Para ir a la clave de un género en concreto, pulsar el siguiente botón (Aparecera si el género clasificado se encuentra entre las claves disponibles)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        textView11.check(matches(withText("Para ir a la clave de un género en concreto, pulsar el siguiente botón (Aparecera si el género clasificado se encuentra entre las claves disponibles)")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.imageView3),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.textView4), withText("Para mostrar el menú de la aplicación pulse el siguiente botón."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        textView12.check(matches(withText("Para mostrar el menú de la aplicación pulse el siguiente botón.")));

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.imageView4),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        mDevice.waitForIdle(5000);
        mDevice.click(171, 155);

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_clave_dicotomica),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView13 = onView(
                allOf(withText("Herramientas"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()));
        textView13.check(matches(withText("Herramientas")));

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

        ViewInteraction textView14 = onView(
                allOf(withText("Opciones"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                7),
                        isDisplayed()));
        textView14.check(matches(withText("Opciones")));

        ViewInteraction checkedTextView5 = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        8),
                                0),
                        isDisplayed()));
        checkedTextView5.check(matches(isDisplayed()));

        ViewInteraction textView15 = onView(
                allOf(withId(android.R.id.text1), withText("Sombrero con aspecto de panal, unido al pie en su base:"),
                        childAtPosition(
                                allOf(withId(R.id.listView_claveDicotomica),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                2)),
                                5),
                        isDisplayed()));
        textView15.check(matches(withText("Sombrero con aspecto de panal, unido al pie en su base:")));

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
