package com.example.lab4_1_football_areas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompetitionsActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private ListView listViewCompetitions;
    private static ArrayList<String> arrayCompetitionView;
    private static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitions);
        
        listViewCompetitions = findViewById(R.id.listViewCompetitions);

        arrayCompetitionView = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayCompetitionView);
        listViewCompetitions.setAdapter(arrayAdapter);


        Intent getId = getIntent();
        String competitionId = getId.getStringExtra("areaId");
        String competitionArea = getId.getStringExtra("areaName");
        setTitle("Competitions: " + competitionArea);

        Log.d("Aluekoodi",  competitionId);

        fetchCompetitions(competitionId);
    }

    public void refreshListView() {
        arrayAdapter.notifyDataSetChanged();
    }
    public void clearArrays() {
        arrayCompetitionView.clear();
    }

    private void fetchCompetitions(String id) {
        clearArrays();
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.football-data.org/v2/competitions?areas=" + id;

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String name;

                        try {

                            JSONArray jsonArray = response.getJSONArray("competitions");

                            if(jsonArray.length() == 0)
                            {
                                arrayCompetitionView.add("Ei tuloksia. Koita toista aluetta");
                            }
                            else
                            {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject competition = jsonArray.getJSONObject(i);

                                    name = competition.getString("name");

                                    arrayCompetitionView.add(name);
                                }
                            }
                            refreshListView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);

    }
}
