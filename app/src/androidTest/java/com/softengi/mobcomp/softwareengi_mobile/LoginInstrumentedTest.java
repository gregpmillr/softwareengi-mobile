package com.softengi.mobcomp.softwareengi_mobile;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test for loggin in.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void validateGoodPassword() throws Exception {
        String user = "No username Exists";
        String password = "pass";
        // type user
        onView(withId(R.id.etUsername))
                .perform(typeText(user), closeSoftKeyboard());
        // type password
        onView(withId(R.id.etPassword))
                .perform(typeText(password), closeSoftKeyboard());
        // press login button
        onView(withId(R.id.btnLogin))
                .perform(click());
        // check user text
        onView(withId(R.id.etUsername))
                .check(matches(withText(user)));
    }
}
