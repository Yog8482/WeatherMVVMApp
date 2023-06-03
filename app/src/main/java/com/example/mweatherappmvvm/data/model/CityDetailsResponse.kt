package com.example.mweatherappmvvm.data.model

import com.squareup.moshi.Json


/**
 * This class has network response for city details.
 */
data class CityDetailsResponse(
    @Json(name = "name") var name: String? = null,
    @Json(name = "lat") var latitude: String? = null,
    @Json(name = "lon") var longitude: String? = null,
    @Json(name = "country") var country: String? = null,
    @Json(name = "state") var state: String? = null
    )  {
    override fun toString()= "name=$name, country=$country, state=$state"
}