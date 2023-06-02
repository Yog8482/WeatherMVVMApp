package com.example.mweatherappmvvm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mweatherapplication.R
import com.example.mweatherappmvvm.utils.PermissionHelper

class MainActivity : AppCompatActivity(), PermissionHelper.PermissionListener {

    private lateinit var permissionHelper: PermissionHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Create an instance of PermissionHelper
        permissionHelper = PermissionHelper(this, this)
        // Request the permissions
        permissionHelper.requestPermissions()
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
    }

    override fun onPermissionsDenied(deniedPermissions: MutableList<String>?) {
        // Permissions denied, handle the situation (e.g., show a message, disable functionality)
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()

    }

}