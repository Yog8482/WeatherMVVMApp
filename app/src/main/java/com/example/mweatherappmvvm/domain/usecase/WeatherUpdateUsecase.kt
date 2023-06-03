package com.example.mweatherappmvvm.domain.usecase

import android.util.Log
import com.example.mweatherappmvvm.common.UpdateBy
import com.example.mweatherappmvvm.data.model.WeatherDetails
import com.example.mweatherappmvvm.data.model.toWeatherDetailsModel
import com.example.mweatherappmvvm.domain.repository.WeatherUpdateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherUpdateUsecase @Inject constructor(private val weatherUpdateRepo: WeatherUpdateRepository) {

    suspend fun execute(updateBy: UpdateBy): Flow<WeatherDetails?> {
        return  flow {
            val response = weatherUpdateRepo.getWeatherUpdate(updateBy)
            Log.d("WeatherUpdateUsecase", "Resposne:$response")
            emit(response.toWeatherDetailsModel())
        }.flowOn(Dispatchers.IO)
    }
}