package com.ujizin.leafy.domain.model

/**
 * Daily weather forecast for a garden location.
 * */
data class DailyWeather(
    val date: String, // ISO-8601, e.g. "2026-09-15"
    val minTemperatureCelsius: Double,
    val maxTemperatureCelsius: Double,
    val precipitationMillimeters: Double,
) {
    val frostRisk: Boolean get() = minTemperatureCelsius <= 0.0
    val heavyRain: Boolean get() = precipitationMillimeters >= HEAVY_RAIN_MM

    companion object {
        internal const val HEAVY_RAIN_MM = 10.0
    }
}
