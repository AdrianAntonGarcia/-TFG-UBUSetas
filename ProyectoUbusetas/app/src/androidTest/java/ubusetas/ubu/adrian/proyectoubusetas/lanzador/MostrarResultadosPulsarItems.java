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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ubusetas.ubu.adrian.proyectoubusetas.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

/*
* @name: MostrarResultadosPulsarItems
* @Author: Adrián Antón García
* @category: clase
* @Description: Clase que prueba que funcionen correctamentes los diferentes resultados de la lista
* al ser pulsados.
* */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MostrarResultadosPulsarItems {

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
    * @name: pulsarItems
    * @Author: Adrián Antón García
    * @category: procedimiento test
    * @Description: Procedimiento que prueba que funcionen correctamentes los diferentes resultados de la lista
    * al ser pulsados. También prueba el boton de refrecar las imágenes.
    * */

    @Test
    public void pulsarItems() {
        RecogerFotoPruebaGaleria recogerFoto = new RecogerFotoPruebaGaleria();
        try {
            recogerFoto.setup();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        recogerFoto.recogerFotoGaleria();
        for (int i = 0; i < 5; ++i) {
            DataInteraction linearLayout = onData(anything())
                    .inAdapterView(allOf(withId(R.id.listView_lista_resultados),
                            childAtPosition(
                                    withClassName(is("android.widget.RelativeLayout")),
                                    1)))
                    .atPosition(i);
            linearLayout.perform(click());

            pressBack();

            linearLayout.perform(longClick());

            pressBack();
            ViewInteraction floatingActionButton4 = onView(
                    allOf(withId(R.id.boton_refrescar_resultados),
                            childAtPosition(
                                    childAtPosition(
                                            withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                            1),
                                    3),
                            isDisplayed()));
            floatingActionButton4.perform(click());
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
