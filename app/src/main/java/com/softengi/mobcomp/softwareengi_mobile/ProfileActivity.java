package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Controllers.ProfileController;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private EditText etProfileUsername, etProfileEmail, etProfileLanguage, etProfileCoach;
    private Button btnLogout, btnProfileEmail, btnProfileLanguage, btnProfileCoach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etProfileUsername    = findViewById(R.id.etProfileUsername);
        etProfileEmail       = findViewById(R.id.etProfileEmail);
        etProfileLanguage    = findViewById(R.id.etProfileLanguage);
        etProfileCoach       = findViewById(R.id.etProfileCoach);
        btnLogout            = findViewById(R.id.btnLogout);
        btnProfileEmail      = findViewById(R.id.btnProfileEmail);
        btnProfileLanguage   = findViewById(R.id.btnProfileLanguage);
        btnProfileCoach      = findViewById(R.id.btnProfileCoach);

        final SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());

        etProfileUsername.setText(sharedPrefManager.getUsername());
        etProfileEmail.setText(sharedPrefManager.getEmail());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.logout();
                getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnProfileEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileController.postEmail(getApplicationContext(), etProfileEmail);
            }
        });

        btnProfileLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileController.postLanguage(getApplicationContext(), etProfileLanguage);
            }
        });

        btnProfileCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileController.postCoach(getApplicationContext(), etProfileCoach);

            }
        });

    }
}
