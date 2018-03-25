package com.softengi.mobcomp.softwareengi_mobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Actions.AuthAction;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        final Context context = getApplicationContext();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 AuthAction.postLogin(
                        context, etUsername, etPassword);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        // create alarm
        // from https://stackoverflow.com/questions/34517520/how-to-give-notifications-on-android-on-specific-time
        Intent notifyIntent = new Intent(this,DailyNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (context, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis()+5000,
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }
}
