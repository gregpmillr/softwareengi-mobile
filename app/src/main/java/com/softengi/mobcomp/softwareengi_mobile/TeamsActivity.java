package com.softengi.mobcomp.softwareengi_mobile;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Actions.TeamAction;
import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListTeamAdapter;
import com.softengi.mobcomp.softwareengi_mobile.Adapters.SectionsPageAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.TeamDataModel;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeamsActivity extends AppCompatActivity  implements AllTeamsFragment.onAllTeamsFragmentLoad,
        YourTeamsFragment.onYourTeamsFragmentLoad {

    private static final String TAG = "TeamsActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Toast.makeText(getApplicationContext(), "TEST CREATE TEAMS ACTIVITY", Toast.LENGTH_SHORT).show();
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllTeamsFragment(), "ALLTEAMS");
        adapter.addFragment(new YourTeamsFragment(), "YOURTEAMS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void loadAllTeamsAdapter(final ArrayListTeamAdapter adapter, final ArrayList<TeamDataModel> listData) {
        TeamAction.getListOfAllTeams(
                getApplication(),
                new ListParser() {
                    @Override
                    public void onSuccessResponse(JSONArray data) {
                        try {
                            Toast.makeText(getApplicationContext(), "API REQUEST SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            listData.clear();

                            for(int i = 0; i < data.length(); i++) {
                                JSONObject jsonObj = data.getJSONObject(i);
                                // get all members
                                ArrayList<String> memberList = new ArrayList<String>();
                                JSONArray jsonArray = jsonObj.getJSONArray("members");
                                if(jsonArray != null) {
                                    int len = jsonArray.length();
                                    for(int j=0; j<len;j++) {
                                        memberList.add(jsonArray.get(i).toString());
                                    }
                                }
                                listData.add(
                                        new TeamDataModel(
                                                jsonObj.getString("name"),
                                                memberList,
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
    public void onCreateTeam() {

    }

    @Override
    public void loadYourTeamsAdapter(final ArrayListTeamAdapter adapter, final ArrayList<TeamDataModel> listData) {
        TeamAction.getListOfYourTeams(
                getApplication(),
                SharedPrefManager.getInstance(getApplicationContext()).getUsername(),
                new ListParser() {
                    @Override
                    public void onSuccessResponse(JSONArray data) {
                        try {
                            listData.clear();

                            for(int i = 0; i < data.length(); i++) {
                                JSONObject jsonObj = data.getJSONObject(i);
                                // get all members
                                ArrayList<String> memberList = new ArrayList<String>();
                                JSONArray jsonArray = jsonObj.getJSONArray("members");
                                if(jsonArray != null) {
                                    int len = jsonArray.length();
                                    for(int j=0; j<len;j++) {
                                        memberList.add(jsonArray.get(i).toString());
                                    }
                                }
                                listData.add(
                                        new TeamDataModel(
                                                jsonObj.getString("name"),
                                                memberList,
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
    public void onTeamDetail(TeamDataModel dataModel) {

    }
}
