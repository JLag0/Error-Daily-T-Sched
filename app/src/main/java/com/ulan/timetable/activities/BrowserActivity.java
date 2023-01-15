package com.ulan.timetable.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ulan.timetable.R;
import com.ulan.timetable.utils.DbHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


public class BrowserActivity extends AppCompatActivity {

    private DbHelper db;
    private ArrayList<String> links;
    private ArrayAdapter<String> adapter;
    private ListView listView1;
    private EditText et1;
    private Button save_btn;
    private ImageButton messenger;
    private ImageButton gmeet;
    private ImageButton google;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        // Initialize the DbHelper
        db = new DbHelper(this);

        // Load the saved links from the SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        String json = sharedPreferences.getString("links", null);
        // If there are no saved links, create a new ArrayList
        links = gson.fromJson(json, type);
        if (links == null) {
            links = new ArrayList<>();
        }
        // Initialize the ListView, EditText, and Buttons
        listView1 = findViewById(R.id.listView1);
        et1 = findViewById(R.id.et1);
        save_btn = findViewById(R.id.save_btn);
        ImageButton messengerButton = findViewById(R.id.messenger);
        messengerButton.setImageResource(R.drawable.ic_messenger_icon);
        messenger = findViewById(R.id.messenger);
        ImageButton gmeetButton = findViewById(R.id.gmeet);
        gmeetButton.setImageResource(R.drawable.google_meet_icon);
        gmeet = findViewById(R.id.gmeet);
        ImageButton googleButton = findViewById(R.id.google);
        google = findViewById(R.id.google);
        googleButton.setImageResource(R.drawable.chrome_icon);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, links);
        listView1.setAdapter(adapter);






        save_btn.setOnClickListener(this::onClick);

        messenger.setOnClickListener(v -> openLink("https://m.me"));
        gmeet.setOnClickListener(v -> openLink("https://meet.google.com"));
        google.setOnClickListener(v -> openLink("https://www.google.com"));

        findViewById(R.id.click_btn).setOnClickListener(v -> {
            String URL = et1.getText().toString();
            openLink(URL);
        });

        listView1.setOnItemClickListener((parent, view, position, id) -> openLink(adapter.getItem(position)));
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // Show a prompt asking the user to delete the link or not
                AlertDialog.Builder builder = new AlertDialog.Builder(BrowserActivity.this);
                builder.setTitle("Delete link");
                builder.setMessage("Are you sure you want to delete this link?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteLink(position);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    private void openLink(String link) {
        if (!link.startsWith("http")) {
            link = "http://" + link;
        }
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void onClick(View view) {
        String link = et1.getText().toString();
        if(link.isEmpty() || !link.startsWith("https://")){
            Toast.makeText(this,"Enter a valid url",Toast.LENGTH_SHORT).show();
        }else if(links.contains(link)){
            Toast.makeText(this,"Link already exists",Toast.LENGTH_SHORT).show();
        }else {
            links.add(link);
            adapter.notifyDataSetChanged();
            String json = gson.toJson(links);
            sharedPreferences.edit().putString("links", json).apply();
            Toast.makeText(this,"Link saved",Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteLink(int position) {
        // Delete the link from the database
        db.deleteLink(links.get(position));
        // Delete the link from the ArrayList
        links.remove(position);
        String json = gson.toJson(links);
        sharedPreferences.edit().putString("links", json).apply();
        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }
}
