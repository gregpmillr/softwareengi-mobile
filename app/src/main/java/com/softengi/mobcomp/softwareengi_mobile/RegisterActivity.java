package com.softengi.mobcomp.softwareengi_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Actions.AuthAction;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etLanguage;
    private CheckBox chkCoach;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail    = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etLanguage = findViewById(R.id.etLanguage);
        chkCoach   = findViewById(R.id.chkCoach);
        btnSubmit  = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthAction.postRegister(
                        getApplicationContext(), etUsername, etEmail,
                        etPassword, etLanguage, chkCoach
                );

            }
        });

    }
}
