package com.softengi.mobcomp.softwareengi_mobile;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;

import com.softengi.mobcomp.softwareengi_mobile.PlansFragment.onFragmentLoad;
import com.softengi.mobcomp.softwareengi_mobile.Controllers.PlanController;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListOfPlanParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class AthleteActivity extends AppCompatActivity implements onFragmentLoad {

    private BottomNavigationView mAthleteNav;
    private FrameLayout mAthleteFrame;
    private PlansFragment plansFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete);

        mAthleteFrame = findViewById(R.id.athlete_frame);
        mAthleteNav   = findViewById(R.id.athlete_nav);
        plansFragment = new PlansFragment();

        mAthleteNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case R.id.nav_plans :
                        mAthleteNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(plansFragment);
                        return true;

                    case R.id.nav_profile :
                        mAthleteNav.setItemBackgroundResource(R.color.colorAccent);
                        return true;

                    case R.id.nav_teams :
                        mAthleteNav.setItemBackgroundResource(R.color.colorPrimaryDark);
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
        fragmentTransaction.commit();

    }

    @Override
    public void loadAdapter(final ArrayAdapter listAdapter, final ArrayList<String> arrList) {
        arrList.add("test");
        listAdapter.notifyDataSetChanged();
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PlansFragment fragment = new PlansFragment();
        fragmentTransaction.replace(R.id.athlete_frame, fragment);
    }

    @Override
    public void onPlanDetail(int position, long id) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PlansFragment fragment = new PlansFragment();
        fragmentTransaction.replace(R.id.athlete_frame, fragment);
    }

}
