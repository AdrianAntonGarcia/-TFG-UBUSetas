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

/*
* @name: MostrarResultadosPruebasEs
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que MostrarResultadosPulsarItems que se carguen todos los textos en español de la actividad MostrarResultados,
* tanto de la ayuda, como del menú y el propio contenido de la actividad. Además comprueba que se muestren los
* resultados correctos trás haber cargado la imágen cargada en el test de RecogerFotoPruebaGaleria.
* */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MostrarResultadosPruebasEn {

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
    * @name: mostrarResultadosPruebasEs
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que MostrarResultadosPulsarItems que se carguen todos los textos en español de la actividad MostrarResultados,
    * tanto de la ayuda, como del menú y el propio contenido de la actividad. Además comprueba que se muestren los
    * resultados correctos trás haber cargado la imágen cargada en el test de RecogerFotoPruebaGaleria.
    * */

    @Test
    public void mostrarResultadosPruebasEs() {

        //Cargo la foto llamando al anterior test

        RecogerFotoPruebaGaleria recogerFoto = new RecogerFotoPruebaGaleria();
        try {
            recogerFoto.setup();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        recogerFoto.recogerFotoGaleria();

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_mostrar_resultados),
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
                allOf(withId(R.id.TextView_ResultadosClasificador), withText("Results:"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Results:")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView_texto_lista), withText("agrocybe parasitica (67,7%)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("agrocybe parasitica (67,7%)")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.textView_texto_lista), withText("armillaria mellea (14,4%)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        1),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("armillaria mellea (14,4%)")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.textView_texto_lista), withText("hypholoma fasciculare (11,3%)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        2),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("hypholoma fasciculare (11,3%)")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.textView_texto_lista), withText("flammulina velutipes (2,3%)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        3),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("flammulina velutipes (2,3%)")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView_texto_lista), withText("pleurotus ostreatus (1,8%)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        4),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("pleurotus ostreatus (1,8%)")));

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView_imagen_lista),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        0),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.imageView_imagen_lista),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        1),
                                0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction imageView3 = onView(
                allOf(withId(R.id.imageView_imagen_lista),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        2),
                                0),
                        isDisplayed()));
        imageView3.check(matches(isDisplayed()));

        ViewInteraction imageView4 = onView(
                allOf(withId(R.id.imageView_imagen_lista),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        3),
                                0),
                        isDisplayed()));
        imageView4.check(matches(isDisplayed()));

        ViewInteraction imageView5 = onView(
                allOf(withId(R.id.imageView_imagen_lista),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.listView_lista_resultados),
                                        4),
                                0),
                        isDisplayed()));
        imageView5.check(matches(isDisplayed()));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.boton_refrescar_resultados),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        1),
                                3),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.boton_clave),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(ViewGroup.class),
                                        1),
                                2),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

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

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.textView0), withText("Activity help show results:"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView10.check(matches(withText("Activity help show results:")));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.textView1), withText("The page will show the most likely mushroom species classified for the mushroom photo introduced.      You can see a comparison of your photo and that of the species by clicking once on the species in the list.      To see a description of the species, press and hold the species in the list."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        textView11.check(matches(withText("The page will show the most likely mushroom species classified for the mushroom photo introduced.      You can see a comparison of your photo and that of the species by clicking once on the species in the list.      To see a description of the species, press and hold the species in the list.")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.textView2), withText("To choose on which genres of the classifieds to filter the dichotomous key          general press the next button."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        textView12.check(matches(withText("To choose on which genres of the classifieds to filter the dichotomous key          general press the next button.")));

        ViewInteraction imageView13 = onView(
                allOf(withId(R.id.imageView2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        imageView13.check(matches(isDisplayed()));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.textView3), withText("To change the displayed images of each genre press the following button."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        textView14.check(matches(withText("To change the displayed images of each genre press the following button.")));

        ViewInteraction imageView15 = onView(
                allOf(withId(R.id.imageView3),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        imageView15.check(matches(isDisplayed()));

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.textView4), withText("To show the application menu press the following button."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        textView16.check(matches(withText("To show the application menu press the following button.")));

        ViewInteraction imageView17 = onView(
                allOf(withId(R.id.imageView4),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        imageView17.check(matches(isDisplayed()));

        mDevice.waitForIdle(5000);
        mDevice.click(171, 155);

        ViewInteraction appCompatImageButton1 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.barra_mostrar_resultados),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton1.perform(click());

        ViewInteraction textView18 = onView(
                allOf(withText("Tools"),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()));
        textView18.check(matches(withText("Tools")));

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
