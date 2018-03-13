package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListOfPlanActivity extends AppCompatActivity {

    ListView lvPlans;
    Button btnCreatePlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_plan);

        lvPlans       = findViewById(R.id.lvPlans);
        btnCreatePlan = findViewById(R.id.btnCreatePlan);
        String[] data = new String[] {};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);

        lvPlans.setAdapter(arrayAdapter);

        lvPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String planId = ((TextView) view).getText().toString();
                Intent intent = new Intent(getApplicationContext(), PlanDetailActivity.class);
                intent.putExtra("planId", planId);
                startActivity(intent);
            }
        });

        btnCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreatePlanActivity.class);
                startActivity(intent);
            }
        });

    }
}
