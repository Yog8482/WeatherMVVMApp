package com.example.mweatherappmvvm.domain.repository

import com.example.mweatherappmvvm.data.model.CityDetailsResponse

interface SearchRepository {
   suspend fun getCityDetails(query: String): List<CityDetailsResponse>
}