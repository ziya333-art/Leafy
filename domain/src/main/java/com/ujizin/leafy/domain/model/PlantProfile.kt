package com.ujizin.leafy.domain.model

/**
 * Care requirements for a plant species, generated at development time
 * from the open Permapeople (CC BY-SA 4.0) and PlantSolve datasets
 * and bundled as app assets.
 * */
data class PlantProfile(
    val commonName: String,
    val scientificName: String? = null,
    val waterIntervalDays: Int,
    val frostTolerant: Boolean,
    val hardinessZones: List<String> = emptyList(),
    val sunExposure: SunExposure = SunExposure.FULL,
)

enum class SunExposure { FULL, PARTIAL, SHADE }
