package com.example.mweatherappmvvm.di


import com.example.mweatherappmvvm.data.datasource.DataSource
import com.example.mweatherappmvvm.data.datasource.NetworkDataSource
import com.example.mweatherappmvvm.data.repository.SearchRepoImpl
import com.example.mweatherappmvvm.data.repository.WeatherUpdateRepoImpl
import com.example.mweatherappmvvm.domain.repository.SearchRepository
import com.example.mweatherappmvvm.domain.repository.WeatherUpdateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindNetworkDatasource(networkDataSource: NetworkDataSource): DataSource

    @Binds
    fun bindsSearchRepository(searchRepository: SearchRepoImpl): SearchRepository

    @Binds
    fun bindsWeatherRepository(weatherUpdateRepository: WeatherUpdateRepoImpl): WeatherUpdateRepository

}