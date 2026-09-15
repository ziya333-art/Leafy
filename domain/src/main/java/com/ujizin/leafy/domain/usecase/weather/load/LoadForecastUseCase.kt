package com.ujizin.leafy.domain.usecase.weather.load

import com.ujizin.leafy.domain.model.DailyWeather
import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.result.Result
import kotlinx.coroutines.flow.Flow

/**
 * Load weather forecast use case.
 * */
interface LoadForecastUseCase {

    /**
     * Load the daily forecast on data source.
     *
     * @param location the garden's location
     * @param days number of forecast days
     * */
    operator fun invoke(
        location: GardenLocation,
        days: Int = DEFAULT_DAYS,
    ): Flow<Result<List<DailyWeather>>>

    companion object {
        internal const val DEFAULT_DAYS = 7
    }
}
