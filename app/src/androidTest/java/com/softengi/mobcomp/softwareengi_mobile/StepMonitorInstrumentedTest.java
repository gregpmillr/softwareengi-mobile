package com.softengi.mobcomp.softwareengi_mobile;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by br239 on 2018-03-06.
 */

@RunWith(AndroidJUnit4.class)
public class StepMonitorInstrumentedTest {

    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void TEST(){
        /*
        String user = "greg";
        String password = "password";
        // type user
        onView(withId(R.id.etUsername))
                .perform(typeText(user), closeSoftKeyboard());
        // type password
        onView(withId(R.id.etPassword))
                .perform(typeText(password), closeSoftKeyboard());
        // press login button
        onView(withId(R.id.btnLogin))
                .perform(click());
        intended(hasComponent(ListOfPlanActivity.class.getName()));
        */
    }
}
