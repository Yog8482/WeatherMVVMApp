package com.example.mweatherappmvvm.ui

import android.Manifest
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.mweatherapplication.R
import com.example.mweatherapplication.databinding.FragmentWeatherBinding
import com.example.mweatherappmvvm.data.model.CityDetailsResponse
import com.example.mweatherappmvvm.utils.LocationHelper
import com.example.mweatherappmvvm.utils.LocationHelper.isGPSEnabled
import com.example.mweatherappmvvm.utils.PermissionHelper
import com.example.mweatherappmvvm.utils.SharedPrefs
import com.example.mweatherappmvvm.utils.showGenericAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class WeatherFragment : Fragment(), PermissionHelper.PermissionListener,
    SharedPreferences.OnSharedPreferenceChangeListener {
    private val viewModel by viewModels<WeatherViewModel>()

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var locationHelper: LocationHelper

    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->

        if (isGranted) {
            // Permission granted
            onPermissionsGranted()
        } else {
            // Permission denied
            onPermissionsDenied(null)
        }
    }


    override fun onStart() {
        super.onStart()
        viewModel.resetWeatherState() // reset the state when the fragment is restarted
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionRequestLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        observe()
        getWeatherUpdate()

        // Register the listener
        sharedPrefs.registerChangeListner(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.bind(
            inflater.inflate(R.layout.fragment_weather, container, false)
        )

        binding.floatingActionButton.setOnClickListener {
            navigateToSearchCity(it)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (sharedPrefs.isLocationPermissionGranted && this::locationHelper.isInitialized) {
            locationHelper.startLocationUpdates()
        }

        getWeatherUpdate()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPrefs.unregisterChangeListner(this)

        if (sharedPrefs.isLocationPermissionGranted && this::locationHelper.isInitialized) {
            locationHelper.stopLocationUpdates();
        }
    }

    /**
     * This function will check weather update for last selected city first and if city is not selected
     * then it will check last location co-ordinates (latitude, longitude)
     */
    private fun getWeatherUpdate() {
        val lastSelectedCity = sharedPrefs.cityDetails
        if (lastSelectedCity != null) {
            val cityDetails = lastSelectedCity as CityDetailsResponse
            cityDetails.name?.let { viewModel.getWeatherUpdateByCityName(it) }
        } else if (sharedPrefs.isLocationPermissionGranted) {
            if (isGPSEnabled(requireContext())) {
                val latlong = sharedPrefs.lastLocationCords
                if (latlong.isNotEmpty()) {
                    val cords = latlong.split(",")
                    viewModel.getWeatherUpdateByCordinates(cords[0], cords[1]) //latitude, longitude
                }
            }
        }
    }

    private fun observe() {
        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherUIState
                    .onEach { state -> handleStateChange(state) }
                    .collect()
            }
        }
    }

    private fun handleStateChange(state: WeatherUpdateState) {
        when (state) {
            is WeatherUpdateState.Idle -> Unit
            is WeatherUpdateState.Success -> {
                binding.weatherDetails = state.data
                handleLoading(false)
            }

            is WeatherUpdateState.Loading -> {
                handleLoading(true)
            }

            is WeatherUpdateState.Fail -> handleFail(state.failMsg)
        }
    }

    private fun handleFail(failMsg: String?) {
        context?.showGenericAlertDialog(
            failMsg ?: resources.getString(R.string.unknown_reason_try_again)
        )
        handleLoading(isLoading = false)
    }

    private fun handleLoading(isLoading: Boolean = false) {
        binding.progressBar.isVisible = isLoading
    }

    private fun navigateToSearchCity(view: View) {
        view.findNavController()
            .navigate(WeatherFragmentDirections.actionWeatherFragmentToSelectCityFragment())
    }

    override fun onPermissionsGranted() {
        // Permissions granted, perform your weather app logic here
        //Check Location is ON and get user location
        sharedPrefs.setLocationPermission(true)

        if (isGPSEnabled(requireContext())) {
//            Toast.makeText(requireContext(), "GPs enabled", Toast.LENGTH_SHORT).show()
            //get user location
            locationHelper = LocationHelper(requireContext(), sharedPrefs)
            locationHelper.startLocationUpdates()
        } else {
            Toast.makeText(
                requireContext(),
                "GPS is not enabled. Please enable it in your device's settings.",
                Toast.LENGTH_LONG
            ).show()

            if (!isSelectedCityIsPresent())
                navigateToSearchCity(binding.root)
            else getWeatherUpdate()

        }
    }


    override fun onPermissionsDenied(deniedPermissions: MutableList<String>?) {
        // Permissions denied, handle the situation (e.g., show a message, disable functionality)
//        Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        sharedPrefs.setLocationPermission(false)

        //If permission denied, it will check last selected city is present or not if not then fallback to select city
        if (!isSelectedCityIsPresent())
            navigateToSearchCity(binding.root)
        else
            getWeatherUpdate()

    }

    private fun isSelectedCityIsPresent() = sharedPrefs.cityDetails != null

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            sharedPrefs.cityDetailsKey -> {
                getWeatherUpdate()
            }

            sharedPrefs.lastLocationKey -> {
                getWeatherUpdate()
            }
        }
    }


}