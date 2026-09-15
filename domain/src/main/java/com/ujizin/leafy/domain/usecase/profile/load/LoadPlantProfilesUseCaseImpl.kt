package com.ujizin.leafy.domain.usecase.profile.load

import com.ujizin.leafy.domain.model.PlantProfile
import com.ujizin.leafy.domain.repository.PlantProfileRepository
import com.ujizin.leafy.domain.result.Result
import com.ujizin.leafy.domain.result.asResult
import kotlinx.coroutines.flow.Flow

class LoadPlantProfilesUseCaseImpl(
    private val repository: PlantProfileRepository,
) : LoadPlantProfilesUseCase {

    override fun invoke(
        query: String,
    ): Flow<Result<List<PlantProfile>>> = repository.searchProfiles(query).asResult()
}
