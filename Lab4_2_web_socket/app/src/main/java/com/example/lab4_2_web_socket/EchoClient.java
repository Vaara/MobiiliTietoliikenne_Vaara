package com.example.lab4_2_web_socket;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

interface EchoClientInterface {
    void onMessage(String message);
    void onStatusChange(String newStatus);
}

public class EchoClient extends WebSocketClient {

    EchoClientInterface observer;

    public EchoClient(URI serverUri, EchoClientInterface observer) {
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d("L4SOCKET", "onOpen called");

        observer.onStatusChange("Connection open");
    }

    @Override
    public void onMessage(String message) {
        Log.d("L4SOCKET", "onMessage called");

        observer.onMessage(message);
        observer.onStatusChange("Received Message");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d("L4SOCKET", "onClose called");
        observer.onStatusChange("Socket closed");
    }

    @Override
    public void onError(Exception ex) {
        Log.d("L4SOCKET", "onError called");
        observer.onStatusChange("Error in socket:" + ex.toString());
    }
}