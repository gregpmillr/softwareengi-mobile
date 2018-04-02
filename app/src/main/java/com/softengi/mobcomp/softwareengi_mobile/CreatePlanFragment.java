package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
        void onSubmitPlan(String title, String requiredSteps);
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
                boolean isValid = true;
                EditText etTitle = getActivity().findViewById(R.id.etPlanCreateTitle);
                EditText etRequiredSteps = getActivity().findViewById(R.id.etPlanCreateRequiredSteps);
                String title = etTitle.getText().toString();
                String requiredSteps = etRequiredSteps.getText().toString();
                // validate
                if(TextUtils.isEmpty(title)) {
                    etTitle.setError("Please enter a title");
                    isValid = false;
                }
                if(TextUtils.isEmpty(requiredSteps) || requiredSteps.equals("0")) {
                    etRequiredSteps.setError("Please enter the required steps. A plan cannot have zero required steps.");
                    isValid = false;
                }
                if(isValid) {
                    fragmentLoad.onSubmitPlan(title, requiredSteps);
                }
            }
        });
    }
}
