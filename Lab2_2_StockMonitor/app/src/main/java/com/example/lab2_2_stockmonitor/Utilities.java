package com.example.lab2_2_stockmonitor;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utilities extends AsyncTask<String, Integer, String> {
    String result = "";

    public interface ReporterInterface{
        void networkFetchDone(String data);
    }

    ReporterInterface callbackInterface;

    public void setCallbackInterface(ReporterInterface callbackInterface){
        this.callbackInterface = callbackInterface;
    }

    public static String fromStream(InputStream in) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }



    @Override
    protected String doInBackground(String... strings) {
        int count = strings.length;
        try{

            for(int i = 0; i<count; i++){
                URL url = new URL(strings[i]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(connection.getInputStream());
                result = result + fromStream(in);
            }

            //publishProgress(new Integer(i) * 10);

        } catch (Exception e)
        {e.printStackTrace();}

        return result;
    }
    @Override
    protected void onPostExecute(String data){
        if (callbackInterface != null){
            callbackInterface.networkFetchDone(data);
        }
    }
}
