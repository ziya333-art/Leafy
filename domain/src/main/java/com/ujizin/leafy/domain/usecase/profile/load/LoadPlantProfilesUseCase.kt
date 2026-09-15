package com.ujizin.leafy.domain.usecase.profile.load

import com.ujizin.leafy.domain.model.PlantProfile
import com.ujizin.leafy.domain.result.Result
import kotlinx.coroutines.flow.Flow

/**
 * Load plant profiles use case.
 * */
interface LoadPlantProfilesUseCase {

    /**
     * Search bundled plant profiles on data source.
     *
     * @param query name of the plant to search
     * */
    operator fun invoke(
        query: String,
    ): Flow<Result<List<PlantProfile>>>
}
