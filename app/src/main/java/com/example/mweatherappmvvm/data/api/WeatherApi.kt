package com.example.mweatherappmvvm.data.api

import com.example.mweatherapplication.BuildConfig
import com.example.mweatherappmvvm.data.model.CityDetailsResponse
import com.example.mweatherappmvvm.data.model.WeatherAPIResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(BuildConfig.WEATHER_DETAILS_ENDPOINT)
    suspend fun getWeatherDetailsByCordinates(
        @Query("lat") lat: String,
        @Query("appid") appid: String = BuildConfig.APP_ID,
        @Query("lon") lon: String
    ): WeatherAPIResponse

    @GET(BuildConfig.WEATHER_DETAILS_ENDPOINT)
    suspend fun getWeatherDetailsByCityName(
        @Query("q") cityname: String,
        @Query("appid") appid: String = BuildConfig.APP_ID
    ): WeatherAPIResponse

    @GET(BuildConfig.WEATHER_GEOCODE_ENDPOINT)
    suspend fun getCityDetailsByQuery(
        @Query("q") query: String,
        @Query("appid") appid: String = BuildConfig.APP_ID
    ): List<CityDetailsResponse>

}