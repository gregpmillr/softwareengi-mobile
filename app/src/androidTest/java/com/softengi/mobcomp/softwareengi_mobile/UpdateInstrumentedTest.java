package com.softengi.mobcomp.softwareengi_mobile;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test for updating user profile.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UpdateInstrumentedTest {

    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void testUpdateProfile() throws Exception {

        String loginUsername = "test";
        String loginPassword = "miller";
        String newEmail      = "updatedemail@gmail.com";
        String newLanguage   = "Spanish";

        onView(withId(R.id.etUsername))
                .perform(clearText(), typeText(loginUsername), closeSoftKeyboard());
        // type password
        onView(withId(R.id.etPassword))
                .perform(clearText(), typeText(loginPassword), closeSoftKeyboard());
        // press login button
        onView(withId(R.id.btnLogin))
                .perform(click());
        //wait for it to log in
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(MainActivity.class.getName()));
        onView(withId(R.id.tvProfileUsername)).check(matches(withText(loginUsername)));

        // type new email
        onView(withId(R.id.etProfileEmail))
                .perform(clearText(), typeText(newEmail), closeSoftKeyboard());
        // check coach
        onView(withId(R.id.chkCoach)).check(matches(isNotChecked()));
        // type user
        onView(withId(R.id.etProfileLanguage))
                .perform(clearText(), typeText(newLanguage), closeSoftKeyboard());

        onView(withId(R.id.btnProfileUpdate))
                .perform(click());

        onView(withId(R.id.etProfileEmail)).check(matches(withText(newEmail)));

    }

}
