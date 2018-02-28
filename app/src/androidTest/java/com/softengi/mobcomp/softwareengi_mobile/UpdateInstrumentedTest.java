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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test for updating user profile.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UpdateInstrumentedTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testUpdateProfile() throws Exception {
        String loginUsername = "greg";
        String loginPassword = "password";
        String newUsername   = "updated greg";
        String newEmail      = "updatedemail@gmail.com";
        String newLanguage   = "Spanish";

        // type user
        onView(withId(R.id.etUsername))
                .perform(typeText(loginUsername), closeSoftKeyboard());
        // type password
        onView(withId(R.id.etPassword))
                .perform(typeText(loginPassword), closeSoftKeyboard());
        // press login button
        onView(withId(R.id.btnLogin))
                .perform(click());
        // make sure profile is reached
        intended(hasComponent(ProfileActivity.class.getName()));
        // type user
        onView(withId(R.id.etProfileUsername))
                .perform(typeText(newUsername), closeSoftKeyboard());
        // type user
        onView(withId(R.id.etProfileEmail))
                .perform(typeText(newEmail), closeSoftKeyboard());
        // check coach
        onView(withId(R.id.chkCoach)).check(matches(isNotChecked()));
        // type user
        onView(withId(R.id.etProfileLanguage))
                .perform(typeText(newLanguage), closeSoftKeyboard());
        // press login button
        onView(withId(R.id.btnUpdateProfile))
                .perform(click());

    }

}
