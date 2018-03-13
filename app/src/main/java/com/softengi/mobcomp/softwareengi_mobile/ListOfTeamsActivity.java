package com.softengi.mobcomp.softwareengi_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOfTeamsActivity extends AppCompatActivity {

    ListView teamListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_teams);

        teamListView = findViewById(R.id.teamListView);
        String[] teamNames = new String[]{"Team 1", "Team 2"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.layout_team_list_item, R.id.teamName, teamNames);
        teamListView.setAdapter(adapter);
    }
}
