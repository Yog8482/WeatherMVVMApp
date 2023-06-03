package com.example.mweatherappmvvm.domain.repository

import com.example.mweatherappmvvm.common.UpdateBy
import com.example.mweatherappmvvm.data.model.WeatherAPIResponse

interface WeatherUpdateRepository {
   suspend fun getWeatherUpdate(updateBy: UpdateBy) : WeatherAPIResponse
}