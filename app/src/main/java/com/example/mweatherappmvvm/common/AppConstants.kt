package com.example.mweatherappmvvm.common

object AppConstants {

    const val LOCATION_UPDATE_INTERVAL =  10000  //millis
    const val LOCATION_UPDATE_INTERVAL_FASTEST =  5000  //millis

    //Retrofit
    const val CONNECT_TIMEOUT=60L
    const val READ_TIMEOUT=60L
    const val WRITE_TIMEOUT=60L
}

sealed class UpdateBy {
    data class ByCityName(val name:String):UpdateBy()
    data class ByCordinates(val latitude:String, val longitude:String):UpdateBy()
}
