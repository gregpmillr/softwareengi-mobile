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
import android.widget.ListView;

import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListTeamAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.TeamDataModel;

import java.util.ArrayList;

public class YourTeamsFragment extends Fragment {

    public interface onYourTeamsFragmentLoad {
        void loadYourTeamsAdapter(ArrayListTeamAdapter adapter, ArrayList<TeamDataModel> data);
        void onTeamDetail(TeamDataModel dataModel);
    }

    private static final String TAG = "YourTeamsFragment";
    YourTeamsFragment.onYourTeamsFragmentLoad mFragmentListener;
    ArrayList<TeamDataModel> dataModels;
    ListView lvTeams;
    private ArrayListTeamAdapter adapter;

    public YourTeamsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mFragmentListener = (YourTeamsFragment.onYourTeamsFragmentLoad) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams_yours, container, false);
        lvTeams       = view.findViewById(R.id.lvYourTeams);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataModels = new ArrayList<>();
        // fill data here
        adapter = new ArrayListTeamAdapter(dataModels, getActivity().getApplicationContext());

        lvTeams.setAdapter(adapter);
        mFragmentListener.loadYourTeamsAdapter(adapter, dataModels);

        lvTeams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TeamDataModel dataModel = dataModels.get(position);
                mFragmentListener.onTeamDetail(dataModel);
            }
        });

    }
}
