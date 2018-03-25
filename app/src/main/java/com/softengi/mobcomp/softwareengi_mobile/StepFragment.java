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

import com.softengi.mobcomp.softwareengi_mobile.Actions.StepAction;
import com.softengi.mobcomp.softwareengi_mobile.Utils.StepDetector;
import com.softengi.mobcomp.softwareengi_mobile.Utils.StepListener;

import java.util.LinkedList;

/**
 * Created by br239 on 2018-03-25.
 */

public class StepFragment extends Fragment implements SensorEventListener, StepListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PLAN_TITLE = "plan_title";
    private static final String ARG_PLAN_ID = "plan_id";

    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int planId = 0;
    private String planTitle = "";
    private TextView tvStep, tvPace, tvPlanDescription;
    private Button btnStartPause;
    private Button btnStop;

    private Chronometer mChronometer;

    private int numSteps = 0;
    private long lastPause = 0;
    private int pausedSteps = 0;

    private LinkedList<Integer> timestamps;
    String pace;

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            planTitle         = getArguments().getString(ARG_PLAN_TITLE);
            planId            = Integer.valueOf(getArguments().getString(ARG_PLAN_ID));
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

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mChronometer.setCountDown(false);

        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener()
        {
            @Override
            public void onChronometerTick(Chronometer chronometer)
            {
                System.out.println("tick test " + SystemClock.elapsedRealtime());
                calculatePace();
            }
        });

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

                StepAction.postSteps(
                        getActivity().getApplicationContext(),
                        numSteps,
                        planId
                );

                numSteps = 0;
                pausedSteps = 0;
                tvStep.setText("0");
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
        tvStep.setText(String.valueOf(numSteps));
    }

    private void calculatePace() {
        /*
        //pace change on every steps made
        if (timestamps.size() >= 5) {
            timestamps.removeFirst();
        }
        timestamps.addLast(lastPause = SystemClock.elapsedRealtime());

        pace = timestamps.size() / (timestamps.getLast() - timestamps.getFirst()) + " " + R.string.pace;
        tvPace.setText(pace);
        */

        //pace change on every seconds passed
        if (timestamps.size() >= 5) {
            timestamps.removeFirst();
        }
        timestamps.addLast(numSteps);
        double firstStep = timestamps.getFirst();
        double lastStep = timestamps.getLast();
        double diffStep = lastStep - firstStep;
        double pace = diffStep / timestamps.size();
        tvPace.setText(pace + " " + getString(R.string.pace));
    }
}
