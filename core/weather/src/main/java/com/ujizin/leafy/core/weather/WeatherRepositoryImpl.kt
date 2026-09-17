@file:OptIn(com.openmeteo.api.common.Response.ExperimentalGluedUnitTimeStepValues::class)

package com.ujizin.leafy.core.weather

import com.openmeteo.api.Forecast
import com.openmeteo.api.GeocodingSearch
import com.ujizin.leafy.domain.model.DailyWeather
import javax.inject.Inject
import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.repository.WeatherRepository
import java.text.SimpleDateFormat
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Weather Repository implementation backed by Open-Meteo,
 * the free, keyless, open-source weather API.
 * */
class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {

    override fun getForecast(
        location: GardenLocation,
        days: Int,
    ): Flow<List<DailyWeather>> = flow {
        val response = Forecast(
            location.latitude.toFloat(),
            location.longitude.toFloat(),
        ) {
            forecastDays = days
            daily = listOf(
                Forecast.Daily.temperature2mMax,
                Forecast.Daily.temperature2mMin,
                Forecast.Daily.precipitationSum,
            ).joinToString(",")
        }.getOrThrow()

        val daily = response.daily
        if (daily == null) {
            emit(emptyList())
            return@flow
        }

        val dates = daily[Forecast.Daily.temperature2mMin]?.values?.keys?.toList().orEmpty()
        emit(
            dates.map { date ->
                DailyWeather(
                    date = ISO_FORMAT.format(date),
                    minTemperatureCelsius = daily[Forecast.Daily.temperature2mMin]?.values?.get(date) ?: 0.0,
                    maxTemperatureCelsius = daily[Forecast.Daily.temperature2mMax]?.values?.get(date) ?: 0.0,
                    precipitationMillimeters = daily[Forecast.Daily.precipitationSum]?.values?.get(date) ?: 0.0,
                )
            },
        )
    }.flowOn(Dispatchers.IO)

    override fun geocode(
        city: String,
    ): Flow<GardenLocation> = flow {
        val response = try {
            GeocodingSearch(name = city) {
                count = 10
                language = Locale.getDefault().language
            }.getOrThrow()
        } catch (e: Throwable) {
            android.util.Log.e("LeafyGeocode", "request failed for '" + city + "'", e)
            throw e
        }
        val result = response.results?.firstOrNull()
        if (result == null) {
            android.util.Log.e("LeafyGeocode", "no results for '" + city + "'")
        }
        requireNotNull(result) { "No geocoding result for '$city'" }
        emit(
            GardenLocation(
                name = result.name ?: city,
                latitude = result.latitude.toDouble(),
                longitude = result.longitude.toDouble(),
            ),
        )
    }.flowOn(Dispatchers.IO)

    companion object {
        private val ISO_FORMAT by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.US) }
    }
}
