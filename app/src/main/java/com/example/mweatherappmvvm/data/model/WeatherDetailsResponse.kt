package com.example.mweatherappmvvm.data.model

import com.squareup.moshi.Json

/**
 * This class has network response for weather details and extension function to convert network response to ui model
 */
data class WeatherDetails(
    var title: String,
    var description: String,
    var icon: String,
    var temp: String,
    var temp_min: String,
    var temp_max: String,
    var humidity: String
) {
    override fun toString() =
        "Weather details (title:$title,description:$description,icon:$icon,temp:$temp)"
}

data class WeatherDetailsResponse(
    @Json(name = "weather") var listOfweather: List<Weather>,
    @Json(name = "main") var otherDetails: OtherDetailsResponse
)

data class OtherDetailsResponse(
    @Json(name = "temp") val temp: String = "",
    @Json(name = "temp_min") val temp_min: String = "",
    @Json(name = "temp_max") val temp_max: String = "",
    @Json(name = "humidity") val humidity: String = ""
)

data class Weather(
    @Json(name = "main") val main: String = "",
    @Json(name = "description") val description: String = "",
    @Json(name = "icon") val icon: String = ""
)

fun WeatherDetailsResponse.toWeatherDetailsModel() = WeatherDetails(
    title = this.listOfweather[0].main,
    description = this.listOfweather[0].description,
    icon = listOfweather[0].icon,
    temp = this.otherDetails.temp,
    temp_max = this.otherDetails.temp_max,
    temp_min = this.otherDetails.temp_min,
    humidity = this.otherDetails.humidity
)