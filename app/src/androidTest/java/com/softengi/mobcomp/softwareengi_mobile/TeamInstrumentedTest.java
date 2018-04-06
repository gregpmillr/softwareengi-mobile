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
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Instrumented test for all teams fragment
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamInstrumentedTest {
    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void ATestTeamFragment(){
        LoginUserForTest.login();
        goToTeam();
    }

    @Test
    public void BTestCreateTeamFragment(){
        String teamName = "espresso";

        LoginUserForTest.login();
        goToTeam();

        onView(withId(R.id.btnCreateTeam))
                .perform(click());

        onView(withId(R.id.etTeamCreateName))
                .perform(clearText(), typeText(teamName), closeSoftKeyboard());

        //click create
        onView(withId(android.R.id.button1))
                .perform(click());

        //click the second user on the list to add to the team
        onData(anything()).inAdapterView(withId(R.id.lvUsers)).atPosition(1)
                .perform(click());

        //click submit
        onView(withId(android.R.id.button1))
                .perform(click());

        intended(hasComponent(TeamsActivity.class.getName()));
    }

    @Test
    public void CTestJoinTeamFragment(){
        LoginUserForTest.login();
        goToTeam();

        //wait for the team list to load
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        //get the number of items on the list
        final int[] numberOfAdapterItems = new int[1];
        onView(withId(R.id.lvAllTeams)).check(matches(new TypeSafeMatcher<View>() {
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

        //click the last team on the list
        onData(anything()).inAdapterView(withId(R.id.lvAllTeams)).atPosition(numberOfAdapterItems[0]-1)
                .perform(click());

        //click view member
        onView(withId(android.R.id.button3))
                .perform(click());

        //click join
        onView(withId(android.R.id.button1))
                .perform(click());
        intended(hasComponent(TeamsActivity.class.getName()));
    }

    @Test
    public void DTestDeleteTeamFragment(){
        LoginUserForTest.login();
        goToTeam();

        //wait for the team list to load
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        //get the number of items on the list
        final int[] numberOfAdapterItems = new int[1];
        onView(withId(R.id.lvAllTeams)).check(matches(new TypeSafeMatcher<View>() {
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

        //long click the last team on the list to pop the delete dialog
        onData(anything()).inAdapterView(withId(R.id.lvAllTeams)).atPosition(numberOfAdapterItems[0]-1)
                .perform(longClick());

        //click delete
        onView(withId(android.R.id.button1))
                .perform(click());
        intended(hasComponent(TeamsActivity.class.getName()));
    }

    public static void goToTeam() {
        onView(withId(R.id.nav_teams))
                .perform(click());
        intended(hasComponent(TeamsActivity.class.getName()));
    }
}
