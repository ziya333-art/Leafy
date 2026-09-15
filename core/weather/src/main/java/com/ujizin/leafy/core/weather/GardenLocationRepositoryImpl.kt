package com.ujizin.leafy.core.weather

import com.ujizin.leafy.core.local.datastore.GardenLocationDataStore
import com.ujizin.leafy.core.local.model.GardenLocationStore
import com.ujizin.leafy.domain.dispatcher.IoDispatcher
import com.ujizin.leafy.domain.model.GardenLocation
import com.ujizin.leafy.domain.repository.GardenLocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GardenLocationRepositoryImpl @Inject constructor(
    private val gardenLocationDataStore: GardenLocationDataStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : GardenLocationRepository {

    override fun getGardenLocation(): Flow<GardenLocation?> = gardenLocationDataStore.getGardenLocation()
        .map { it?.toDomain() }
        .flowOn(dispatcher)

    override fun saveGardenLocation(location: GardenLocation): Flow<Unit> = flow {
        emit(
            gardenLocationDataStore.saveGardenLocation(
                GardenLocationStore(
                    name = location.name,
                    latitude = location.latitude,
                    longitude = location.longitude,
                ),
            ),
        )
    }.flowOn(dispatcher)

    private fun GardenLocationStore.toDomain() = GardenLocation(
        name = name,
        latitude = latitude,
        longitude = longitude,
    )
}
