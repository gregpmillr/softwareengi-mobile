package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.softengi.mobcomp.softwareengi_mobile.Adapters.ArrayListUserPlanAdapter;
import com.softengi.mobcomp.softwareengi_mobile.DataModels.UserPlanProgressDataModel;

import java.util.ArrayList;

public class UserPlanProgressFragment  extends Fragment {

    public interface onUserPlanProgressFragmentLoad {
        void loadUserPlanProgressAdapter(ArrayListUserPlanAdapter adapter, ArrayList<UserPlanProgressDataModel> data, String planId);
    }

    onUserPlanProgressFragmentLoad mFragmentListener;
    ArrayList<UserPlanProgressDataModel> dataModels;
    ListView lvUserPlanProgress;
    private ArrayListUserPlanAdapter adapter;
    private String mPlanId;

    public UserPlanProgressFragment() {
        // required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlanId = getArguments().getString(getString(R.string.plan_id));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mFragmentListener = (UserPlanProgressFragment.onUserPlanProgressFragmentLoad) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_plan_progress, container, false);
        lvUserPlanProgress = v.findViewById(R.id.lvUserPlanProgress);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dataModels = new ArrayList<>();
        // fill data
        adapter = new ArrayListUserPlanAdapter(dataModels, getContext());

        lvUserPlanProgress.setAdapter(adapter);
        lvUserPlanProgress.setClickable(false);
        mFragmentListener.loadUserPlanProgressAdapter(adapter, dataModels, mPlanId);
    }

}
