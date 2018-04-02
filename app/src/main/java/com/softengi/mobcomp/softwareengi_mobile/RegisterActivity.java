package com.softengi.mobcomp.softwareengi_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Actions.AuthAction;

/**
 * Activity used to register users
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etLanguage;
    private CheckBox chkCoach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail    = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etLanguage = findViewById(R.id.etLanguage);
        chkCoach   = findViewById(R.id.chkCoach);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String language = etLanguage.getText().toString();
                String coach = String.valueOf(chkCoach.isChecked());
                // register the user
                boolean valid = true;
                if(TextUtils.isEmpty(username)) {
                    etUsername.setError("Username is required");
                    valid = false;
                }
                if(TextUtils.isEmpty(email)) {
                    etEmail.setError("Email is required");
                    valid = false;
                }
                if(TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is required");
                    valid = false;
                }
                if(TextUtils.isEmpty(language)) {
                    etLanguage.setError("Language is required");
                    valid = false;
                }
                if(valid) {
                    AuthAction.postRegister(getApplicationContext(), username, email, password,
                            language, coach
                    );
                }
            }
        });

    }
}
