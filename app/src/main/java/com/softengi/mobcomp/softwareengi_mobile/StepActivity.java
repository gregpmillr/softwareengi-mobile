package com.softengi.mobcomp.softwareengi_mobile;

import com.softengi.mobcomp.softwareengi_mobile.Utils.StepDetector;
import com.softengi.mobcomp.softwareengi_mobile.Utils.StepListener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class StepActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps = 0;
    private int pausedSteps = 0;

    private TextView tvStep;
    private Button btnStartPause;
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_monitor);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        tvStep = findViewById(R.id.tvStep);
        btnStartPause = findViewById(R.id.btnStartPause);
        btnStop = findViewById(R.id.btnStop);

        btnStartPause.setSelected(false);

        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnStartPause.isSelected()) {
                    // resume
                    pausedSteps = numSteps;
                    sensorManager.unregisterListener(StepActivity.this);
                    btnStartPause.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp,null));

                } else {
                    // pause
                    numSteps = pausedSteps;
                    sensorManager.registerListener(StepActivity.this, accel,SensorManager.SENSOR_DELAY_FASTEST);
                    btnStartPause.setBackground(getResources().getDrawable(R.drawable.ic_pause_black_24dp,null));
                }

                btnStartPause.setSelected(!btnStartPause.isSelected());

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numSteps = 0;
                pausedSteps = 0;
                tvStep.setText("0");
                btnStartPause.setSelected(false);
                btnStartPause.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp,null));
                sensorManager.unregisterListener(StepActivity.this);
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
}
