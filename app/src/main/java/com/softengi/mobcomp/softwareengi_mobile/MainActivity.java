package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Actions.ProfileAction;
import com.softengi.mobcomp.softwareengi_mobile.Actions.TeamAction;
import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListTeamAdapter;
import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListUserPlanAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.PlanDataModel;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.TeamDataModel;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.UserPlanProgressDataModel;
import com.softengi.mobcomp.softwareengi_mobile.ProfileFragment.onProfileListener;
import com.softengi.mobcomp.softwareengi_mobile.PlansFragment.onPlansFragmentLoad;
import com.softengi.mobcomp.softwareengi_mobile.CreatePlanFragment.onCreateFragmentLoad;
import com.softengi.mobcomp.softwareengi_mobile.UserPlanProgressFragment.onUserPlanProgressFragmentLoad;
import com.softengi.mobcomp.softwareengi_mobile.PlansDetailFragment.onPlansDetail;
import com.softengi.mobcomp.softwareengi_mobile.Actions.PlanAction;
import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListPlanAdapter;
import com.softengi.mobcomp.softwareengi_mobile.Utils.DetailParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the entry point after logging in. Handles all CRUD actions, along with
 * acting as a controller for each fragment through the use of interfaces.
 */
public class MainActivity extends AppCompatActivity implements onPlansFragmentLoad,
        onCreateFragmentLoad, onProfileListener, onPlansDetail, onUserPlanProgressFragmentLoad {

    private BottomNavigationView mAthleteNav;
    private FrameLayout mAthleteFrame;
    private PlansFragment mPlansFragment;
    private ProfileFragment mProfileFragment;
    private int previousItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAthleteFrame   = findViewById(R.id.athlete_frame);
        mAthleteNav     = findViewById(R.id.athlete_nav);
        mPlansFragment   = new PlansFragment();
        mProfileFragment = new ProfileFragment();

        // default fragment to user's profile
        setFragment(mProfileFragment);

        // handle navigation of fragments
        mAthleteNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_plans :
                        previousItem = item.getItemId();
                        setFragment(mPlansFragment);
                        return true;

                    case R.id.nav_profile :
                        previousItem = item.getItemId();
                        setFragment(mProfileFragment);
                        return true;

                    case R.id.nav_teams :
                        Intent i = new Intent(getApplicationContext(), TeamsActivity.class);
                        startActivity(i);
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        switch(previousItem) {
            case R.id.nav_plans :
                setFragment(mPlansFragment);
                mAthleteNav.setSelectedItemId(previousItem);

            case R.id.nav_profile :
                setFragment(mProfileFragment);
                mAthleteNav.setSelectedItemId(previousItem);

            default:
                return;
        }
    }

    /**
     * Handles navigation between fragments
     * @param fragment Fragment to navigate to
     */
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.athlete_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void loadPlansAdapter(final ArrayListPlanAdapter hmAdapter, final ArrayList<PlanDataModel> listData) {
        // get list of plans and load into adapter
        PlanAction.getListOfPlans(
                getApplication(),
                SharedPrefManager.getInstance(getApplicationContext()).getUsername(),
                new ListParser() {
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
        // navigate to fragment for creating a plan
        CreatePlanFragment fragment = new CreatePlanFragment();
        setFragment(fragment);
    }

    @Override
    public void onPlanDetail(final PlanDataModel dataModel) {
        // add Plan details to bundle which is passed to the new fragment
        Bundle args = new Bundle();
        args.putString(getString(R.string.plan_title), dataModel.getTitle());
        args.putString(getString(R.string.plan_required_steps), dataModel.getRequiredSteps());
        args.putString(getString(R.string.plan_id), dataModel.getId());
        // navigate to plan detail fragment
        PlansDetailFragment fragment = new PlansDetailFragment();
        fragment.setArguments(args);
        setFragment(fragment);
    }

    @Override
    public void loadAllTeamsAdapter(final ArrayListTeamAdapter adapter, final ArrayList<TeamDataModel> teamDataModels) {
        // get a list of all teams and populate the datamodels
        TeamAction.getListOfAllTeams(
                getApplication(),
                new ListParser() {
                    @Override
                    public void onSuccessResponse(JSONArray data) {
                        try {
                            teamDataModels.clear();

                            for(int i = 0; i < data.length(); i++) {
                                JSONObject jsonObj = data.getJSONObject(i);

                                teamDataModels.add(
                                        new TeamDataModel(
                                                jsonObj.getString("name"),
                                                jsonObj.getString("id")
                                        )
                                );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void massAssignTeam(PlanDataModel planDataModel, TeamDataModel teamDataModel) {
        TeamAction.massAssignTeam(getApplicationContext(), planDataModel.getId(), teamDataModel.getId());
    }

    @Override
    public void onSubmitPlan(String title, String requiredSteps ) {
        // create a new plan
        PlanAction.postCreatePlans(getApplicationContext(),
                title,
                requiredSteps,
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
    public void updateProfile(String username, String email, String language, String coach) {
        // update a profile
        ProfileAction.postUpdate(getApplicationContext(),
                username,
                email,
                language,
                coach
        );
    }

    @Override
    public void loadProfile(final TextView tvTotalSteps, final TextView tvTotalPlans, final TextView tvTotalTeams,
                            final TextView tvRecentSteps, final TextView tvRecentPlans) {
        // get a profile
        ProfileAction.getProfile(getApplicationContext(), new DetailParser() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                // tvTotalSteps.setText();
                try {
                    tvTotalSteps.setText(result.getString("total_steps"));
                    tvTotalPlans.setText(result.getString("total_plans"));
                    tvTotalTeams.setText(result.getString("total_teams"));
                    tvRecentPlans.setText(result.getString("recent_plans"));
                    tvRecentSteps.setText(result.getString("recent_steps"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void logout() {
        MainActivity.this.finish();
        Intent i = new Intent(this,LoginActivity.class);
        // clear the backstack
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void deletePlan(String planId) {
        // delete a plan
        PlanAction.postDelete(getApplicationContext(), planId, new SuccessListener() {
            @Override
            public void successful() {
                PlansFragment fragment = new PlansFragment();
                setFragment(fragment);
            }
        });
    }

    @Override
    public void updatePlan(String title, String requiredSteps, String planId) {
        // update a plan
        PlanAction.postUpdate(getApplicationContext(),
                title,
                requiredSteps,
                planId, new SuccessListener() {
            @Override
            public void successful() {
                Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void toStepFragment(String planId, String title, String requiredSteps, String totalSteps) {
        // Add the plan details to the Bundle which is passed to the fragment
        Bundle args = new Bundle();
        args.putString(getString(R.string.plan_id), planId);
        args.putString(getString(R.string.plan_title), title);
        args.putString(getString(R.string.plan_required_steps), requiredSteps);
        args.putString(getString(R.string.plan_total_steps), totalSteps);

        // navigate to new fragment
        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);
        setFragment(fragment);
    }

    @Override
    public void toUserPlanProgress(String planId) {
        // add the plan id to the Bundle which is passed to the fragment
        Bundle args = new Bundle();
        args.putString("plan_id", planId);

        // navigate to new fragment
        UserPlanProgressFragment fragment = new UserPlanProgressFragment();
        fragment.setArguments(args);
        setFragment(fragment);
    }

    @Override
    public void loadUserPlanProgressAdapter(final ArrayListUserPlanAdapter adapter, final ArrayList<UserPlanProgressDataModel> userPlanProgressDataModels, String planId) {
        // get the all user progress for this plan
        PlanAction.getUserProgressForPlans(getApplicationContext(), planId, new ListParser() {
            @Override
            public void onSuccessResponse(JSONArray data) {
                try {
                    userPlanProgressDataModels.clear();
                    for(int i = 0; i < data.length(); i++) {
                        JSONObject jsonObj = data.getJSONObject(i);
                        userPlanProgressDataModels.add(
                                new UserPlanProgressDataModel(
                                        jsonObj.getString("username"),
                                        jsonObj.getString("stepsContributed")
                                )
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
