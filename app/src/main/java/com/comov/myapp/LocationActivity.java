package com.comov.myapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationActivity extends AppCompatActivity {

    public final static int MY_LOCATION_PERMISSION = 0;
    public final static int MY_LOCATION_PERMISSION_FINE = 1;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private boolean requestingLocationUpdates = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        fusedLocationClient = LocationHelper.getLocationFusedInstance(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                TextView text = findViewById(R.id.textLocation);
                if (locationResult == null) {
                    text.setText("NULL LOCATION");
                }
                for (Location location : locationResult.getLocations()) {
                    Log.i("DEBUG", location.toString());
                    // Update the Location
                    LocationHelper.location = location;
                    LocationAddressModel address = LocationHelper.getAddress(location.getLatitude(), location.getLongitude(), getApplicationContext());
                    text.setText(address.toString());
                }
            }
        };

        Switch enabler = findViewById(R.id.enableGPS);
        enabler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                requestingLocationUpdates = isChecked;
                Button btnLocation = findViewById(R.id.button8);
                if (requestingLocationUpdates) {
                    Log.i("DEBUG", "Location updates");
                    btnLocation.setEnabled(false);
                    checkPermissionsLocationGPS();
                } else {
                    Log.i("DEBUG", "Stop location updates");
                    btnLocation.setEnabled(true);
                    stopLocationUpdates();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkPermissionsLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, MY_LOCATION_PERMISSION);
            }
        } else {
            getLastLocation(fusedLocationClient, this);
        }
    }

    private void checkPermissionsLocationGPS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, MY_LOCATION_PERMISSION_FINE);
            }
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_LOCATION_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation(fusedLocationClient, this);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            case MY_LOCATION_PERMISSION_FINE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    public void openMap(View v) {
        Intent intent = new Intent(LocationActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(LocationHelper.createLocationRequest(), locationCallback, null);
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public void getLocation(View v) {
        checkPermissionsLocation();
    }

    /*
        Last location "no funciona" si estamos usando la fine location en este proyecto porque estamos solapando los listener.
     */
    @SuppressLint("MissingPermission")
    private void getLastLocation(FusedLocationProviderClient fusedLocationProviderClient, final Activity activity) {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                TextView text = findViewById(R.id.textLocation);
                if (location != null) {
                    LocationHelper.location = location;
                    Log.i("DEBUG", location.toString());
                    LocationAddressModel address = LocationHelper.getAddress(location.getLatitude(), location.getLongitude(), getApplicationContext());
                    text.setText(address.toString());
                } else {
                    text.setText("NULL LOCATION");
                }
            }
        });

        fusedLocationProviderClient.getLastLocation().addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("DEBUG", "Something went wrong");
            }
        });
    }

}
