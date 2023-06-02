package com.example.mweatherappmvvm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefs {
    private static final String PREF_FILE_NAME = "WeatherPrefs";
    static final String KEY_CITY_NAME = "CityName";
    static final String KEY_TEMPERATURE_UNIT = "TemperatureUnit";
    static final String KEY_IS_LOCATION_PERMISSION_GRANTED = "is_loc_permission_granted";
    static final String KEY_LAST_LOCATION = "lat_long";

    private SharedPreferences sharedPreferences ;
    private  SharedPreferences.Editor editor ;

    public SharedPrefs(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLocationPermission(Boolean isGranted) {
        editor.putBoolean(KEY_IS_LOCATION_PERMISSION_GRANTED, isGranted);
        editor.apply();
    }
    public Boolean isLocationPermissionGranted(){
        return sharedPreferences.getBoolean(KEY_IS_LOCATION_PERMISSION_GRANTED, false);
    }

    public void saveLastLocation(String lat_long) {
        editor.putString(KEY_LAST_LOCATION, lat_long);
        editor.apply();
    }

    public String getLastLocation() {
        return sharedPreferences.getString(KEY_LAST_LOCATION, "");
    }

    public void saveCityName(String cityName) {
        editor.putString(KEY_CITY_NAME, cityName);
        editor.apply();
    }

    public String getCityName() {
        return sharedPreferences.getString(KEY_CITY_NAME, "");
    }

    public void saveTemperatureUnit(String temperatureUnit) {
        editor.putString(KEY_TEMPERATURE_UNIT, temperatureUnit);
        editor.apply();
    }

    public String getTemperatureUnit() {
        return sharedPreferences.getString(KEY_TEMPERATURE_UNIT, "Celsius");
    }

    public void clearPreferences() {
        editor.clear();
        editor.apply();
    }

}
