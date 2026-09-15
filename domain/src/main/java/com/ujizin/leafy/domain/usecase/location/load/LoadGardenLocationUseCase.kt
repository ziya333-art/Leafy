package com.ujizin.leafy.domain.usecase.location.load

import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.result.Result
import kotlinx.coroutines.flow.Flow

/**
 * Load garden location use case.
 * */
interface LoadGardenLocationUseCase {

    /**
     * Load the saved garden location, if any.
     * */
    operator fun invoke(): Flow<Result<GardenLocation?>>
}
