package com.ujizin.leafy.domain.usecase.alarm.weathercheck

import com.ujizin.leafy.domain.model.AdjustmentDecision
import com.ujizin.leafy.domain.model.AlarmAdjustment
import com.ujizin.leafy.domain.repository.GardenLocationRepository
import com.ujizin.leafy.domain.result.Result
import com.ujizin.leafy.domain.result.asResult
import com.ujizin.leafy.domain.result.getOrNull
import com.ujizin.leafy.domain.usecase.alarm.adjust.AdjustAlarmUseCase
import com.ujizin.leafy.domain.usecase.alarm.load.LoadAlarmUseCase
import com.ujizin.leafy.domain.usecase.weather.load.LoadForecastUseCase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class CheckAlarmWeatherUseCaseImpl(
    private val loadAlarmUseCase: LoadAlarmUseCase,
    private val gardenLocationRepository: GardenLocationRepository,
    private val loadForecastUseCase: LoadForecastUseCase,
    private val adjustAlarmUseCase: AdjustAlarmUseCase,
) : CheckAlarmWeatherUseCase {

    override fun invoke(
        alarmId: Long,
    ): Flow<Result<AlarmAdjustment>> = flow {
        val today = ISO_FORMAT.format(Calendar.getInstance().time)
        val alarm = loadAlarmUseCase(alarmId).firstOrNull()?.getOrNull()
        if (alarm == null) {
            return@flow
        }
        val location = gardenLocationRepository.getGardenLocation().firstOrNull()
        val adjustment = if (location == null) {
            AlarmAdjustment(alarm, today, AdjustmentDecision.KEEP)
        } else {
            val forecast = loadForecastUseCase(location, days = 1).firstOrNull()?.getOrNull().orEmpty()
            adjustAlarmUseCase(alarm, null, forecast, today).firstOrNull()?.getOrNull()
                ?: AlarmAdjustment(alarm, today, AdjustmentDecision.KEEP)
        }
        emit(adjustment)
    }.asResult()

    companion object {
        private val ISO_FORMAT by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.US) }
    }
}
