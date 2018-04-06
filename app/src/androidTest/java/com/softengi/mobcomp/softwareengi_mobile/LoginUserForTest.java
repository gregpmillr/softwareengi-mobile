package com.softengi.mobcomp.softwareengi_mobile;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Class used for logging in during the espresso test
 */
public class LoginUserForTest {
    public static void login() {
        String user = "greg";
        String password = "password";
        // type user
        onView(withId(R.id.etUsername))
                .perform(clearText(), typeText(user), closeSoftKeyboard());
        // type password
        onView(withId(R.id.etPassword))
                .perform(clearText(), typeText(password), closeSoftKeyboard());
        // press login button
        onView(withId(R.id.btnLogin))
                .perform(click());
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(MainActivity.class.getName()));
        onView(withId(R.id.tvProfileUsername)).check(matches(withText(user)));
    }
}
