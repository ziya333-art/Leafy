package com.ujizin.leafy.domain.repository

import com.ujizin.leafy.domain.model.GardenLocation
import kotlinx.coroutines.flow.Flow

/**
 * Interface to Garden Location repository implementation.
 * */
interface GardenLocationRepository {

    /**
     * Get the saved garden location, if any.
     * */
    fun getGardenLocation(): Flow<GardenLocation?>

    /**
     * Save the garden location.
     *
     * @param location the location to be saved
     * */
    fun saveGardenLocation(location: GardenLocation): Flow<Unit>
}
