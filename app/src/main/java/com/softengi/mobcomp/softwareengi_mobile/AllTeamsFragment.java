package com.softengi.mobcomp.softwareengi_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListTeamAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.TeamDataModel;

import java.util.ArrayList;

public class AllTeamsFragment extends Fragment {

    public interface onAllTeamsFragmentLoad {
        void loadAllTeamsAdapter(ArrayListTeamAdapter adapter, ArrayList<TeamDataModel> data);
        void onCreateTeam(String name, ArrayList<String> selectedUsers);
        void onTeamDetail(TeamDataModel dataModel);
        void getUsers(ListView lvUsers);
    }

    onAllTeamsFragmentLoad mFragmentListener;
    ArrayList<TeamDataModel> dataModels;
    ArrayList<String> selectedUsers = new ArrayList<String>();
    ListView lvTeams, lvUsers;
    Button btnCreateTeam;
    AlertDialog alertDialog;
    private static final String TAG = "AllTeamsFragment";
    private ArrayListTeamAdapter adapter;
    private EditText etTeamCreateName;
    private String name;
    AlertDialog listUsersAlertDialog;

    public AllTeamsFragment() {
        // Required empty public constructor
    }

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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataModels = new ArrayList<>();
        // fill data here
        adapter = new ArrayListTeamAdapter(dataModels, getActivity().getApplicationContext());

        lvTeams.setAdapter(adapter);
        mFragmentListener.loadAllTeamsAdapter(adapter, dataModels);

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
                        listUsersAlertDialog = new AlertDialog.Builder(getContext()).create();
                        name = etTeamCreateName.getText().toString();
                        System.out.println("AD NAME: " + name);
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

                        listUsersAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONTINUE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFragmentListener.onCreateTeam(name, selectedUsers);
                            }
                        });

                        listUsersAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listUsersAlertDialog.dismiss();
                            }
                        });

                        listUsersAlertDialog.setView(listAd);
                        listUsersAlertDialog.show();
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setView(ad);
                alertDialog.show();
            }
        });

        lvTeams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TeamDataModel dataModel = dataModels.get(position);
                mFragmentListener.onTeamDetail(dataModel);
            }
        });

    }
}
