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

/**
 * Clase que prueba que los textos de la actividad ClaveDicotomica se muestren correctamente en inglés,
 * así como los textos de la clave según se vaya avanzando a través de ella.
 *
 * @author Adrián Antón García
 * @name ClaveDicotomicaPruebasEs
 * @category clase
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ClaveDicotomicaPruebasEn {

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
     * Procedimiento que prueba que los textos de la actividad ClaveDicotomica se muestren correctamente en inglés,
     * así como los textos de la clave según se vaya avanzando a través de ella.
     *
     * @name claveDicotomicaPruebasEs
     * @author Adrián Antón García
     * @category procedimiento test
     */

    @Test
    public void claveDicotomicaPruebasEn() {

        RecogerFotoPruebaGaleria recogerFoto = new RecogerFotoPruebaGaleria();
        try {
            recogerFoto.setup();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        recogerFoto.recogerFotoGaleria();

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.boton_clave),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                2),
                        isDisplayed()));
        floatingActionButton4.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_lista_seleccion_claves),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler_view_lista_seleccion_claves),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.boton_obtener), withText("Seleccionar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.boton_ir_clave), withText("Ir clave dicotómica"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                4),
                        isDisplayed()));
        appCompatButton2.perform(click());

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
                allOf(withId(R.id.TextView_ClaveMostrada), withText("general"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("general")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.TextView_Pregunta_clave), withText("Select the key that most suits the mushroom:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Select the key that most suits the mushroom:")));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("In mature mushrooms, the lines take a pink tint."),
                        childAtPosition(
                                allOf(withId(R.id.listView_claveDicotomica),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                2)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("In mature mushrooms, the lines take a pink tint.")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("In mostly mature mushrooms, the lines are brown or other dark colors."),
                        childAtPosition(
                                allOf(withId(R.id.listView_claveDicotomica),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                2)),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("In mostly mature mushrooms, the lines are brown or other dark colors.")));

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.text1), withText("The young and mature mushrooms have white or light colored lines, sometimes lilies."),
                        childAtPosition(
                                allOf(withId(R.id.listView_claveDicotomica),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                2)),
                                2),
                        isDisplayed()));
        textView5.check(matches(withText("The young and mature mushrooms have white or light colored lines, sometimes lilies.")));

        ViewInteraction textView6 = onView(
                allOf(withId(android.R.id.text1), withText("Mushrooms with orange and decurrent lines."),
                        childAtPosition(
                                allOf(withId(R.id.listView_claveDicotomica),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                2)),
                                3),
                        isDisplayed()));
        textView6.check(matches(withText("Mushrooms with orange and decurrent lines.")));

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
                .atPosition(1);
        appCompatTextView.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView_claveDicotomica),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(2);
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

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.TextView_Pregunta_clave), withText("Classified genre: Hebeloma"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                1),
                        isDisplayed()));
        textView7.check(matches(withText("Classified genre: Hebeloma")));


        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.title), withText("Help"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView5.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.textView0), withText("Help of the dichotomous key activity:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView8.check(matches(withText("Help of the dichotomous key activity:")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.textView1), withText("The page will show a succession of dilemmas, you should choose the one that considers that          the more it adapts to the mushroom until it reaches a genre."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView9.check(matches(withText("The page will show a succession of dilemmas, you should choose the one that considers that          the more it adapts to the mushroom until it reaches a genre.")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.textView2), withText("To return to the previous question press the following button."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        textView10.check(matches(withText("To return to the previous question press the following button.")));

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
                allOf(withId(R.id.textView3), withText("To go to the key of a specific genre, press the following button (It will appear if the sorted genre is among the available keys)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        textView11.check(matches(withText("To go to the key of a specific genre, press the following button (It will appear if the sorted genre is among the available keys)")));

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
                allOf(withId(R.id.textView4), withText("To show the application menu press the following button."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        textView12.check(matches(withText("To show the application menu press the following button.")));

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

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_clave_dicotomica),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction textView13 = onView(
                allOf(withText("Tools"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()));
        textView13.check(matches(withText("Tools")));

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
                allOf(withText("Options"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                7),
                        isDisplayed()));
        textView14.check(matches(withText("Options")));

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
