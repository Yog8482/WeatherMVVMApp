package com.example.mweatherappmvvm.data.datasource

import com.example.mweatherappmvvm.data.api.WeatherApi
import com.example.mweatherappmvvm.data.model.CityDetailsResponse
import com.example.mweatherappmvvm.data.model.WeatherAPIResponse
import retrofit2.Retrofit
import javax.inject.Inject

interface DataSource {
    suspend fun getWeatherDetailsByCordinates(lat:String, longi:String): WeatherAPIResponse
    suspend fun getWeatherDetailsByCity(city:String): WeatherAPIResponse
    suspend fun getCityDetailsByQuery(query:String): List<CityDetailsResponse>
}

/**
 * [Retrofit] backed [DataSource]
 */
class NetworkDataSource @Inject constructor(private val networkApi: WeatherApi) :
    DataSource {
    override suspend fun getWeatherDetailsByCordinates(
        lat: String,
        longi: String
    ): WeatherAPIResponse {
        return networkApi.getWeatherDetailsByCordinates(lat = lat, lon = longi)
    }
    override suspend fun getWeatherDetailsByCity(city: String): WeatherAPIResponse {
        return networkApi.getWeatherDetailsByCityName(cityname = city)
    }

    override suspend fun getCityDetailsByQuery(query: String): List<CityDetailsResponse> {
        return networkApi.getCityDetailsByQuery(query = query)
    }

}