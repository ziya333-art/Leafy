package com.ujizin.leafy.domain.usecase.weather.load

import com.ujizin.leafy.domain.model.DailyWeather
import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.repository.WeatherRepository
import com.ujizin.leafy.domain.result.Result
import com.ujizin.leafy.domain.result.asResult
import kotlinx.coroutines.flow.Flow

class LoadForecastUseCaseImpl(
    private val repository: WeatherRepository,
) : LoadForecastUseCase {

    override fun invoke(
        location: GardenLocation,
        days: Int,
    ): Flow<Result<List<DailyWeather>>> = repository.getForecast(location, days).asResult()
}
