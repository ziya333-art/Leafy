package com.ujizin.leafy.domain.usecase.profile

import com.ujizin.leafy.domain.repository.PlantProfileRepository
import com.ujizin.leafy.domain.usecase.profile.load.LoadPlantProfilesUseCase
import com.ujizin.leafy.domain.usecase.profile.load.LoadPlantProfilesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideLoadPlantProfiles(
        repository: PlantProfileRepository,
    ): LoadPlantProfilesUseCase = LoadPlantProfilesUseCaseImpl(repository)
}
