package com.example.mweatherappmvvm.data.api

import com.example.mweatherapplication.BuildConfig
import com.example.mweatherappmvvm.data.model.CityDetailsResponse
import com.example.mweatherappmvvm.data.model.WeatherDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(BuildConfig.WEATHER_DETAILS_URL)
    suspend fun getWeatherDetailsByCordinates(
        @Query("lat") lat: String,
        @Query("appid") appid: String = BuildConfig.APP_ID,
        @Query("lon") lon: String
    ): WeatherDetailsResponse

    @GET(BuildConfig.WEATHER_DETAILS_URL)
    suspend fun getWeatherDetailsByCityName(
        @Query("q") cityname: String,
        @Query("appid") appid: String = BuildConfig.APP_ID
    ): WeatherDetailsResponse

    @GET(BuildConfig.WEATHER_GEOCODE_URL)
    suspend fun getCityDetailsByQuery(
        @Query("q") query: String,
        @Query("appid") appid: String = BuildConfig.APP_ID
    ): List<CityDetailsResponse>

}