package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Actions.ProfileAction;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.PlanDataModel;
import com.softengi.mobcomp.softwareengi_mobile.ProfileFragment.onProfileListener;
import com.softengi.mobcomp.softwareengi_mobile.PlansFragment.onPlansFragmentLoad;
import com.softengi.mobcomp.softwareengi_mobile.CreatePlanFragment.onCreateFragmentLoad;
import com.softengi.mobcomp.softwareengi_mobile.PlansDetailFragment.onPlansDetail;
import com.softengi.mobcomp.softwareengi_mobile.Actions.PlanAction;
import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListPlanAdapter;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListOfPlanParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ProfileParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements onPlansFragmentLoad,
        onCreateFragmentLoad, onProfileListener, onPlansDetail {

    private BottomNavigationView mAthleteNav;
    private FrameLayout mAthleteFrame;
    private PlansFragment mPlansFragment;
    private ProfileFragment mProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAthleteFrame   = findViewById(R.id.athlete_frame);
        mAthleteNav     = findViewById(R.id.athlete_nav);
        mPlansFragment   = new PlansFragment();
        mProfileFragment = new ProfileFragment();

        setFragment(mProfileFragment);

        mAthleteNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case R.id.nav_plans :
                        setFragment(mPlansFragment);
                        return true;

                    case R.id.nav_profile :
                        setFragment(mProfileFragment);
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
    public void loadPlansAdapter(final ArrayListPlanAdapter hmAdapter, final ArrayList<PlanDataModel> listData) {
        PlanAction.getListOfPlans(
                getApplication(),
                SharedPrefManager.getInstance(getApplicationContext()).getUsername(),
                new ListOfPlanParser() {
                    @Override
                    public void onSuccessResponse(JSONArray data) {
                        try {
                            listData.clear();

                            for(int i = 0; i < data.length(); i++) {
                                JSONObject jsonObj = data.getJSONObject(i);
                                listData.add(new PlanDataModel(jsonObj.getString("title"),jsonObj.getString("required_steps"),jsonObj.getString("id")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        hmAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onCreatePlan() {
        CreatePlanFragment fragment = new CreatePlanFragment();
        setFragment(fragment);
    }

    @Override
    public void onPlanDetail(final PlanDataModel dataModel) {
        Bundle args = new Bundle();
        args.putString("plan_title", dataModel.getTitle());
        args.putString("plan_required_steps", dataModel.getRequiredSteps());
        args.putString("plan_id", dataModel.getId());
        PlansDetailFragment fragment = new PlansDetailFragment();
        fragment.setArguments(args);
        setFragment(fragment);
    }

    @Override
    public void onSubmitPlan() {

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        PlanAction.postCreatePlans(getApplicationContext(),
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
    public void updateProfile(TextView username, EditText email, EditText language, CheckBox coach) {
        ProfileAction.postUpdate(getApplicationContext(), username, email, language, coach, new SuccessListener() {
            @Override
            public void successful() {
                Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void loadProfile(TextView tvTotalSteps, TextView tvTotalPlans, TextView tvTotalTeams) {
        ProfileAction.getProfile(getApplicationContext(), tvTotalSteps, tvTotalPlans, tvTotalTeams, new ProfileParser() {
            @Override
            public void onSuccessResponse(JSONObject response) {
                // stub
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void logout() {
        SharedPrefManager.getInstance(getApplicationContext()).logout();
        MainActivity.this.finish();
        Intent i = new Intent(this,LoginActivity.class);
        // clear the backstack
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void deletePlan(String planId) {
        PlanAction.postDelete(getApplicationContext(), planId, new SuccessListener() {
            @Override
            public void successful() {
                PlansFragment fragment = new PlansFragment();
                setFragment(fragment);
            }
        });
    }

    @Override
    public void updatePlan(EditText title, EditText requiredSteps, String planId) {
        PlanAction.postUpdate(getApplicationContext(), title, requiredSteps, planId, new SuccessListener() {
            @Override
            public void successful() {
                Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
