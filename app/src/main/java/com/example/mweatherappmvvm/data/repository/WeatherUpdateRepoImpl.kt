package com.example.mweatherappmvvm.data.repository

import com.example.mweatherappmvvm.common.UpdateBy
import com.example.mweatherappmvvm.data.datasource.DataSource
import com.example.mweatherappmvvm.data.model.WeatherAPIResponse
import com.example.mweatherappmvvm.domain.repository.WeatherUpdateRepository
import javax.inject.Inject

class WeatherUpdateRepoImpl @Inject constructor(private val networkDataSource: DataSource) :
    WeatherUpdateRepository {
    override suspend fun getWeatherUpdate(updateBy: UpdateBy): WeatherAPIResponse {
        return when (updateBy) {
            is UpdateBy.ByCityName -> {
                networkDataSource.getWeatherDetailsByCity(updateBy.name)
            }

            is UpdateBy.ByCordinates -> {
                networkDataSource.getWeatherDetailsByCordinates(
                    lat = updateBy.latitude,
                    longi = updateBy.longitude
                )
            }
        }
    }
}