package com.softengi.mobcomp.softwareengi_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softengi.mobcomp.softwareengi_mobile.Controllers.PlanController;

public class CreatePlanActivity extends AppCompatActivity {

    private EditText etPlanCreateRequiredSteps, etPlanCreateTitle;
    private Button btnSubmitPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        etPlanCreateRequiredSteps = findViewById(R.id.etPlanCreateRequiredSteps);
        etPlanCreateTitle         = findViewById(R.id.etPlanCreateTitle);
        btnSubmitPlan             = findViewById(R.id.btnCreatePlan);

        btnSubmitPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlanController.postCreatePlans(
                        getApplicationContext(),
                        etPlanCreateRequiredSteps.getText().toString(),
                        etPlanCreateTitle.getText().toString()
                        );
            }
        });

    }
}
