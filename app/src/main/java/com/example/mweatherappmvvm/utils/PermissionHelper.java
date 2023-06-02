package com.example.mweatherappmvvm.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.List;


/**
 * [PermissionHelper] is to use system services and get the permission from user before using the service
 */

public class PermissionHelper {
    private static final int PERMISSION_REQUEST_CODE = 100;

    private Activity activity;
    private PermissionListener permissionListener;

    public PermissionHelper(Activity activity, PermissionListener listener) {
        this.activity = activity;
        this.permissionListener = listener;
    }

    public void requestPermissions() {
        List<String> permissionsList = new ArrayList<>();

        // Check and request location permission
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            permissionsList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionsList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        // Request permissions if the list is not empty
        if (!permissionsList.isEmpty()) {
            String[] permissions = permissionsList.toArray(new String[0]);
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
        } else {
            // All permissions already granted
            permissionListener.onPermissionsGranted();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                List<String> deniedPermissions = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add(permissions[i]);
                    }
                }

                if (deniedPermissions.isEmpty()) {
                    // All permissions granted
                    permissionListener.onPermissionsGranted();
                } else {
                    // Some permissions denied
                    permissionListener.onPermissionsDenied(deniedPermissions);
                }
            }
        }
    }

    public interface PermissionListener {
        void onPermissionsGranted();

        void onPermissionsDenied(List<String> deniedPermissions);
    }
}