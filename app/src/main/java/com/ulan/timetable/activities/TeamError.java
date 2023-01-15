package com.ulan.timetable.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ulan.timetable.R;


public class TeamError extends AppCompatActivity {
    private String teamName;
    private String[] teamMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        teamName = "App Development Team";
        teamMembers = new String[] {"GONZALVO, KENNETH JASPER Q." ,"BARILLA, LAWRENCE JUSTINE C.", "BAUTISTA KAZUYUKI", "BROSAS, ALEXANDRA NICHOLE M." , "DELA CERNA, JENZY D.", "BELEN, RASSEL M.","LAGO, JHON ULYSIS M."};

        TextView teamNameView = findViewById(R.id.team_name);
        teamNameView.setText(teamName);

        ListView teamMembersView = findViewById(R.id.team_member);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamMembers);
        teamMembersView.setAdapter(adapter);
    }
}













