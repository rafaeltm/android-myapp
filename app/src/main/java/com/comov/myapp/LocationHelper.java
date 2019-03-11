package com.comov.myapp;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.support.v4.content.ContextCompat.getSystemService;


public class LocationHelper {
    public static Location location = null;

    public static FusedLocationProviderClient getLocationFusedInstance(Activity activity) {
        return LocationServices.getFusedLocationProviderClient(activity);
    }

    /*
        Location request con precision
     */
    public static LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return locationRequest;
    }


    public static LocationAddressModel getAddress(double latitude, double longitude, Context contex) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(contex, Locale.getDefault());
        LocationAddressModel addressModel = null;
        try {

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            addressModel = new LocationAddressModel(addresses.get(0).getAddressLine(0),
                    addresses.get(0).getLocality(),
                    addresses.get(0).getAdminArea(),
                    addresses.get(0).getCountryName(),
                    addresses.get(0).getPostalCode(),
                    addresses.get(0).getFeatureName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressModel;
    }
}
