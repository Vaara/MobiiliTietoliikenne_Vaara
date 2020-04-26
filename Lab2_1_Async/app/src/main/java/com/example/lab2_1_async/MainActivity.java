package com.example.lab2_1_async;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Utilities.ReporterInterface {
    EditText addressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressBar = findViewById(R.id.addressBar);
    }

    public void goButton(View v){
        // https
        String url = ""+addressBar.getText();

        Utilities task = new Utilities();
        task.setCallbackInterface(this);
        task.execute(url);
    }

    @Override
    public void networkFetchDone(String data) {
        String htmlText = data;
        TextView textView = findViewById(R.id.httpTextView);
        textView.setText(htmlText);
    }
}
