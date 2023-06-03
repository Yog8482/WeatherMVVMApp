package com.example.mweatherappmvvm.domain.usecase

import com.example.mweatherappmvvm.data.model.CityDetailsResponse
import com.example.mweatherappmvvm.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchCityUsecase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend fun execute(q:String): Flow<List<CityDetailsResponse>> {
        return  flow {
            emit(searchRepository.getCityDetails(q))
        }.flowOn(Dispatchers.IO)
    }
}