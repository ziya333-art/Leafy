package com.ujizin.leafy.domain.usecase.location

import com.ujizin.leafy.domain.repository.GardenLocationRepository
import com.ujizin.leafy.domain.usecase.location.load.LoadGardenLocationUseCase
import com.ujizin.leafy.domain.usecase.location.load.LoadGardenLocationUseCaseImpl
import com.ujizin.leafy.domain.usecase.location.save.SaveGardenLocationUseCase
import com.ujizin.leafy.domain.usecase.location.save.SaveGardenLocationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLoadGardenLocation(
        repository: GardenLocationRepository,
    ): LoadGardenLocationUseCase = LoadGardenLocationUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideSaveGardenLocation(
        repository: GardenLocationRepository,
    ): SaveGardenLocationUseCase = SaveGardenLocationUseCaseImpl(repository)
}
