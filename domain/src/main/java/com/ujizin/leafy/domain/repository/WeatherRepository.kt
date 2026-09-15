package com.ujizin.leafy.domain.repository

import com.ujizin.leafy.domain.model.DailyWeather
import com.ujizin.leafy.domain.model.GardenLocation
import kotlinx.coroutines.flow.Flow

/**
 * Interface to Weather Repository implementation (Open-Meteo).
 * */
interface WeatherRepository {

    /**
     * Get the daily forecast for a garden location.
     *
     * @param location the garden's location
     * @param days number of forecast days
     * */
    fun getForecast(location: GardenLocation, days: Int): Flow<List<DailyWeather>>

    /**
     * Geocode a city name into a garden location.
     *
     * @param city name of the city to search
     * */
    fun geocode(city: String): Flow<GardenLocation>
}
