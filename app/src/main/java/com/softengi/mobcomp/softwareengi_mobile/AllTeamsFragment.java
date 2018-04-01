package com.softengi.mobcomp.softwareengi_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListTeamAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.TeamDataModel;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;
import com.softengi.mobcomp.softwareengi_mobile.Utils.TeamsLoader;

import java.util.ArrayList;

/**
 * Represents the Tab for displaying all teams. From here, the user
 * can create a new team (if they're a coach), view teams, and delete teams.
 */
public class AllTeamsFragment extends Fragment {

    /**
     * Interface for MainActivity to implement
     */
    public interface onAllTeamsFragmentLoad {
        /**
         * Loads all teams
         * @param adapter Adapter for ListView
         * @param data ArrayList of data models
         */
        void loadAllTeamsAdapter(ArrayListTeamAdapter adapter, ArrayList<TeamDataModel> data);

        /**
         * Creates a team
         * @param name new team name
         * @param selectedUsers members of the team
         * @param teamLoader Loader of data to display
         */
        void onCreateTeam(String name, ArrayList<String> selectedUsers, TeamsLoader teamLoader);

        /**
         * Shows the detail of a team
         * @param dataModel Model of Team to display
         * @param TAG Tag of Fragment
         */
        void onTeamDetail(TeamDataModel dataModel, String TAG);

        /**
         * Gets users to populate the ListView
         * @param lvUsers ListView to populate
         */
        void getUsers(ListView lvUsers);

        /**
         * Deletes a team
         * @param teamId Id of team to delete
         * @param adapter Adapter to re-populate data
         * @param data ArrayList of data models
         */
        void onAllTeamDelete(String teamId, ArrayListTeamAdapter adapter, ArrayList<TeamDataModel> data);
    }

    onAllTeamsFragmentLoad mFragmentListener;
    ArrayList<TeamDataModel> dataModels = new ArrayList<>();
    ArrayList<String> selectedUsers = new ArrayList<>();
    ListView lvTeams, lvUsers;
    Button btnCreateTeam;
    AlertDialog alertDialog;
    public static final String TAG = "AllTeamsFragment";
    private ArrayListTeamAdapter adapter;
    private EditText etTeamCreateName;
    private String name;
    AlertDialog listUsersAlertDialog;
    private boolean isLoaded = false;
    AlertDialog alertDialogDeleteTeam;

    /**
     * Required empty default constructor
     */
    public AllTeamsFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentListener = (onAllTeamsFragmentLoad) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams_all, container, false);
        btnCreateTeam = view.findViewById(R.id.btnCreateTeam);
        lvTeams       = view.findViewById(R.id.lvAllTeams);
        if(!SharedPrefManager.getInstance(getContext()).getCoach().equals("true")) {
            btnCreateTeam.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // fill data
        adapter = new ArrayListTeamAdapter(dataModels, getContext());
        mFragmentListener.loadAllTeamsAdapter(adapter, dataModels);
        lvTeams.setAdapter(adapter);
        // guide through the process of creating a team
        btnCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog   = new AlertDialog.Builder(getContext()).create();

                View ad = getActivity().getLayoutInflater().inflate(R.layout.alertdialog_create_team, null);
                alertDialog.setTitle("Create a team");
                alertDialog.setCancelable(false);
                etTeamCreateName = ad.findViewById(R.id.etTeamCreateName);

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "NEXT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!TextUtils.isEmpty(etTeamCreateName.getText().toString())) {
                            listUsersAlertDialog = new AlertDialog.Builder(getContext()).create();
                            name = etTeamCreateName.getText().toString();
                            View listAd = getActivity().getLayoutInflater().inflate(R.layout.alertdialog_select_users, null);
                            listUsersAlertDialog.setTitle("Choose Users");
                            listUsersAlertDialog.setCancelable(false);
                            lvUsers = listAd.findViewById(R.id.lvUsers);
                            mFragmentListener.getUsers(lvUsers);

                            lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String itemText = ((TextView)view).getText().toString();
                                    if(selectedUsers.contains(itemText)) {
                                        // already contained, user must be de-selecting!
                                        lvUsers.getChildAt(position).setBackgroundColor(Color.WHITE);
                                        selectedUsers.remove(itemText);
                                    } else {
                                        // selecting new user
                                        selectedUsers.add(itemText);
                                        lvUsers.getChildAt(position).setBackgroundColor(Color.GREEN);
                                    }

                                }
                            });

                            listUsersAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SUBMIT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mFragmentListener.onCreateTeam(name, selectedUsers, new TeamsLoader() {
                                        @Override
                                        public void loadTeams() {
                                            mFragmentListener.loadAllTeamsAdapter(adapter, dataModels);
                                        }
                                    });
                                }
                            });

                            listUsersAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    listUsersAlertDialog.dismiss();
                                }
                            });

                            listUsersAlertDialog.setView(listAd);
                            if(!((Activity) getContext()).isFinishing()) {
                                listUsersAlertDialog.show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Missing team name!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setView(ad);
                if(!((Activity) getContext()).isFinishing()) {
                    alertDialog.show();
                }
            }
        });

        lvTeams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TeamDataModel dataModel = dataModels.get(position);
                mFragmentListener.onTeamDetail(dataModel, TAG);
            }
        });

        // restrict deleting teams to coaches only
        if(SharedPrefManager.getInstance(getContext()).getCoach().equals("true")) {
            lvTeams.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final TeamDataModel dataModel = dataModels.get(position);

                    // show dialog to confirm delete
                    alertDialogDeleteTeam = new AlertDialog.Builder(getContext()).create();
                    alertDialogDeleteTeam.setTitle("Are you sure you want to delete this team?");
                    alertDialogDeleteTeam.setCancelable(false);

                    alertDialogDeleteTeam.setButton(AlertDialog.BUTTON_POSITIVE, "DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mFragmentListener.onAllTeamDelete(dataModel.getId(), adapter, dataModels);
                        }
                    });

                    alertDialogDeleteTeam.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialogDeleteTeam.dismiss();
                        }
                    });

                    if(!((Activity) getContext()).isFinishing()) {
                        alertDialogDeleteTeam.show();
                    }

                    return true;
                }
            });
        }


        isLoaded = true;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isLoaded) {
            mFragmentListener.loadAllTeamsAdapter(adapter, dataModels);
        }
    }

}
