package com.softengi.mobcomp.softwareengi_mobile;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Instrumented test for all step fragment
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StepMonitorInstrumentedTest {

    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void TestStepFragment(){
        LoginUserForTest.login();

        //go to list of plans
        onView(withId(R.id.nav_plans))
                .perform(click());
        onView(withId(R.id.btnCreatePlan)).check(matches(withText(R.string.create)));

        //click the first plan on the list
        onData(anything()).inAdapterView(withId(R.id.lvPlans)).atPosition(0)
                .perform(click());

        onView(withId(R.id.btnToStep)).check(matches(withText(R.string.to_step)));

        onView(withId(R.id.btnToStep))
                .perform(click());

        onView(withId(R.id.tvStep)).check(matches(withText(R.string.steps)));
    }

    @Test
    public void testTotalStepCalculation() {
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        try {
            item.put("steps", 8);
            array.put(item);
            item = new JSONObject();
            item.put("steps", 8);
            array.put(item);
            item = new JSONObject();
            item.put("steps", 2);
            array.put(item);
            item = new JSONObject();
            item.put("steps", 3);
            array.put(item);
            item = new JSONObject();
            item.put("steps", 20);
            array.put(item);
            item = new JSONObject();
            item.put("steps", 4);
            array.put(item);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(45, PlansDetailFragment.getTotalSteps(array));
    }
}
