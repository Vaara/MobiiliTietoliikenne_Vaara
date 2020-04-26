package com.example.lab1_2_gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    private TextView textGPS;
    private TextView textLocation;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 919;

    Geocoder geocoder;
    List<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textGPS = findViewById(R.id.textGPS);
        textLocation = findViewById(R.id.textLocation);

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
                            fetchLocation(latitude, longitude);
                        }

                        else{
                            // jos location on null
                        }

                    }
                });
    }

    public void fetchLocation(Double latitude, Double longitude)
    {
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String city = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();

            textGPS.setText("" + String.format("%.3f",latitude) + "  /  " + String.format("%.3f",longitude) + "\r\n");
            textLocation.setText("" + city+ "  /  " + country + "\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
 */


    }
}



