package com.example.mweatherappmvvm.domain.repository

import com.example.mweatherappmvvm.common.UpdateBy
import com.example.mweatherappmvvm.data.model.WeatherDetailsResponse

interface WeatherUpdateRepository {
   suspend fun getWeatherUpdate(updateBy: UpdateBy) : WeatherDetailsResponse
}