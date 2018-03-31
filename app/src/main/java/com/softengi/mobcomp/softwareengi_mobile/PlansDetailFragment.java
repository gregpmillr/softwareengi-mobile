package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.softengi.mobcomp.softwareengi_mobile.Actions.StepAction;
import com.softengi.mobcomp.softwareengi_mobile.Utils.StepParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlansDetailFragment extends Fragment {

    public interface onPlansDetail {
        void deletePlan(String planId);
        void updatePlan(EditText title, EditText requiredSteps, String planId);
        void toStepFragment(String planId, String title, String requiredSteps, String totalSteps);
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

    public PlansDetailFragment() {
        // Required empty public constructor
    }

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
                plansDetail.updatePlan(etPlanDetailTitle, etPlanDetailRequiredSteps, mPlanId);
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

        stepEntries = new LineGraphSeries<>(new DataPoint[]{});
        gvStep.addSeries(stepEntries);
        gvStep.getViewport().setXAxisBoundsManual(true);
        gvStep.getViewport().setMinX(3);
        gvStep.getViewport().setMaxX(8);
        gvStep.getViewport().setScrollable(true);

        StepAction.getStepsByPlan(getActivity(), mPlanId, stepEntries, new StepParser() {
            @Override
            public void onSuccessResponse(JSONArray response) {
                try {
                    int steps;
                    /*
                    TimeZone tz = TimeZone.getTimeZone("UTC");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    df.setTimeZone(tz);
                    Date d;
                    */
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObj = response.getJSONObject(i);
                        steps = jsonObj.getInt("steps");
                        //try {
                        //d = df.parse(jsonObj.getString("updated_at"));
                        //stepEntries.appendData(new DataPoint(d, jsonObj.getInt("steps")), false, 99999);
                        stepEntries.appendData(new DataPoint(i, steps), true, 99999);
                        //} catch(ParseException e) {
                        //    e.printStackTrace();
                        //}

                        totalSteps += steps;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
