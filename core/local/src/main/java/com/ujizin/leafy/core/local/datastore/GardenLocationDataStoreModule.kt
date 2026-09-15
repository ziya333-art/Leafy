package com.ujizin.leafy.core.local.datastore

import android.content.Context
import com.ujizin.leafy.core.local.datastore.implementation.GardenLocationDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GardenLocationDataStoreModule {

    @Provides
    @Singleton
    fun provideGardenLocationDataStore(
        @ApplicationContext context: Context,
    ): GardenLocationDataStore = GardenLocationDataStoreImpl(
        context = context,
        serializer = Json,
    )
}
