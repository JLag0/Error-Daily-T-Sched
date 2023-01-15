package com.ulan.timetable.activities;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ulan.timetable.R;

public class TeamError extends AppCompatActivity {
    private String teamName;
    private TeamMember[] teamMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        teamName = "App Development Team";
        teamMembers = new TeamMember[] {
                new TeamMember("Gonzalvo, Kenneth Jasper Q.", new String[] {"Team Leader"}),
                new TeamMember("Dela Cerna, Jenzy D.",new String[] {"System Analyst"}),
                new TeamMember("Lago, Jhon Ulysis M.",new String[] {"Programmer"}),
                new TeamMember("Bautista Kazuyuki",new String[] {"Graphic Designer"}),
                new TeamMember("Brosas, Alexandra Nichole M.",new String[] {"Document Controller"}),
                new TeamMember("Barilla, Lawrence Justine C.",new String[]{"Document Controller"})
        };

        TextView teamNameView = findViewById(R.id.team_name);
        teamNameView.setText(teamName);

        ListView teamMembersView = findViewById(R.id.team_member);
        TeamMemberAdapter adapter = new TeamMemberAdapter(this, teamMembers);
        teamMembersView.setAdapter(adapter);
    }
}

class TeamMember {
    private String name;
    private String[] roles;

    public TeamMember(String name, String[] roles) {
        this.name = name;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public String[] getRoles() {
        return roles;
    }
}

class TeamMemberAdapter extends ArrayAdapter<TeamMember> {
    public TeamMemberAdapter(Context context, TeamMember[] teamMembers) {
        super(context, 0, teamMembers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TeamMember teamMember = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_team, parent, false);
        }
        // Lookup view for data population
        TextView name = convertView.findViewById(R.id.team_name);
        TextView role = convertView.findViewById(R.id.role);
        // Populate the data into the template view using the data object
        name.setText(teamMember.getName());
        String roles = TextUtils.join(", ", teamMember.getRoles());
        role.setText(roles);
        // Return the completed view to render on screen
        return convertView;
    }
}
