package com.example.mweatherappmvvm.data.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


/**
 * This is network response for city details.
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class CityDetailsResponse(
    @Json(name = "name") var name: String? = null,
    @Json(name = "lat") var latitude: String? = null,
    @Json(name = "lon") var longitude: String? = null,
    @Json(name = "country") var country: String? = null,
    @Json(name = "state") var state: String? = null
) : Parcelable {
    override fun toString() = "name=$name, country=$country, state=$state"
}