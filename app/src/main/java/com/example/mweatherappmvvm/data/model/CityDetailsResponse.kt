package com.example.mweatherappmvvm.data.model

import android.os.Parcel
import android.os.Parcelable
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
    ):Parcelable  {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun toString()= "name=$name, country=$country, state=$state"
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeString(country)
        parcel.writeString(state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityDetailsResponse> {
        override fun createFromParcel(parcel: Parcel): CityDetailsResponse {
            return CityDetailsResponse(parcel)
        }

        override fun newArray(size: Int): Array<CityDetailsResponse?> {
            return arrayOfNulls(size)
        }
    }
}