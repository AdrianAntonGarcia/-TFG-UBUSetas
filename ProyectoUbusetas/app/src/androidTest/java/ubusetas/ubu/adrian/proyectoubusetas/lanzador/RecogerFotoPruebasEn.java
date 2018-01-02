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
import org.hamcrest.core.IsInstanceOf;
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
 * Clase que prueba que todos los textos de la actividad recogerFoto se muestren
 * correctamente en inglés al pulsar los diferentes botones.
 *
 * @author Adrián Antón García
 * @name RecogerFotoPruebasEn
 * @category clase
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecogerFotoPruebasEn {

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
     * Procedimiento que prueba que todos los textos de la actividad recogerFoto se muestren
     * correctamente en inglés al pulsar los diferentes botones.
     *
     * @name probarTextosEn
     * @author Adrián Antón García
     * @category procedimiento
     */

    @Test
    public void probarTextosEn() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.boton_clasificar_lanzadora),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                3),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_recoger_foto),
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
                allOf(withId(R.id.TextView_elegir_opcion), withText("Choose an option:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Choose an option:")));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.boton_galeria),
                        childAtPosition(
                                allOf(withId(R.id.LinearLayout_botones_recoger_foto),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.boton_hacer_foto),
                        childAtPosition(
                                allOf(withId(R.id.LinearLayout_botones_recoger_foto),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                1)),
                                1),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.TextView_imagen_mostrada), withText("Image taken to classify:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        1),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("Image taken to classify:")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView_mostrar_imagen),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        1),
                                3),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.imageView_mostrar_imagen),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        1),
                                3),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

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

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView0), withText("Activity help pick up photo:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Activity help pick up photo:")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView1), withText("To upload a photo from the gallery press the following button."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("To upload a photo from the gallery press the following button.")));

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.imageView1),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView2), withText("To load a photo from the camera of the mobile press the following button."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        textView5.check(matches(withText("To load a photo from the camera of the mobile press the following button.")));

        ViewInteraction imageView4 = onView(
                allOf(withId(R.id.imageView2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        imageView4.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView3), withText("To classify the photo press the following button (only appears when a photo has been loaded)."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        textView6.check(matches(withText("To classify the photo press the following button (only appears when a photo has been loaded).")));

        ViewInteraction imageView5 = onView(
                allOf(withId(R.id.imageView3),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        imageView5.check(matches(isDisplayed()));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textView4), withText("To save the photo press the following button (only appears when a photo has been taken from the camera)."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        textView7.check(matches(withText("To save the photo press the following button (only appears when a photo has been taken from the camera).")));

        ViewInteraction imageView6 = onView(
                allOf(withId(R.id.imageView4),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        imageView6.check(matches(isDisplayed()));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.textView5), withText("To show the application menu press the following button."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                9),
                        isDisplayed()));
        textView8.check(matches(withText("To show the application menu press the following button.")));

        ViewInteraction imageView7 = onView(
                allOf(withId(R.id.imageView5),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        imageView7.check(matches(isDisplayed()));

        mDevice.waitForIdle(5000);
        mDevice.click(171, 155);

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_recoger_foto),
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

        ViewInteraction textView9 = onView(
                allOf(withText("Options"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                7),
                        isDisplayed()));
        textView9.check(matches(withText("Options")));

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
