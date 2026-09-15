package com.ujizin.leafy.domain.usecase.weather

import com.ujizin.leafy.domain.repository.WeatherRepository
import com.ujizin.leafy.domain.usecase.weather.geocode.GeocodeLocationUseCase
import com.ujizin.leafy.domain.usecase.weather.geocode.GeocodeLocationUseCaseImpl
import com.ujizin.leafy.domain.usecase.weather.load.LoadForecastUseCase
import com.ujizin.leafy.domain.usecase.weather.load.LoadForecastUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideLoadForecast(
        repository: WeatherRepository,
    ): LoadForecastUseCase = LoadForecastUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideGeocodeLocation(
        repository: WeatherRepository,
    ): GeocodeLocationUseCase = GeocodeLocationUseCaseImpl(repository)
}
