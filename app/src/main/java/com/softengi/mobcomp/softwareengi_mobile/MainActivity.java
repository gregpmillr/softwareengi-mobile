package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Controllers.AuthController;

public class MainActivity extends AppCompatActivity {

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

                boolean successfulPost = AuthController.postLogin(
                        context, etUsername, etPassword);

                if(successfulPost){
                    // go to profile
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Unable to login!",
                            Toast.LENGTH_SHORT);
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                context.startActivity(intent);
            }
        });

    }
}
