package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Controllers.AuthController;

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
                boolean success = AuthController.postRegister(
                        getApplicationContext(), etUsername, etEmail, etPassword, etLanguage, chkCoach
                );

                if(success) {
                    // make user login
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
            }
        });

    }
}
