package com.softengi.mobcomp.softwareengi_mobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Controllers.PlanController;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SuccessListener;

public class PlanDetailActivity extends AppCompatActivity {

    private EditText etPlanDetailRequiredSteps, etPlanDetailTitle;
    private Button btnDetailUpdate, btnDetailDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        etPlanDetailRequiredSteps = findViewById(R.id.etPlanDetailRequiredSteps);
        etPlanDetailTitle = findViewById(R.id.etPlanDetailTitle);
        btnDetailUpdate = findViewById(R.id.btnDetailUpdate);
        btnDetailDelete = findViewById(R.id.btnDetailDelete);

        btnDetailUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlanController.postUpdateSteps(
                        getApplicationContext(),
                        etPlanDetailTitle,
                        etPlanDetailRequiredSteps,
                        new SuccessListener() {
                            @Override
                            public void successful() {

                                PlansFragment fragment = new PlansFragment();
                                setFragment(fragment);

                            }
                        }
                );
            }
        });

        btnDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlanController.postDelete(
                        getApplicationContext(),
                        "1",
                        new SuccessListener() {
                            @Override
                            public void successful() {

                                PlansFragment fragment = new PlansFragment();
                                setFragment(fragment);

                            }
                        }
                );
            }
        });

    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.athlete_frame, fragment);
        fragmentTransaction.commit();

    }
}
