package com.example.mweatherappmvvm.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * This class has network response for weather details and extension function to
 * convert network response into ui model
 */
data class WeatherDetails(
    val city: String,
    val country: String,
    val title: String,
    val description: String,
    val icon: String,
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val humidity: String
) {
    override fun toString() =
        "Weather details (city:$city,country:$country,title:$title,description:$description,icon:$icon,temp:$temp)"
    val inDegree=(temp - 273.15)
    fun tempInDegreeCelcius():String= String.format("%.0f", inDegree)
}

fun WeatherAPIResponse.toWeatherDetailsModel() =
    WeatherDetails(
    title = this.listOfweather[0].main,
    description = this.listOfweather[0].description,
    icon = this.listOfweather[0].icon,
    temp = this.main.temp,
    temp_max = this.main.temp_max,
    temp_min = this.main.temp_min ,
    humidity = this.main.humidity.toString(),
    city = this.name,
    country = this.sys.country
)


@JsonClass(generateAdapter = true)
data class WeatherAPIResponse(
//    @field:Json(name = "base") val base: String,
//   @field:Json(name = "clouds")  val clouds: Clouds,
//   @field:Json(name = "cod")  val cod: Int,
//   @field:Json(name = "coord")  val coord: Coord,
//   @field:Json(name = "dt")  val dt: Int=0,
//   @field:Json(name = "id")  val id: Int=0,
   @field:Json(name = "main")  val main: Main,
   @field:Json(name = "name")  val name: String,
   @field:Json(name = "sys")  val sys: Sys,
//   @field:Json(name = "timezone")  val timezone: Int,
//   @field:Json(name = "visibility")  val visibility: Int,
   @field:Json(name = "weather")  val listOfweather: List<Weatherr>,
//   @field:Json(name = "wind")  val wind: Wind
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Main(
//    val feels_like: Double,
//    val grnd_level: Int,
    val humidity: Int,
//    val pressure: Int,
//    val sea_level: Int,
    val temp: Double=0.0,
    val temp_max: Double=0.0,
    val temp_min: Double=0.0
)

data class Sys(
    val country: String,
//    val id: Int=0,
//    val sunrise: Int=0,
//    val sunset: Int=0,
//    val type: Int=-1
)

data class Weatherr(
    val description: String,
    val icon: String,
//    val id: Int=0,
    val main: String
)

data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)
