package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Fragment used to create a plan.
 */
public class CreatePlanFragment extends Fragment {

    /**
     * Interface for MainActivity to implement
     */
    public interface onCreateFragmentLoad {
        /**
         * Submits a new plan
         */
        void onSubmitPlan();
    }

    onCreateFragmentLoad fragmentLoad;
    Button btnSubmitPlan;

    /**
     * Required default constructor
     */
    public CreatePlanFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentLoad = (onCreateFragmentLoad) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_plan, container, false);
        btnSubmitPlan = v.findViewById(R.id.btnSubmitPlan);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSubmitPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentLoad.onSubmitPlan();
            }
        });
    }
}
