package com.ujizin.leafy.domain.model

/**
 * Garden location, geocoded via Open-Meteo.
 * */
data class GardenLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double,
)
