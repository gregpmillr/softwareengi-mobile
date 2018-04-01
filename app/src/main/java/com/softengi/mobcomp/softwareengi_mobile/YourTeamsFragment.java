package com.softengi.mobcomp.softwareengi_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        void onTeamDetail(TeamDataModel dataModel, String TAG);
        void onYourTeamDelete(String teamId, ArrayListTeamAdapter adapter, ArrayList<TeamDataModel> data);
    }

    public static final String TAG = "YourTeamsFragment";
    YourTeamsFragment.onYourTeamsFragmentLoad mFragmentListener;
    ArrayList<TeamDataModel> dataModels = new ArrayList<>();;
    ListView lvTeams;
    private ArrayListTeamAdapter adapter;
    private boolean isLoaded = false;
    AlertDialog alertDialog;

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

        // fill data here
        adapter = new ArrayListTeamAdapter(dataModels, getActivity().getApplicationContext());

        lvTeams.setAdapter(adapter);
        mFragmentListener.loadYourTeamsAdapter(adapter, dataModels);

        lvTeams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TeamDataModel dataModel = dataModels.get(position);
                mFragmentListener.onTeamDetail(dataModel, TAG);
            }
        });

        lvTeams.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final TeamDataModel dataModel = dataModels.get(position);

                // show dialog to confirm delete
                alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Are you sure you want to delete this team?");
                alertDialog.setCancelable(false);

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFragmentListener.onYourTeamDelete(dataModel.getId(), adapter, dataModels);
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                if(!((Activity) getContext()).isFinishing()) {
                    alertDialog.show();
                }

                return true;
            }
        });

        isLoaded = true;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isLoaded) {
            mFragmentListener.loadYourTeamsAdapter(adapter, dataModels);
        }
    }
}
