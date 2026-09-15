package com.ujizin.leafy.domain.usecase.weather.geocode

import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.repository.WeatherRepository
import com.ujizin.leafy.domain.result.Result
import com.ujizin.leafy.domain.result.asResult
import kotlinx.coroutines.flow.Flow

class GeocodeLocationUseCaseImpl(
    private val repository: WeatherRepository,
) : GeocodeLocationUseCase {

    override fun invoke(
        city: String,
    ): Flow<Result<GardenLocation>> = repository.geocode(city).asResult()
}
