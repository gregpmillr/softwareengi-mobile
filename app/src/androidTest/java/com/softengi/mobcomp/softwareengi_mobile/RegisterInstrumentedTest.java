package com.softengi.mobcomp.softwareengi_mobile;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test for logging out.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegisterInstrumentedTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);


    @Test
    public void testAthleteRegister() throws Exception {
        String username = "greg";
        String email = "gregpmillr@gmail.com";
        String password = "miller";
        String language = "English";

        // press login button
        onView(withId(R.id.btnRegister))
                .perform(click());

        intended(hasComponent(RegisterActivity.class.getName()));

        // type username
        onView(withId(R.id.etUsername))
                .perform(typeText(username), closeSoftKeyboard());
        // type email
        onView(withId(R.id.etEmail))
                .perform(typeText(email), closeSoftKeyboard());
        // type password
        onView(withId(R.id.etPassword))
                .perform(typeText(password), closeSoftKeyboard());
        // check coach
        onView(withId(R.id.chkCoach)).check(matches(isNotChecked()));
        // type language
        onView(withId(R.id.etLanguage))
                .perform(typeText(language), closeSoftKeyboard());
        onView(withId(R.id.btnSubmit))
                .perform(click());

        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void testCoachRegister() throws Exception {
        String username = "greg";
        String email = "gregpmillr@gmail.com";
        String password = "miller";
        String language = "English";

        // press login button
        onView(withId(R.id.btnRegister))
                .perform(click());

        intended(hasComponent(RegisterActivity.class.getName()));

        // type username
        onView(withId(R.id.etUsername))
                .perform(typeText(username), closeSoftKeyboard());
        // type email
        onView(withId(R.id.etEmail))
                .perform(typeText(email), closeSoftKeyboard());
        // type password
        onView(withId(R.id.etPassword))
                .perform(typeText(password), closeSoftKeyboard());

        // check coach
        onView(withId(R.id.chkCoach))
                .perform(click());
        onView(withId(R.id.chkCoach)).check(matches(isChecked()));

        // type language
        onView(withId(R.id.etLanguage))
                .perform(typeText(language), closeSoftKeyboard());
        onView(withId(R.id.btnSubmit))
                .perform(click());

        intended(hasComponent(MainActivity.class.getName()));
    }
}
