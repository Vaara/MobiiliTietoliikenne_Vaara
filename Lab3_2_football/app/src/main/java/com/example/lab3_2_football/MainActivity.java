package com.example.lab3_2_football;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;

    private ListView listView;
    static ArrayList<String> arrayListView;
    static ArrayList<Integer> arrayId;
    static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewLeagues);

        arrayListView = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayListView );
        listView.setAdapter(arrayAdapter);

        fetchFootball();
    }

public void refreshListView()
    {
        arrayAdapter.notifyDataSetChanged();
    }

private void fetchFootball(){

    requestQueue = Volley.newRequestQueue(this);
    String url = "https://api.football-data.org/v2/competitions?areas=2072";

    JsonObjectRequest request = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                                    String name;


                                    try {

                                        JSONArray jsonArray = response.getJSONArray("competitions");

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject competitionName = jsonArray.getJSONObject(i);

                                            name = competitionName.getString("name");
                                            arrayListView.add(name);
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