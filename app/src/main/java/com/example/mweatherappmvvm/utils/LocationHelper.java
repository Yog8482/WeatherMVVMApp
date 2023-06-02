package com.example.mweatherappmvvm.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;

import com.example.mweatherappmvvm.ui.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

public class LocationHelper {

    private static String TAG= "LocationHelper";
    public static boolean isGPSEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
        private Context context;
        private FusedLocationProviderClient fusedLocationClient;
        private LocationCallback locationCallback;

        public LocationHelper(Context context, SharedPrefs sharedPrefs) {

            this.context = context;
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
                        Log.i(TAG, "Location:"+location.getLatitude()+","+location.getLongitude());
                        sharedPrefs.saveLastLocation(location.getLatitude()+","+location.getLongitude());
                    }
                }
            };
        }

        @SuppressLint("MissingPermission")
        public void startLocationUpdates() {
            LocationRequest locationRequest = LocationRequest.create()
                    .setInterval(10000) // Update interval in milliseconds
                    .setFastestInterval(5000) // Fastest update interval in milliseconds
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }

        public void stopLocationUpdates() {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

