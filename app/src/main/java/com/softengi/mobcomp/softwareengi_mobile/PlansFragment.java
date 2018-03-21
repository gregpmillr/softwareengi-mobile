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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlansFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public interface onFragmentLoad {
        void loadAdapter(ArrayAdapter listAdapter, ArrayList<String> data);
        void onCreatePlan();
        void onPlanDetail(String title);
    }

    onFragmentLoad fragmentLoad;
    Button btnCreatePlan;

    public PlansFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            fragmentLoad = (onFragmentLoad) context;
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

        ArrayList<String> data;
        ArrayAdapter<String> arrayAdapter;

        data = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,data);

        setListAdapter(arrayAdapter);
        getListView().setOnItemClickListener(this);
        fragmentLoad.loadAdapter(arrayAdapter, data);

        btnCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentLoad.onCreatePlan();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        TextView test = (TextView) view;

        fragmentLoad.onPlanDetail(test.getText().toString());
    }
}
