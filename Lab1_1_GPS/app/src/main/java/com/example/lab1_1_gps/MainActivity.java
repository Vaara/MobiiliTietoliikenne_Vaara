package com.example.lab1_1_gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private TextView textGPS;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 919;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textGPS = findViewById(R.id.textGPS);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) { // jos location permission ei ole myönnetty

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION))
            {
                // jos tarvitaan selitys, en alkanut tähän nyt sen kummempia tekemään.
            }
            else
            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
            }
            updateGPS(null);
        }

        else
        {
            updateGPS(null);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateGPS(null);
    }

    public void updateGPS(View v){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            Double latitude = location.getLatitude();
                            Double longitude = location.getLongitude();

                            textGPS.setText(latitude + "  /  " + longitude);
                        }

                        else{
                            // jos location on null
                        }

                    }
                });
    }
}



