package com.example.lab2_2_stockmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements Utilities.ReporterInterface {

    private ListView listView;
    static ArrayList<String> arrayListView;
    static ArrayList<String> prices;
    static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewStocks);

        arrayListView = new ArrayList<>(Arrays.asList("Apple","Alphabet (Google)","Facebook","Nokia"));
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayListView );
        listView.setAdapter(arrayAdapter);

        String url1 = getString(R.string.baseURL) + "AAPL" + getString(R.string.jsonData);
        String url2 = getString(R.string.baseURL) + "GOOGL" + getString(R.string.jsonData);
        String url3 = getString(R.string.baseURL) + "FB" + getString(R.string.jsonData);
        String url4 = getString(R.string.baseURL) + "NOK" + getString(R.string.jsonData);

        Utilities task = new Utilities();
        task.setCallbackInterface(this);
        task.execute(url1, url2, url3, url4);
    }

    public void addItems()
    {
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void networkFetchDone(String data) {
        String htmlText = data;
        String getPrice = "";

        int start = 0;
        int i = 0;

        while (true) {
            int match = htmlText.indexOf("price", start);
            if (match != -1) {
                int end = htmlText.indexOf("}", match);
                getPrice = htmlText.substring(match+7, end-5);
                arrayListView.set(i, arrayListView.get(i) + ": " + getPrice + " USD");
                i++;
            }
            if (match == -1) break;
            start = match + 2;  // ++
        }
        addItems();
    }
}
