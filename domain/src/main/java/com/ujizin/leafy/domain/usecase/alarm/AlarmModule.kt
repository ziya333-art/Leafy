package com.ujizin.leafy.domain.usecase.alarm

import com.ujizin.leafy.domain.repository.AlarmRepository
import com.ujizin.leafy.domain.usecase.alarm.add.AddAlarmUseCase
import com.ujizin.leafy.domain.usecase.alarm.add.AddAlarmUseCaseImpl
import com.ujizin.leafy.domain.usecase.alarm.adjust.AdjustAlarmUseCase
import com.ujizin.leafy.domain.usecase.alarm.adjust.AdjustAlarmUseCaseImpl
import com.ujizin.leafy.domain.repository.GardenLocationRepository
import com.ujizin.leafy.domain.usecase.alarm.weathercheck.CheckAlarmWeatherUseCase
import com.ujizin.leafy.domain.usecase.alarm.weathercheck.CheckAlarmWeatherUseCaseImpl
import com.ujizin.leafy.domain.usecase.weather.load.LoadForecastUseCase
import com.ujizin.leafy.domain.usecase.alarm.delete.DeleteAlarmUseCase
import com.ujizin.leafy.domain.usecase.alarm.delete.DeleteAlarmUseCaseImpl
import com.ujizin.leafy.domain.usecase.alarm.load.LoadAlarmUseCase
import com.ujizin.leafy.domain.usecase.alarm.load.LoadAlarmUseCaseImpl
import com.ujizin.leafy.domain.usecase.alarm.load.LoadAlarmsUseCase
import com.ujizin.leafy.domain.usecase.alarm.load.LoadAlarmsUseCaseImpl
import com.ujizin.leafy.domain.usecase.alarm.update.UpdateAlarmUseCase
import com.ujizin.leafy.domain.usecase.alarm.update.UpdateAlarmUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {

    @Provides
    @Singleton
    fun provideAddAlarm(
        repository: AlarmRepository,
    ): AddAlarmUseCase = AddAlarmUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideLoadAlarm(
        repository: AlarmRepository,
    ): LoadAlarmUseCase = LoadAlarmUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideLoadAlarms(
        repository: AlarmRepository,
    ): LoadAlarmsUseCase = LoadAlarmsUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideUpdateAlarm(
        repository: AlarmRepository,
    ): UpdateAlarmUseCase = UpdateAlarmUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideDeleteAlarm(
        repository: AlarmRepository,
    ): DeleteAlarmUseCase = DeleteAlarmUseCaseImpl(repository)

    @Provides
    @Singleton
    fun provideAdjustAlarm(): AdjustAlarmUseCase = AdjustAlarmUseCaseImpl()

    @Provides
    @Singleton
    fun provideCheckAlarmWeather(
        loadAlarmUseCase: LoadAlarmUseCase,
        gardenLocationRepository: GardenLocationRepository,
        loadForecastUseCase: LoadForecastUseCase,
        adjustAlarmUseCase: AdjustAlarmUseCase,
    ): CheckAlarmWeatherUseCase = CheckAlarmWeatherUseCaseImpl(
        loadAlarmUseCase = loadAlarmUseCase,
        gardenLocationRepository = gardenLocationRepository,
        loadForecastUseCase = loadForecastUseCase,
        adjustAlarmUseCase = adjustAlarmUseCase,
    )
}
