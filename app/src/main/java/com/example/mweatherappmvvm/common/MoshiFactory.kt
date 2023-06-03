package com.example.mweatherappmvvm.common

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiFactory {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    fun get(): Moshi {
        return moshi
    }
}