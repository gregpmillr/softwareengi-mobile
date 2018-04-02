package com.softengi.mobcomp.softwareengi_mobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Actions.AuthAction;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

/**
 * Handles logging in the user. This Activity supports remembering the User's credentials
 * through Shared Preferences. Credentials are validated.
 */
public class LoginActivity extends AppCompatActivity {

    private CheckBox chkRememberMe;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);
        etUsername    = findViewById(R.id.etUsername);
        etPassword    = findViewById(R.id.etPassword);
        chkRememberMe = findViewById(R.id.chkRememberMe);
        final Context context = getApplicationContext();

        // check if user wants to load their previous data
        boolean rememberMe = SharedPrefManager.getInstance(getApplicationContext()).getRememberMe();
        chkRememberMe.setChecked(rememberMe);
        if(rememberMe) {
            etUsername.setText(SharedPrefManager.getInstance(getApplicationContext()).getUsername());
            etPassword.setText(SharedPrefManager.getInstance(getApplicationContext()).getPassword());
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // store all inputs into HashMap
                boolean valid = true;
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String rememberMe = String.valueOf(chkRememberMe.isChecked());
                // validate
                if(TextUtils.isEmpty(username)) {
                    etUsername.setError("Please enter your username");
                    valid = false;
                }
                if(TextUtils.isEmpty(password)) {
                    etPassword.setError("Please enter your password");
                    valid = false;
                }
                if(valid) { AuthAction.postLogin(context, username, password, rememberMe); }
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
