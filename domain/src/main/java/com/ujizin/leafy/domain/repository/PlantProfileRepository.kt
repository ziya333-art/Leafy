package com.ujizin.leafy.domain.repository

import com.ujizin.leafy.domain.model.PlantProfile
import kotlinx.coroutines.flow.Flow

/**
 * Interface to Plant Profile Repository implementation (bundled dataset).
 * */
interface PlantProfileRepository {

    /**
     * Search bundled plant profiles.
     *
     * @param query name of the plant to search
     * */
    fun searchProfiles(query: String): Flow<List<PlantProfile>>

    /**
     * Get a bundled plant profile by its scientific name.
     *
     * @param scientificName the species' scientific name
     * */
    fun getProfile(scientificName: String): Flow<PlantProfile?>
}
