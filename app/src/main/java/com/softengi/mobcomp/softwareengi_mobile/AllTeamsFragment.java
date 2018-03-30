package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListTeamAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.TeamDataModel;

import java.util.ArrayList;

public class AllTeamsFragment extends Fragment {

    public interface onAllTeamsFragmentLoad {
        void loadAllTeamsAdapter(ArrayListTeamAdapter adapter, ArrayList<TeamDataModel> data);
        void onCreateTeam();
        void onTeamDetail(TeamDataModel dataModel);
    }

    private static final String TAG = "AllTeamsFragment";
    onAllTeamsFragmentLoad mFragmentListener;
    ArrayList<TeamDataModel> dataModels;
    ListView lvTeams;
    Button btnCreateTeam;
    private ArrayListTeamAdapter adapter;

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

        btnCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Create team stub", Toast.LENGTH_SHORT);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataModels = new ArrayList<>();
        // fill data here
        adapter = new ArrayListTeamAdapter(dataModels, getActivity().getApplicationContext());

        lvTeams.setAdapter(adapter);
        Toast.makeText(getContext(),"LOADING DATA", Toast.LENGTH_SHORT).show();
        mFragmentListener.loadAllTeamsAdapter(adapter, dataModels);

        btnCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.onCreateTeam();
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
