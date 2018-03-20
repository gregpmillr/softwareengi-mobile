package com.softengi.mobcomp.softwareengi_mobile;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.softengi.mobcomp.softwareengi_mobile.ProfileFragment.onUpdateProfile;
import com.softengi.mobcomp.softwareengi_mobile.PlansFragment.onFragmentLoad;
import com.softengi.mobcomp.softwareengi_mobile.CreatePlanFragment.onCreateFragmentLoad;
import com.softengi.mobcomp.softwareengi_mobile.Controllers.PlanController;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListOfPlanParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AthleteActivity extends AppCompatActivity implements onFragmentLoad, onCreateFragmentLoad, onUpdateProfile {

    private BottomNavigationView mAthleteNav;
    private FrameLayout mAthleteFrame;
    private PlansFragment plansFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete);

        mAthleteFrame   = findViewById(R.id.athlete_frame);
        mAthleteNav     = findViewById(R.id.athlete_nav);
        plansFragment   = new PlansFragment();
        profileFragment = new ProfileFragment();

        setFragment(profileFragment);

        mAthleteNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case R.id.nav_plans :
                        setFragment(plansFragment);
                        return true;

                    case R.id.nav_profile :
                        setFragment(profileFragment);
                        return true;

                    case R.id.nav_teams :
                        return true;

                    default:
                        return false;

                }

            }
        });

    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.athlete_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void loadAdapter(final ArrayAdapter listAdapter, final ArrayList<String> arrList) {
        PlanController.getListOfPlans(
                getApplication(),
                SharedPrefManager.getInstance(getApplicationContext()).getUsername(),
                new ListOfPlanParser() {
                    @Override
                    public void onSuccessResponse(JSONArray data) {

                        try {
                            arrList.clear();
                            for(int i = 0; i < data.length(); i++) {
                                arrList.add(data.getJSONObject(i).getString("title"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        listAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onCreatePlan() {
        CreatePlanFragment fragment = new CreatePlanFragment();
        setFragment(fragment);
    }

    @Override
    public void onPlanDetail(int position, long id) {
        PlansFragment fragment = new PlansFragment();
        setFragment(fragment);
    }

    @Override
    public void onSubmitPlan() {


        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        PlanController.postCreatePlans(getApplicationContext(),
                (EditText) findViewById(R.id.etPlanCreateTitle),
                (EditText) findViewById(R.id.etPlanCreateRequiredSteps),
                new SuccessListener() {
                    @Override
                    public void successful() {

                        PlansFragment fragment = new PlansFragment();
                        setFragment(fragment);

                    }
                }
        );

    }

    @Override
    public void updateEmail() {

    }

    @Override
    public void updateLanguage() {

    }

    @Override
    public void updateCoach() {

    }

    @Override
    public void logout() {
        SharedPrefManager.getInstance(getApplicationContext()).logout();
        AthleteActivity.this.finish();
        Intent i = new Intent(this,MainActivity.class);
        // clear the backstack
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
