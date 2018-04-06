package com.softengi.mobcomp.softwareengi_mobile;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
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
import static org.hamcrest.Matchers.anything;

/**
 * Instrumented test for plan fragment
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanInstrumentedTest {
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void ATestPlanFragment(){
        LoginUserForTest.login();

        //go to list of plans
        onView(withId(R.id.nav_plans))
                .perform(click());
        onView(withId(R.id.btnCreatePlan)).check(matches(withText(R.string.create)));
    }

    @Test
    public void BTestCreatePlanFragment(){
        String title = "espresso test";
        String requiredSteps = "500";

        LoginUserForTest.login();

        //go to list of plans
        onView(withId(R.id.nav_plans))
                .perform(click());
        onView(withId(R.id.btnCreatePlan)).check(matches(withText(R.string.create)));

        //click create plan
        onView(withId(R.id.btnCreatePlan))
                .perform(click());

        //type and submit to create a plan
        onView(withId(R.id.etPlanCreateTitle))
                .perform(clearText(), typeText(title), closeSoftKeyboard());
        onView(withId(R.id.etPlanCreateRequiredSteps))
                .perform(clearText(), typeText(requiredSteps), closeSoftKeyboard());
        onView(withId(R.id.btnSubmitPlan))
                .perform(click());

        onView(withId(R.id.btnCreatePlan)).check(matches(withText(R.string.create)));
    }

    @Test
    public void CTestUpdatePlanFragment(){
        String title = "update test";
        String requiredSteps = "9000";

        LoginUserForTest.login();

        //go to list of plans
        onView(withId(R.id.nav_plans))
                .perform(click());
        onView(withId(R.id.btnCreatePlan)).check(matches(withText(R.string.create)));

        //wait for the list to load
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        //get the number of items on the list
        final int[] numberOfAdapterItems = new int[1];
        onView(withId(R.id.lvPlans)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                //here we assume the adapter has been fully loaded already
                numberOfAdapterItems[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        //click the last plan on the list
        onData(anything()).inAdapterView(withId(R.id.lvPlans)).atPosition(numberOfAdapterItems[0]-1)
                .perform(click());

        onView(withId(R.id.btnDetailUpdate)).check(matches(withText(R.string.update)));

        //type in the title and required steps to update to
        onView(withId(R.id.etPlanDetailTitle))
                .perform(clearText(), typeText(title), closeSoftKeyboard());
        onView(withId(R.id.etPlanDetailRequiredSteps))
                .perform(clearText(), typeText(requiredSteps), closeSoftKeyboard());

        //click update
        onView(withId(R.id.btnDetailUpdate))
                .perform(click());

        //go to list of plan
        onView(withId(R.id.nav_plans))
                .perform(click());

        //click on the last plan of the list
        onData(anything()).inAdapterView(withId(R.id.lvPlans)).atPosition(numberOfAdapterItems[0]-1)
                .perform(click());

        //check if the plan has been updated to the new title
        onView(withId(R.id.etPlanDetailTitle)).check(matches(withText(title)));
    }

    @Test
    public void DTestDeletePlanFragment(){
        LoginUserForTest.login();

        //go to list of plans
        onView(withId(R.id.nav_plans))
                .perform(click());
        onView(withId(R.id.btnCreatePlan)).check(matches(withText(R.string.create)));

        //wait for the list to load
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        //get the number of items on the list
        final int[] numberOfAdapterItems = new int[1];
        onView(withId(R.id.lvPlans)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                //here we assume the adapter has been fully loaded already
                numberOfAdapterItems[0] = listView.getAdapter().getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        //click the last plan on the list
        onData(anything()).inAdapterView(withId(R.id.lvPlans)).atPosition(numberOfAdapterItems[0]-1)
                .perform(click());

        onView(withId(R.id.btnDetailDelete)).check(matches(withText(R.string.delete)));

        //DELETE THIS PLAN
        onView(withId(R.id.btnDetailDelete))
                .perform(click());

        onView(withId(R.id.btnCreatePlan)).check(matches(withText(R.string.create)));
    }
}
