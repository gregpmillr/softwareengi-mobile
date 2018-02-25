package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUser = findViewById(R.id.tvUsername);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());

        tvUser.setText(sharedPrefManager.getUsername());

    }
}
