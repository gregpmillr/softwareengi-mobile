package com.softengi.mobcomp.softwareengi_mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Actions.TeamAction;
import com.softengi.mobcomp.softwareengi_mobile.Actions.UserAction;
import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListTeamAdapter;
import com.softengi.mobcomp.softwareengi_mobile.Adapters.SectionsPageAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.TeamDataModel;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;
import com.softengi.mobcomp.softwareengi_mobile.Utils.TeamsLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity representing actions for teams
 */
public class TeamsActivity extends AppCompatActivity  implements AllTeamsFragment.onAllTeamsFragmentLoad,
        YourTeamsFragment.onYourTeamsFragmentLoad {

    private static final String TAG = "TeamsActivity";
    AlertDialog alertDialog;
    AlertDialog listUsersAlertDialog;
    ListView  lvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        ViewPager mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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
                            listData.clear();

                            for(int i = 0; i < data.length(); i++) {
                                JSONObject jsonObj = data.getJSONObject(i);

                                listData.add(
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
    public void onCreateTeam(String name, ArrayList<String> selectedUsers, TeamsLoader teamsLoader) {
        TeamAction.postCreateTeams(getApplicationContext(),name,selectedUsers, teamsLoader);
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

                                listData.add(
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
    public void onTeamDetail(final TeamDataModel dataModel, final String TAG) {
        Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
        EditText etTeamCreateName;
        alertDialog = new AlertDialog.Builder(TeamsActivity.this).create();
        View ad = this.getLayoutInflater().inflate(R.layout.alertdialog_create_team, null);
        alertDialog.setTitle("Team Information");
        alertDialog.setCancelable(false);
        etTeamCreateName = ad.findViewById(R.id.etTeamCreateName);
        etTeamCreateName.setFocusable(false);
        etTeamCreateName.setText(dataModel.getName());

        if(TAG.equals(AllTeamsFragment.TAG)) {
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "JOIN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TeamAction.postJoinTeam(getApplicationContext(),SharedPrefManager.getInstance(getApplicationContext()).getUsername(), dataModel.getId());
                }
            });
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "VIEW MEMBERS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "VIEWING MEMBERS", Toast.LENGTH_SHORT).show();
                listUsersAlertDialog = new AlertDialog.Builder(TeamsActivity.this).create();
                View listAd = TeamsActivity.this.getLayoutInflater().inflate(R.layout.alertdialog_select_users, null);
                listUsersAlertDialog.setTitle("Users:");
                listUsersAlertDialog.setCancelable(false);
                lvUsers = listAd.findViewById(R.id.lvUsers);
                getUsersInTeam(lvUsers, dataModel.getId());

                if(TAG.equals(AllTeamsFragment.TAG)) {
                    listUsersAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "JOIN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TeamAction.postJoinTeam(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUsername(), dataModel.getId());
                        }
                    });
                }

                listUsersAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listUsersAlertDialog.dismiss();
                    }
                });

                listUsersAlertDialog.setView(listAd);
                if(!(TeamsActivity.this.isFinishing())) {
                    listUsersAlertDialog.show();
                }
            }
        });

        alertDialog.setView(ad);
        if(!(TeamsActivity.this.isFinishing())) {
            alertDialog.show();
        }

    }

    @Override
    public void onYourTeamDelete(String teamId, final ArrayListTeamAdapter adapter, final ArrayList<TeamDataModel> data) {
        // deletes a team
        TeamAction.postDeleteTeam(getApplicationContext(),teamId, new SuccessListener() {
            @Override
            public void successful() {
                loadYourTeamsAdapter(adapter, data);
            }
        });
    }

    @Override
    public void getUsers(final ListView lvUsers) {
        // gets a lit of users
        UserAction.getListOfUsers(getApplicationContext(), new ListParser() {
            @Override
            public void onSuccessResponse(JSONArray data) {
                String[] users = new String[data.length()];

                for(int i=0;i<data.length();i++) {
                    JSONObject jsonObj;
                    try {
                        jsonObj = data.getJSONObject(i);
                        users[i] = jsonObj.getString("username");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, users);
                lvUsers.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onAllTeamDelete(String teamId, final ArrayListTeamAdapter adapter, final ArrayList<TeamDataModel> data) {
        // deletes a team
        TeamAction.postDeleteTeam(getApplicationContext(),teamId, new SuccessListener() {
            @Override
            public void successful() {
                loadAllTeamsAdapter(adapter, data);
            }
        });
    }

    public void getUsersInTeam(final ListView lvUsers, String teamId) {
        // gets a lit of users in a team
        TeamAction.getListOfUsersInTeam(getApplicationContext(), teamId, new ListParser() {
            @Override
            public void onSuccessResponse(JSONArray data) {
                String[] users = new String[data.length()];

                for(int i=0;i<data.length();i++) {
                    JSONObject jsonObj;
                    try {
                        jsonObj = data.getJSONObject(i);
                        users[i] = jsonObj.getString("username");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, users);
                lvUsers.setAdapter(adapter);
            }
        });
    }
}
