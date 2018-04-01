package com.softengi.mobcomp.softwareengi_mobile;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListPlanAdapter;
import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListTeamAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.PlanDataModel;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.TeamDataModel;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

import java.util.ArrayList;

/**
 * Fragment representing a plan
 */
public class PlansFragment extends Fragment {

    /**
     * Interface for MainActivity to implement
     */
    public interface onPlansFragmentLoad {
        /**
         * Loads plans into the adapter
         * @param hmAdapter Adapter to load plans
         * @param data ArrayList of data models
         */
        void loadPlansAdapter(ArrayListPlanAdapter hmAdapter, ArrayList<PlanDataModel> data);

        /**
         * Creates a plan
         */
        void onCreatePlan();

        /**
         * Gets plan detail
         * @param dataModel Data model of plan
         */
        void onPlanDetail(PlanDataModel dataModel);

        /**
         * Lodas all plans into adapter
         * @param adapter Adapter to laod
         * @param teamDataModels ArrayList of data models
         */
        void loadAllTeamsAdapter(ArrayListTeamAdapter adapter, ArrayList<TeamDataModel> teamDataModels);

        /**
         * Assigns plans to all team members
         * @param planDataModel Plan to add users to
         * @param teamDataModel Team to add models to
         */
        void massAssignTeam(PlanDataModel planDataModel, TeamDataModel teamDataModel);
    }

    onPlansFragmentLoad mFragmentListener;
    Button btnCreatePlan;
    ArrayList<PlanDataModel> dataModels;
    ArrayList<TeamDataModel> teamDataModels = new ArrayList<>();
    PlanDataModel planDataModel;
    ListView lvPlans;
    private ArrayListTeamAdapter teamAdapter;
    private ArrayListPlanAdapter adapter;
    AlertDialog listTeamsAlertDialog;
    ListView lvMassTeams;

    /**
     * Required default constructor
     */
    public PlansFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentListener = (onPlansFragmentLoad) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plans, container, false);
        btnCreatePlan = v.findViewById(R.id.btnCreatePlan);
        lvPlans       = v.findViewById(R.id.lvPlans);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataModels = new ArrayList<>();
        // fill data here
        adapter = new ArrayListPlanAdapter(dataModels, getActivity().getApplicationContext());
        teamAdapter = new ArrayListTeamAdapter(teamDataModels, getActivity().getApplicationContext());

        lvPlans.setAdapter(adapter);
        lvPlans.setLongClickable(true);
        mFragmentListener.loadPlansAdapter(adapter, dataModels);

        btnCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.onCreatePlan();
            }
        });

        lvPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PlanDataModel dataModel = dataModels.get(position);
                mFragmentListener.onPlanDetail(dataModel);
            }
        });

        // Only allow mass assign to coaches
        if(SharedPrefManager.getInstance(getContext()).getCoach().equals("true")) {
            lvPlans.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    planDataModel = dataModels.get(position);

                    // mass assign plan
                    listTeamsAlertDialog = new AlertDialog.Builder(getContext()).create();
                    View listAd = PlansFragment.this.getLayoutInflater().inflate(R.layout.alertdialog_select_teams, null);
                    listTeamsAlertDialog.setTitle("Assign Team:");
                    listTeamsAlertDialog.setCancelable(false);
                    lvMassTeams = listAd.findViewById(R.id.lvMassTeams);
                    lvMassTeams.setAdapter(teamAdapter);
                    mFragmentListener.loadAllTeamsAdapter(teamAdapter, teamDataModels);

                    listTeamsAlertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CLOSE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listTeamsAlertDialog.dismiss();
                        }
                    });

                    listTeamsAlertDialog.setView(listAd);
                    if(!((Activity) getContext()).isFinishing()) {
                        listTeamsAlertDialog.show();
                    }

                    lvMassTeams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TeamDataModel teamDataModel = teamDataModels.get(position);
                            mFragmentListener.massAssignTeam(planDataModel, teamDataModel);
                            listTeamsAlertDialog.dismiss();
                        }
                    });


                    return true;
                }
            });
        }
    }

}
