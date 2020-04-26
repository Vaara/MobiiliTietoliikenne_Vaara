package com.example.lab4_2_web_socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EchoClientInterface {
    TextView messageView;
    EchoClient echoClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageView = findViewById(R.id.messageView);
        messageView.setMovementMethod(new ScrollingMovementMethod());

        findViewById(R.id.sendMessageBtn).setOnClickListener(this);
        //findViewById(R.id.closeConnectionBtn).setOnClickListener(this);
        //findViewById(R.id.openConnectionBtn).setOnClickListener(this);
        openConnection();

    }

    @Override
    protected void onStop() {
        super.onStop();
        closeConnection();
    }

    @Override
    public void onClick(View view) {
       /*
        if(view.getId() == R.id.openConnectionBtn)
        {
            openConnection();

        }
        */
        if(view.getId() == R.id.sendMessageBtn)
        {
            sendMessage();
        }
        /*
        if(view.getId() == R.id.closeConnectionBtn)
        {
            closeConnection();
        }
         */

    }

    private void closeConnection(){
        if(echoClient != null && echoClient.isOpen())
        {
            echoClient.close();
        }
    }

    private void sendMessage(){
        if(echoClient != null && echoClient.isOpen())
        {
            EditText editor = findViewById(R.id.messageEditText);
            String text = editor.getText().toString();

            echoClient.send(text);
        }
    }

    private void openConnection()
    {
        try {
            echoClient = new EchoClient(new URI("wss://obscure-waters-98157.herokuapp.com"), this);
            //echoClient = new EchoClient(new URI("wss://echo.websocket.org"), this);
            echoClient.connect();

            //Log.d("L4SOCKET", "no error");
        }catch (Exception e){
            Log.d("L4SOCKET", "URI error");
            e.printStackTrace();
        }

    }

    @Override
    public void onMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageView.append(message + "\n");
            }
        });
    }

    @Override
    public void onStatusChange(final String newStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView statusView = findViewById(R.id.statusTextView);
                statusView.setText(newStatus);
            }
        });
    }
}
