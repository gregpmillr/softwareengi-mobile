package com.softengi.mobcomp.softwareengi_mobile;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.softengi.mobcomp.softwareengi_mobile.Utils.HashMapAdapter;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlansFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public interface onPlansFragmentLoad {
        void loadPlansAdapter(HashMapAdapter hmAdapter, HashMap<Integer,String> data);
        void onCreatePlan();
        void onPlanDetail(Integer planId);
    }

    onPlansFragmentLoad mFragmentListener;
    Button btnCreatePlan;
    HashMap<Integer,String> data;

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

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        HashMapAdapter hmAdapter;

        data = new HashMap<Integer,String>();
        hmAdapter = new HashMapAdapter(getActivity().getApplicationContext(), data);

        setListAdapter(hmAdapter);
        getListView().setOnItemClickListener(this);
        mFragmentListener.loadPlansAdapter(hmAdapter, data);

        btnCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentListener.onCreatePlan();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        TextView test = (TextView) view;

        Integer planId = Integer.valueOf(data.get(test.getText().toString()));
        mFragmentListener.onPlanDetail(planId);
    }
}
