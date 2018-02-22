package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Controllers.AuthController;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        final Context context = getApplicationContext();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean success = AuthController.postLogin(
                        context, etUsername, etPassword);
                if(success){
                    // go to profile
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }
}
