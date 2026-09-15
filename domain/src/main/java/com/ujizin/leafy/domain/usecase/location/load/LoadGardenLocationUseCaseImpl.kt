package com.ujizin.leafy.domain.usecase.location.load

import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.repository.GardenLocationRepository
import com.ujizin.leafy.domain.result.Result
import com.ujizin.leafy.domain.result.asResult
import kotlinx.coroutines.flow.Flow

class LoadGardenLocationUseCaseImpl(
    private val repository: GardenLocationRepository,
) : LoadGardenLocationUseCase {

    override fun invoke(): Flow<Result<GardenLocation?>> = repository.getGardenLocation().asResult()
}
