package com.example.mweatherappmvvm.utils;

import static com.example.mweatherappmvvm.common.AppConstants.LOCATION_UPDATE_INTERVAL;
import static com.example.mweatherappmvvm.common.AppConstants.LOCATION_UPDATE_INTERVAL_FASTEST;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * [{@link LocationHelper}] This class is usefule to track user location in background
 */
public class LocationHelper {

    private static String TAG = "LocationHelper";

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    public static boolean isGPSEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public LocationHelper(Context context, SharedPrefs sharedPrefs) {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Process the received location
                    // You can send it to a server or perform any other required actions
                    Log.i(TAG, "Location:" + location.getLatitude() + "," + location.getLongitude());
                    sharedPrefs.saveLastLocation(location.getLatitude() + "," + location.getLongitude());
                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(LOCATION_UPDATE_INTERVAL) // Update interval in milliseconds
                .setFastestInterval(LOCATION_UPDATE_INTERVAL_FASTEST) // Fastest update interval in milliseconds
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}

