package com.example.mweatherappmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mweatherappmvvm.common.UpdateBy
import com.example.mweatherappmvvm.data.model.CityDetailsResponse
import com.example.mweatherappmvvm.data.model.WeatherDetails
import com.example.mweatherappmvvm.domain.usecase.SearchCityUsecase
import com.example.mweatherappmvvm.domain.usecase.WeatherUpdateUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val searchUsecase: SearchCityUsecase,
    private val weatherUpdateUsecase: WeatherUpdateUsecase
) : ViewModel() {

    private val _searchstate = MutableStateFlow<SearchCityState>(SearchCityState.Idle)
    val searchUIState: StateFlow<SearchCityState> get() = _searchstate

    private val _weatherUpdateState = MutableStateFlow<WeatherUpdateState>(WeatherUpdateState.Idle)
    val weatherUIState: StateFlow<WeatherUpdateState> get() = _weatherUpdateState

    fun resetSearchState() {
        _searchstate.value = SearchCityState.Idle
    }

    fun resetWeatherState() {
        _weatherUpdateState.value = WeatherUpdateState.Idle
    }

    /**
     * Get Weather updates by City name
     * @param name
     */
    fun getWeatherUpdateByCityName(name: String) {
        viewModelScope.launch {
            weatherUpdateUsecase.execute(UpdateBy.ByCityName(name = name))
                .onStart {
                    _weatherUpdateState.value = WeatherUpdateState.Loading
                }
                .catch {
                    _weatherUpdateState.value = WeatherUpdateState.Fail(it.message)
                }
                .collect { weatherDetails ->
                    weatherDetails?.let {
                        _weatherUpdateState.value = WeatherUpdateState.Success(it)
                    }?: run {
                        _weatherUpdateState.value =
                            WeatherUpdateState.Fail("No Data received from server")
                    }

                }
        }
    }

    /**
     * Get weather update by latitude and longitude
     * @param latitude
     * @param longitude
     */

    fun getWeatherUpdateByCordinates(latitude: String, longitude: String) {
        viewModelScope.launch {
            weatherUpdateUsecase.execute(UpdateBy.ByCordinates(latitude, longitude))
                .onStart {
                    _weatherUpdateState.value = WeatherUpdateState.Loading
                }
                .catch {
                    _weatherUpdateState.value = WeatherUpdateState.Fail(it.message)
                }
                .collect { weatherDetails ->
                    weatherDetails?.let {
                        _weatherUpdateState.value = WeatherUpdateState.Success(it)
                    }?: run {
                        _weatherUpdateState.value =
                            WeatherUpdateState.Fail("No Data received from server")
                    }

                }
        }
    }

    /**
     * get city list
     * @param query
     */
    fun getCityDetailsByQuery(query: String) {
        viewModelScope.launch {
            searchUsecase.execute(query)
                .onStart {
                    _searchstate.value = SearchCityState.Loading
                }
                .catch {
                    _searchstate.value = SearchCityState.Fail(it.message)
                }
                .collect { cities ->
                    _searchstate.value = SearchCityState.Success(cities)
                }
        }
    }
}


sealed interface SearchCityState {
    object Idle : SearchCityState
    object Loading : SearchCityState
    data class Success(val data: List<CityDetailsResponse>) : SearchCityState
    data class Fail(val failMsg: String?) : SearchCityState
}

sealed interface WeatherUpdateState {
    object Idle : WeatherUpdateState
    object Loading : WeatherUpdateState
    data class Success(val data: WeatherDetails) : WeatherUpdateState
    data class Fail(val failMsg: String?) : WeatherUpdateState
}