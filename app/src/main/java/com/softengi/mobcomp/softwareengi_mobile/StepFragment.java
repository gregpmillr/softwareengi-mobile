package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.softengi.mobcomp.softwareengi_mobile.Actions.StepAction;
import com.softengi.mobcomp.softwareengi_mobile.Utils.StepDetector;
import com.softengi.mobcomp.softwareengi_mobile.Utils.StepListener;

import java.util.LinkedList;

/**
 * Fragment representing a step
 *
 * Uses GraphView
 * @see <a href="http://www.android-graphview.org/">Graph View documentation
 */
public class StepFragment extends Fragment implements SensorEventListener, StepListener {

    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int planId = 0;
    private int requiredSteps = 100;
    private int totalSteps = 0;
    private String planTitle = "placeholder";
    private TextView tvStep, tvPace, tvPlanDescription;
    private Button btnStartPause;
    private Button btnStop;
    private Chronometer mChronometer;
    private GraphView gvPace, gvStep;
    private LineGraphSeries<DataPoint> paceEntries, stepEntries;
    private int xGraph;
    private int numSteps = 0;
    private long lastPause = 0;
    private int pausedSteps = 0;

    private LinkedList<Integer> timestamps;
    String pace;

    /**
     * Required empty constructor
     */
    public StepFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            planId            = Integer.valueOf(getArguments().getString(getString(R.string.plan_id)));
            planTitle         = getArguments().getString(getString(R.string.plan_title));
            requiredSteps   = Integer.valueOf(getArguments().getString(getString(R.string.plan_required_steps)));
            totalSteps      = Integer.valueOf(getArguments().getString(getString(R.string.plan_total_steps)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step, container, false);
        tvStep              = v.findViewById(R.id.tvStep);
        tvPace              = v.findViewById(R.id.tvPace);
        tvPlanDescription   = v.findViewById(R.id.tvPlanDescription);
        btnStartPause       = v.findViewById(R.id.btnStartPause);
        btnStop             = v.findViewById(R.id.btnStop);
        simpleStepDetector  = new StepDetector();
        sensorManager       = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel               = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mChronometer        = v.findViewById(R.id.chrStopWatch);
        gvPace              = v.findViewById(R.id.gvPace);
        gvStep              = v.findViewById(R.id.gvStep);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvPlanDescription.setText(planTitle);

        mChronometer.setCountDown(false);

        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
        {
            @Override
            public void onChronometerTick(Chronometer chronometer)
            {
                updatePace();
            }
        });

        paceEntries = new LineGraphSeries<>(new DataPoint[]{});
        gvPace.addSeries(paceEntries);
        xGraph = -1;

        gvPace.getGridLabelRenderer().setPadding(50);

        stepEntries = new LineGraphSeries<>(new DataPoint[]{});
        gvStep.getViewport().setYAxisBoundsManual(true);
        gvStep.getViewport().setMinY(0);
        gvStep.getViewport().setMaxY(requiredSteps);

        gvStep.getViewport().setXAxisBoundsManual(true);
        gvStep.getViewport().setMinX(0);
        gvStep.getViewport().setMaxX(requiredSteps);

        gvStep.addSeries(stepEntries);
        for (int i=0; i<=totalSteps; i++) {
            stepEntries.appendData(new DataPoint(i,i), false, requiredSteps);
        }

        simpleStepDetector.registerListener(this);
        btnStartPause.setSelected(false);

        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnStartPause.isSelected()) {
                    // pause
                    lastPause = SystemClock.elapsedRealtime();
                    mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                    mChronometer.stop();

                    pausedSteps = numSteps;
                    sensorManager.unregisterListener(StepFragment.this);
                    btnStartPause.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp,null));
                } else {
                    // resume
                    if(lastPause == 0) {
                        timestamps = new LinkedList<>();
                        mChronometer.setBase(SystemClock.elapsedRealtime());
                        pace = "0 " + R.string.pace;
                        tvPace.setText(pace);
                    } else {
                        mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                    }
                    mChronometer.start();

                    numSteps = pausedSteps;
                    sensorManager.registerListener(StepFragment.this, accel,SensorManager.SENSOR_DELAY_FASTEST);
                    btnStartPause.setBackground(getResources().getDrawable(R.drawable.ic_pause_black_24dp,null));
                }

                btnStartPause.setSelected(!btnStartPause.isSelected());

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(numSteps > 0) {
                        StepAction.postSteps(
                                getActivity().getApplicationContext(),
                                numSteps,
                                planId
                        );
                    } else {
                        Toast.makeText(getContext(), "You haven't taken any steps yet!", Toast.LENGTH_SHORT).show();
                    }



                xGraph = -1;
                paceEntries.resetData(new DataPoint[]{});

                mChronometer.stop();
                lastPause = 0;

                numSteps = 0;
                pausedSteps = 0;
                tvStep.setText(getResources().getString(R.string.steps));
                btnStartPause.setSelected(false);
                btnStartPause.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp,null));
                sensorManager.unregisterListener(StepFragment.this);
            }
        });
    }


        @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]
            );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        tvStep.setText("Steps: ".concat(String.valueOf(numSteps)));

        totalSteps++;
        stepEntries.appendData(new DataPoint(totalSteps, totalSteps), false, requiredSteps);
    }

    private void updatePace() {
        //pace change on every seconds passed
        if (timestamps.size() >= 5) {
            timestamps.removeFirst();
        }
        timestamps.addLast(numSteps);

        double pace = calculatePace(timestamps);
        xGraph++;

        paceEntries.appendData(new DataPoint(xGraph, pace), false, 10);
        tvPace.setText(pace + " " + getString(R.string.pace));
    }

    public static double calculatePace(LinkedList<Integer> timestamps) {
        double firstStep = timestamps.getFirst();
        double lastStep = timestamps.getLast();
        double diffStep = lastStep - firstStep;
        return diffStep / timestamps.size();
    }
}
