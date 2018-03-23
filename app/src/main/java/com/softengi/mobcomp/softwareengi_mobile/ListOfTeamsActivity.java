package com.softengi.mobcomp.softwareengi_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOfTeamsActivity extends AppCompatActivity {

    private ListView teamListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_teams);

        teamListView = findViewById(R.id.teamListView);
        String[] teamNames = new String[]{"Team 1\n324324", "Team 2\n98437532"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.layout_team_list_item, R.id.teamName, teamNames);
        teamListView.setAdapter(adapter);

        teamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // onItemClick method is called everytime a user clicks an item on the list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String teamId = parent.getItemAtPosition(position).toString().split("\n")[1];
                openTeamActivity(teamId);
            }
        });
    }

    /**
     * Opens the team activity for the specified team.
     * @param teamId ID of team.
     */
    private void openTeamActivity(String teamId){
        Intent intent = new Intent(this, TeamViewActivity.class);
        intent.putExtra("teamId", teamId);
        startActivity(intent);
    }

    /**
     * Create a new team.
     * @param view
     */
    public void createTeam(View view) {
        Intent intent = new Intent(this, TeamCreateActivity.class);
        startActivity(intent);
    }
}
