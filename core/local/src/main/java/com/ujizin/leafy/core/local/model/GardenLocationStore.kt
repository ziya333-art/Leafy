package com.ujizin.leafy.core.local.model

import kotlinx.serialization.Serializable

@Serializable
data class GardenLocationStore(
    val name: String,
    val latitude: Double,
    val longitude: Double,
)
