package com.ujizin.leafy.domain.usecase.location.save

import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.result.Result
import kotlinx.coroutines.flow.Flow

/**
 * Save garden location use case.
 * */
interface SaveGardenLocationUseCase {

    /**
     * Save the garden location.
     *
     * @param location the location to be saved
     * */
    operator fun invoke(location: GardenLocation): Flow<Result<Unit>>
}
