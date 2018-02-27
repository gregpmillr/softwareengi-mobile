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

    private EditText etProfileUsername, etProfileEmail, etProfileLanguage;
    private Button btnLogout, btnUpdateProfile;
    private CheckBox chkProfileCoach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etProfileUsername    = findViewById(R.id.etProfileUsername);
        etProfileEmail       = findViewById(R.id.etProfileEmail);
        etProfileLanguage    = findViewById(R.id.etProfileLanguage);
        chkProfileCoach      = findViewById(R.id.chkProfileCoach);
        btnLogout            = findViewById(R.id.btnLogout);
        btnUpdateProfile     = findViewById(R.id.btnUpdateProfile);

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

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean successfulPost = ProfileController.postUpdate(
                        getApplicationContext(), etProfileUsername, etProfileEmail,
                        etProfileLanguage, chkProfileCoach);

                if(successfulPost) {
                    Toast.makeText(getApplicationContext(),"Success!",
                            Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(getApplicationContext(),"Unable to update!",
                            Toast.LENGTH_SHORT);
                }

            }
        });
    }
}
