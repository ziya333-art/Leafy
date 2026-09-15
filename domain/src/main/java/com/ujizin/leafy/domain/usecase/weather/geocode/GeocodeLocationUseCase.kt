package com.ujizin.leafy.domain.usecase.weather.geocode

import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.result.Result
import kotlinx.coroutines.flow.Flow

/**
 * Geocode garden location use case.
 * */
interface GeocodeLocationUseCase {

    /**
     * Geocode a city name into a garden location.
     *
     * @param city name of the city to search
     * */
    operator fun invoke(
        city: String,
    ): Flow<Result<GardenLocation>>
}
