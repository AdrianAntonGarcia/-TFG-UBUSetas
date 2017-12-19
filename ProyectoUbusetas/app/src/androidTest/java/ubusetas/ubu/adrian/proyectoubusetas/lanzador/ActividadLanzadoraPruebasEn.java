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

/*
* @name: ActividadLanzadoraPruebasEn
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que MostrarResultadosPulsarItems que los textos de la actividad lanzadora se muestren correctamente en inglés,
* tanto los de la actividad, como los de la ayuda y los del menú.
* */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActividadLanzadoraPruebasEn {

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
    * @name: probarTextosEn
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que MostrarResultadosPulsarItems que los textos de la actividad lanzadora se muestren correctamente en inglés,
    * tanto los de la actividad, como los de la ayuda y los del menú.
    * */

    @Test
    public void probarTextosEn() {
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

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        8),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction clasificar = onView(withId(R.id.textView_clasificar));

        clasificar.check(matches(withText("Classify")));

        ViewInteraction irSetas = onView(withId(R.id.textView_ir_setas));

        irSetas.check(matches(withText("Show mushrooms")));

        ViewInteraction irClaves = onView(withId(R.id.textView_ir_claves));

        irClaves.check(matches(withText("Go keys")));


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

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView0), withText("Help of the main activity:"),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Help of the main activity:")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView1), withText("To classify a photo click on the magnifying glass icon."),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("To classify a photo click on the magnifying glass icon.")));

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

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textView2), withText("To show a list of available mushrooms press the information icon."),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        textView7.check(matches(withText("To show a list of available mushrooms press the information icon.")));

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

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.textView3), withText("To show a list of available dichotomous keys press the following button."),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        textView8.check(matches(withText("To show a list of available dichotomous keys press the following button.")));

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

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.textView4), withText("To show the application menu press the following button."),
                        childAtPosition(
                                allOf(withId(R.id.RelativeLayoutLanzadora),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                7),
                        isDisplayed()));
        textView9.check(matches(withText("To show the application menu press the following button.")));

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

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_lanzadora),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withText("Tools"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()));
        textView10.check(matches(withText("Tools")));

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

        ViewInteraction textView11 = onView(
                allOf(withText("Options"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                7),
                        isDisplayed()));
        textView11.check(matches(withText("Options")));

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

        ViewInteraction checkedTextView7 = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        9),
                                0),
                        isDisplayed()));
        checkedTextView7.check(matches(isDisplayed()));

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
