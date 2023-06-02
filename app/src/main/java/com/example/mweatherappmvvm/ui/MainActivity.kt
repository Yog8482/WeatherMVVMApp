package com.example.mweatherappmvvm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mweatherapplication.R
import com.example.mweatherappmvvm.utils.LocationHelper
import com.example.mweatherappmvvm.utils.LocationHelper.isGPSEnabled
import com.example.mweatherappmvvm.utils.PermissionHelper
import com.example.mweatherappmvvm.utils.SharedPrefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PermissionHelper.PermissionListener {
    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private lateinit var locationHelper: LocationHelper

    private lateinit var permissionHelper: PermissionHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an instance of PermissionHelper
        permissionHelper = PermissionHelper(this, this)
        // Request the permissions
        permissionHelper.requestPermissions()

    }

    override fun onResume() {
        super.onResume()
        if (sharedPrefs.isLocationPermissionGranted && this::locationHelper.isInitialized) {
            locationHelper.startLocationUpdates();
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (sharedPrefs.isLocationPermissionGranted && this::locationHelper.isInitialized) {
            locationHelper.stopLocationUpdates();
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Call the PermissionHelper's onRequestPermissionsResult
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    override fun onPermissionsGranted() {
        // Permissions granted, perform your weather app logic here
        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        //Check Location is ON and get user location
        sharedPrefs.setLocationPermission(true)

        if(isGPSEnabled(this)) {
            Toast.makeText(this, "GPs enabled", Toast.LENGTH_SHORT).show()
            //get user location
            locationHelper =  LocationHelper(this,sharedPrefs);
        } else {
            Toast.makeText(this, "GPS is not enabled. Please enable it in your device's settings.", Toast.LENGTH_LONG).show();
        }
    }

    override fun onPermissionsDenied(deniedPermissions: MutableList<String>?) {
        // Permissions denied, handle the situation (e.g., show a message, disable functionality)
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        sharedPrefs.setLocationPermission(false)

        gotoSelectCity()
    }

    private fun gotoSelectCity(){}

}