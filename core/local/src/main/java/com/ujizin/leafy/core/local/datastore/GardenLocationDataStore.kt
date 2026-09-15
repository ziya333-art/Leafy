package com.ujizin.leafy.core.local.datastore

import com.ujizin.leafy.core.local.model.GardenLocationStore
import kotlinx.coroutines.flow.Flow

/**
 * Data store to Garden location.
 * */
interface GardenLocationDataStore {

    /**
     * Get the saved garden location, if any.
     *
     * @return a garden location or null when not set
     * */
    fun getGardenLocation(): Flow<GardenLocationStore?>

    /**
     * Save the garden location.
     *
     * @param location to be saved
     * */
    suspend fun saveGardenLocation(location: GardenLocationStore)
}
