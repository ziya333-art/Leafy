package com.ujizin.leafy.core.weather

import com.ujizin.leafy.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Binds the Open-Meteo adapter to the domain WeatherRepository.
 * */
@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherBindingModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl,
    ): WeatherRepository
}
