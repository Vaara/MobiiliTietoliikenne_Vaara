package com.example.lab4_1_football_areas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private ListView listView;
    private static ArrayList<String> arrayListView;
    private static ArrayList<String> arrayId;
    private static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Areas");

        listView = findViewById(R.id.listViewAreas);

        arrayListView = new ArrayList<>();
        arrayId = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayListView);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), CompetitionsActivity.class);
                intent.putExtra("areaId", arrayId.get(position));
                intent.putExtra("areaName", arrayListView.get(position));
                startActivity(intent);
            }
        });

        fetchAreas();
    }

    public void refreshListView() {
        arrayAdapter.notifyDataSetChanged();
    }

    public void clearArrays() {
        arrayListView.clear();
        arrayId.clear();
    }

    private void fetchAreas() {
        clearArrays();
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.football-data.org/v2/areas";

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String areaName;
                        String areaId;

                        try {

                            JSONArray jsonArray = response.getJSONArray("areas");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject areaObject = jsonArray.getJSONObject(i);

                                areaName = areaObject.getString("name");
                                areaId = areaObject.getString("id");

                                arrayListView.add(areaName);
                                arrayId.add(areaId);
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