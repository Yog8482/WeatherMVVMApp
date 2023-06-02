package com.example.mweatherappmvvm.domain.repository

import com.example.mweatherappmvvm.common.AppConstants

interface SearchRepository {
    fun searchBy(type: AppConstants.SEARCH_BY = AppConstants.SEARCH_BY.CITY_NAME)
}