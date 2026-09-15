package com.ujizin.leafy.core.local.datastore.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ujizin.leafy.core.local.datastore.GardenLocationDataStore
import com.ujizin.leafy.core.local.model.GardenLocationStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

internal class GardenLocationDataStoreImpl(
    context: Context,
    private val serializer: Json,
) : GardenLocationDataStore {

    private val Context.gardenLocationStore by preferencesDataStore(name = GARDEN_LOCATION_PREFERENCES_NAME)

    private val store: DataStore<Preferences> = context.gardenLocationStore

    private val locationKey = stringPreferencesKey("garden_location_stringify")

    override fun getGardenLocation(): Flow<GardenLocationStore?> = store.data.map { preferences ->
        preferences[locationKey]?.let { serializer.decodeFromString<GardenLocationStore>(it) }
    }

    override suspend fun saveGardenLocation(location: GardenLocationStore) {
        store.edit { preferences ->
            preferences[locationKey] = serializer.encodeToString(location)
        }
    }

    companion object {
        private const val GARDEN_LOCATION_PREFERENCES_NAME = "garden_location"
    }
}
