package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUser;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUser    = findViewById(R.id.tvUsername);
        btnLogout = findViewById(R.id.btnLogout);

        final SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());

        tvUser.setText(sharedPrefManager.getUsername());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.logout();
                getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
