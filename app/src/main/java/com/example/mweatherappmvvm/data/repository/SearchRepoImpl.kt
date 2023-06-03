package com.example.mweatherappmvvm.data.repository

import com.example.mweatherappmvvm.data.datasource.DataSource
import com.example.mweatherappmvvm.data.model.CityDetailsResponse
import com.example.mweatherappmvvm.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(private val networkDataSource: DataSource): SearchRepository {
    override suspend fun getCityDetails(query: String): List<CityDetailsResponse> {
       return networkDataSource.getCityDetailsByQuery(query)
    }
}