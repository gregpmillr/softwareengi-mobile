package com.softengi.mobcomp.softwareengi_mobile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.softengi.mobcomp.softwareengi_mobile.Actions.StepAction;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ProfileParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.StepParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PlansDetailFragment extends Fragment {

    public interface onPlansDetail {
        void deletePlan(String planId);
        void updatePlan(EditText title, EditText requiredSteps, String planId);
        void toStepFragment(String planId, String title, String requiredSteps, String totalSteps);
    }

    private String mPlanTitle;
    private String mPlanRequiredSteps;
    private String mPlanId;
    private EditText etPlanDetailTitle;
    private EditText etPlanDetailRequiredSteps;
    private Button btnPlanDetailDelete;
    private Button btnPlanDetailUpdate;
    private Button btnToStep;

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

        stepEntries = new LineGraphSeries<>(new DataPoint[]{});
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

        StepAction.getStepsByPlan(getActivity(), mPlanId, new StepParser() {
            @Override
            public void onSuccessResponse(JSONArray response) {
                updateGraph(response);
            }
        });
    }

    private void updateGraph(JSONArray response) {
        try {
            int steps;

            /*
            //for dates
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            df.setTimeZone(tz);
            Date d;
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObj = response.getJSONObject(i);
                    steps = jsonObj.getInt("steps");
                    d = df.parse(jsonObj.getString("updated_at"));
                    totalSteps += steps;
                    stepEntries.appendData(new DataPoint(d, steps), true, 99999);
                }
                gvStep.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
                //gvStep.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
                gvStep.getGridLabelRenderer().setHumanRounding(false);


            } catch (ParseException e) {
                e.printStackTrace();
            }
            */


            //for non-dates
            int i;
            for (i = 0; i < response.length(); i++) {
                JSONObject jsonObj = response.getJSONObject(i);
                steps = jsonObj.getInt("steps");
                stepEntries.appendData(new DataPoint(i, steps), true, 99999);
                totalSteps += steps;
            }
            gvStep.getViewport().setMinX(0);
            gvStep.getViewport().setMaxX(i-1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
