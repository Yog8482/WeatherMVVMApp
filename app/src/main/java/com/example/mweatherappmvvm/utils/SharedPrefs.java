package com.example.mweatherappmvvm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;

import com.example.mweatherappmvvm.data.model.CityDetailsResponse;
import com.google.gson.Gson;

public class SharedPrefs {
    private static final String PREF_FILE_NAME = "WeatherPrefs";
    static final String KEY_IS_LOCATION_PERMISSION_GRANTED = "is_loc_permission_granted";
    static final String KEY_LAST_LOCATION = "lat_long";
    static final String KEY_LAST_SELECTED_CITY_DETAILS = "last_sel_city_details";

    private SharedPreferences sharedPreferences ;
    private  SharedPreferences.Editor editor ;

    public String getLastLocationKey(){
        return KEY_LAST_LOCATION;
    }
    public String getCityDetailsKey(){
        return KEY_LAST_SELECTED_CITY_DETAILS;
    }
    public SharedPrefs(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void registerChangeListner(SharedPreferences.OnSharedPreferenceChangeListener listner){
        sharedPreferences.registerOnSharedPreferenceChangeListener(listner);
    }
    public void unregisterChangeListner(SharedPreferences.OnSharedPreferenceChangeListener listner){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listner);
    }
    public void setLocationPermission(Boolean isGranted) {
        editor.putBoolean(KEY_IS_LOCATION_PERMISSION_GRANTED, isGranted);
        editor.apply();
    }
    public Boolean isLocationPermissionGranted(){
        return sharedPreferences.getBoolean(KEY_IS_LOCATION_PERMISSION_GRANTED, false);
    }

    public void saveCityDetails(Parcelable object) {
        // Convert Parcelable object to string representation
        String objectString = parcelableToString(object);
        editor.putString(KEY_LAST_SELECTED_CITY_DETAILS, objectString);
        editor.apply();
    }

    public Parcelable getCityDetails() {

        // Retrieve the string representation of the Parcelable object
        String objectString = sharedPreferences.getString(KEY_LAST_SELECTED_CITY_DETAILS, null);

        // Convert string to Parcelable object
        return stringToParcelable(objectString);
    }

    private String parcelableToString(Parcelable object) {
        return new Gson().toJson(object);
    }

    private Parcelable stringToParcelable(String objectString) {
        return new Gson().fromJson(objectString,CityDetailsResponse.class);
    }
    public void saveLastLocationCords(String lat_long) {
        editor.putString(KEY_LAST_LOCATION, lat_long);
        editor.apply();
    }

    public String getLastLocationCords() {
        return sharedPreferences.getString(KEY_LAST_LOCATION, "");
    }

    public void clearPreferences() {
        editor.clear();
        editor.apply();
    }

}
