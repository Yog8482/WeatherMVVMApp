package com.example.mweatherappmvvm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefs {
    private Context context = null;
    private static final String PREF_FILE_NAME = "WeatherPrefs";
    static final String KEY_CITY_NAME = "CityName";
    static final String KEY_TEMPERATURE_UNIT = "TemperatureUnit";

    private SharedPreferences sharedPreferences ;
    private  SharedPreferences.Editor editor ;

    public SharedPrefs(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
