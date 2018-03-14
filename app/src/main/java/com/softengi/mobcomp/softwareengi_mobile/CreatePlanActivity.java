package com.softengi.mobcomp.softwareengi_mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.softengi.mobcomp.softwareengi_mobile.Controllers.PlanController;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreatePlanActivity extends AppCompatActivity {

    private TextView tvCompletedByDate;
    private EditText etPlanCreateRequiredSteps, etPlanCreateTitle;
    private DatePickerDialog.OnDateSetListener date;
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
                PlanController.postPlans(
                        getApplicationContext(),
                        etPlanCreateRequiredSteps.getText().toString(),
                        etPlanCreateTitle.getText().toString()
                        );
            }
        });

    }
}
