package com.softengi.mobcomp.softwareengi_mobile;


import android.content.Context;
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
import com.softengi.mobcomp.softwareengi_mobile.DataModels.PlanDataModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlansFragment extends Fragment {

    public interface onPlansFragmentLoad {
        void loadPlansAdapter(ArrayListPlanAdapter hmAdapter, ArrayList<PlanDataModel> data);
        void onCreatePlan();
        void onPlanDetail(PlanDataModel dataModel);
    }

    onPlansFragmentLoad mFragmentListener;
    Button btnCreatePlan;
    ArrayList<PlanDataModel> dataModels;
    ListView lvPlans;
    private ArrayListPlanAdapter adapter;

    public PlansFragment() {
        // Required empty public constructor
    }

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

        lvPlans.setAdapter(adapter);
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

    }

}
