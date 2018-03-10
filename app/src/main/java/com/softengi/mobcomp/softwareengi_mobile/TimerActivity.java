package com.softengi.mobcomp.softwareengi_mobile;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    private Button mBtnStopwatchStartPause, mBtnStopwatchStop;
    private Chronometer mChronometer;

    private long lastPause = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mBtnStopwatchStartPause = findViewById(R.id.btnStopwatchStartPause);
        mBtnStopwatchStop       = findViewById(R.id.btnStopwatchStop);
        mChronometer            = findViewById(R.id.chrStopWatch);
        mChronometer.setCountDown(false);

        mBtnStopwatchStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBtnStopwatchStartPause.isSelected()) {
                    // pause
                    lastPause = SystemClock.elapsedRealtime();
                    mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                    mChronometer.stop();
                    mBtnStopwatchStartPause.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp,null));
                } else {
                    // resume
                    if(lastPause == 0) {
                        mChronometer.setBase(SystemClock.elapsedRealtime());
                    } else {
                        mChronometer.setBase(mChronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                    }
                    mChronometer.start();
                    mBtnStopwatchStartPause.setBackground(getResources().getDrawable(R.drawable.ic_pause_black_24dp,null));
                }

                mBtnStopwatchStartPause.setSelected(!mBtnStopwatchStartPause.isSelected());

            }
        });

        mBtnStopwatchStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChronometer.stop();
                mBtnStopwatchStartPause.setSelected(false);
                mBtnStopwatchStartPause.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp,null));
                mChronometer.setBase(SystemClock.elapsedRealtime());
                lastPause = 0;
            }
        });

    }
}
