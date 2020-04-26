package com.example.lab2_3_add_stocks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements Utilities.ReporterInterface {

    private EditText editTextName, editTextId;
    private ListView listView;
    static ArrayList<String> arrayListView;
    static ArrayList<String> prices;
    static ArrayAdapter<String> arrayAdapter;
    private Utilities task;
    private Utilities custom;
    private String newName;
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextStockName);
        editTextId = findViewById(R.id.editTextStockId);
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

        task = new Utilities();
        task.setCallbackInterface(this);
        task.execute(url1, url2, url3, url4);
    }

    public void addItems()
    {
        arrayAdapter.notifyDataSetChanged();
    }

    public void addItemsCustom(View v){

        try {
            newName = editTextName.getText().toString();
            newId = editTextId.getText().toString();
            arrayListView.add(newName);

            String urlCustom = getString(R.string.baseURL) + newId + getString(R.string.jsonData);

            custom = new Utilities();
            custom.setCallbackInterface(this);
            custom.execute(urlCustom);

        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void networkFetchDone(String data) {
        String htmlText = data;
        String getPrice = "";

        int start = 0;
        int i;

        if(arrayListView.size() <= 4)
        {
            i = 0;
        }
        else
        {
            i = arrayListView.size()-1;
        }

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
