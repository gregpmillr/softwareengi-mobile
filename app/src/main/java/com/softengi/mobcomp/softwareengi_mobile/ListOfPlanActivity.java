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
import android.widget.Toast;

import com.softengi.mobcomp.softwareengi_mobile.Controllers.PlanController;
import com.softengi.mobcomp.softwareengi_mobile.Utils.ListOfPlanParser;
import com.softengi.mobcomp.softwareengi_mobile.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfPlanActivity extends AppCompatActivity {

    ListView lvPlans;
    Button btnCreatePlan;
    ArrayList<String> mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_plan);

        lvPlans       = findViewById(R.id.lvPlans);
        btnCreatePlan = findViewById(R.id.btnCreatePlan);
        mData         = new ArrayList<>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mData);

        lvPlans.setAdapter(arrayAdapter);

        PlanController.getListOfPlans(
                getApplication(),
                SharedPrefManager.getInstance(getApplicationContext()).getUsername(),
                new ListOfPlanParser() {
                    @Override
                    public void onSuccessResponse(JSONArray data) {

                        try {

                            mData.clear();

                            for(int i = 0; i < data.length(); i++) {
                                mData.add(data.getJSONObject(i).getString("title"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(),mData.toString(),Toast.LENGTH_LONG).show();

                        arrayAdapter.notifyDataSetChanged();
                    }
                });

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
