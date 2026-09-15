package com.ujizin.leafy.domain.usecase.location.save

import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.repository.GardenLocationRepository
import com.ujizin.leafy.domain.result.Result
import com.ujizin.leafy.domain.result.asResult
import kotlinx.coroutines.flow.Flow

class SaveGardenLocationUseCaseImpl(
    private val repository: GardenLocationRepository,
) : SaveGardenLocationUseCase {

    override fun invoke(location: GardenLocation): Flow<Result<Unit>> =
        repository.saveGardenLocation(location).asResult()
}
