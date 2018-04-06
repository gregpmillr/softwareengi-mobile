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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.softengi.mobcomp.softwareengi_mobile.Actions.StepAction;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Fragment representing the detail of a plan
 *
 * Uses GraphView
 * @see <a href="http://www.android-graphview.org/">Graph View documentation
 */
public class PlansDetailFragment extends Fragment {

    /**
     * Interface for MainActivity to implement
     */
    public interface onPlansDetail {
        /**
         * Deletes a plan
         * @param planId Id of plan to delete
         */
        void deletePlan(String planId);

        /**
         * Updates a plan
         * @param title New or old title of plan
         * @param requiredSteps New or old required steps of plan
         * @param planId Id of plan
         */
        void updatePlan(String title, String requiredSteps, String planId);

        /**
         * Navigates to the steps of a plan
         * @param planId Id of plan
         * @param title Title of plan
         * @param requiredSteps Required steps of plan
         * @param totalSteps Total steps of plan
         */
        void toStepFragment(String planId, String title, String requiredSteps, String totalSteps);

        /**
         * Gets progress of a plan for all users associated with that plan
         * @param planId Id of plan
         */
        void toUserPlanProgress(String planId);
    }

    private String mPlanTitle;
    private String mPlanRequiredSteps;
    private String mPlanId;
    private EditText etPlanDetailTitle;
    private EditText etPlanDetailRequiredSteps;
    private Button btnPlanDetailDelete, btnPlanDetailUpdate, btnToStep, btnToUserPlanProgress;

    private GraphView gvStep;
    private LineGraphSeries<DataPoint> stepEntries;

    private int totalSteps = 0;

    onPlansDetail plansDetail;

    /**
     * Required empty constructor
     */
    public PlansDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlanId            = getArguments().getString(getString(R.string.plan_id));
            mPlanTitle         = getArguments().getString(getString(R.string.plan_title));
            mPlanRequiredSteps = getArguments().getString(getString(R.string.plan_required_steps));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plans_detail, container, false);
        etPlanDetailTitle = v.findViewById(R.id.etPlanDetailTitle);
        etPlanDetailRequiredSteps = v.findViewById(R.id.etPlanDetailRequiredSteps);
        btnPlanDetailDelete = v.findViewById(R.id.btnDetailDelete);
        btnPlanDetailUpdate = v.findViewById(R.id.btnDetailUpdate);
        btnToStep = v.findViewById(R.id.btnToStep);
        gvStep = v.findViewById(R.id.gvGraph);
        btnToUserPlanProgress = v.findViewById(R.id.btnToUserPlanProgress);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            plansDetail = (PlansDetailFragment.onPlansDetail) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etPlanDetailTitle.setText(mPlanTitle);
        etPlanDetailRequiredSteps.setText(mPlanRequiredSteps);

        btnPlanDetailUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;
                String title = etPlanDetailTitle.getText().toString();
                String requiredSteps = etPlanDetailRequiredSteps.getText().toString();
                // validate
                if(TextUtils.isEmpty(title)) {
                    etPlanDetailTitle.setError("Please enter a title");
                    isValid = false;
                }
                if(TextUtils.isEmpty(requiredSteps) || requiredSteps.equals("0")) {
                    etPlanDetailRequiredSteps.setError("Please enter the required steps. A plan cannot have zero required steps.");
                    isValid = false;
                }
                if(isValid) {
                    plansDetail.updatePlan(title, requiredSteps, mPlanId);
                }
            }
        });

        btnPlanDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plansDetail.deletePlan(mPlanId);
            }
        });

        btnToStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plansDetail.toStepFragment(mPlanId, mPlanTitle, mPlanRequiredSteps, ""+totalSteps);
            }
        });

        btnToUserPlanProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plansDetail.toUserPlanProgress(mPlanId);
            }
        });

        stepEntries = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0,0)});
        gvStep.addSeries(stepEntries);
        gvStep.getViewport().setXAxisBoundsManual(true);
        gvStep.getViewport().setYAxisBoundsManual(true);
        gvStep.getViewport().setMinX(0);
        gvStep.getViewport().setMaxX(8);
        gvStep.getViewport().setMinY(0);
        gvStep.getViewport().setMaxY(100);
        gvStep.getViewport().setScrollable(true);
        gvStep.getViewport().setScalableY(true);
        stepEntries.setDrawDataPoints(true);
        stepEntries.setDataPointsRadius(10);
        stepEntries.setThickness(8);

        // gets all steps by the plan
        StepAction.getStepsByPlan(getActivity(), mPlanId, new ListParser() {
            @Override
            public void onSuccessResponse(JSONArray response) {
                updateGraph(response);
                totalSteps = getTotalSteps(response);
            }
        });
    }

    /**
     * Updates the graph for plan progress
     * @param response JSONArray value
     */
    public void updateGraph(JSONArray response) {
        try {
            int steps;
            int i;
            for (i = 0; i < response.length(); i++) {
                JSONObject jsonObj = response.getJSONObject(i);
                steps = jsonObj.getInt("steps");
                stepEntries.appendData(new DataPoint(i+1, steps), true, 99999);
            }
            gvStep.getViewport().setMinX(0);
            gvStep.getViewport().setMaxX(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static int getTotalSteps(JSONArray response) {
        int steps;
        int totalSteps = 0;
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObj = response.getJSONObject(i);
                steps = jsonObj.getInt("steps");
                totalSteps += steps;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return totalSteps;
    }
}
