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
 * Instrumented test for logging out.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LogoutInstrumentedTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);


    @Test
    public void testLogout() throws Exception {
        String user = "greg";
        String password = "miller";
        // type user
        onView(withId(R.id.etUsername))
                .perform(typeText(user), closeSoftKeyboard());
        // type password
        onView(withId(R.id.etPassword))
                .perform(typeText(password), closeSoftKeyboard());
        // press login button
        onView(withId(R.id.btnLogin))
                .perform(click());
        intended(hasComponent(ProfileActivity.class.getName()));
        // check user text
        onView(withId(R.id.btnLogout))
                .perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
}
