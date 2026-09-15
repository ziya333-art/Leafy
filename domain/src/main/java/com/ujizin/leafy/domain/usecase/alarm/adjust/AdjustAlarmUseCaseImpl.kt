package com.ujizin.leafy.domain.usecase.alarm.adjust

import com.ujizin.leafy.domain.model.AdjustmentDecision
import com.ujizin.leafy.domain.model.Alarm
import com.ujizin.leafy.domain.model.AlarmAdjustment
import com.ujizin.leafy.domain.model.DailyWeather
import com.ujizin.leafy.domain.model.PlantProfile
import com.ujizin.leafy.domain.result.Result
import com.ujizin.leafy.domain.result.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AdjustAlarmUseCaseImpl : AdjustAlarmUseCase {

    override fun invoke(
        alarm: Alarm,
        profile: PlantProfile?,
        forecast: List<DailyWeather>,
        nextOccurrence: String,
    ): Flow<Result<AlarmAdjustment>> = flowOf(
        AlarmAdjustment(
            alarm = alarm,
            nextOccurrence = nextOccurrence,
            decision = decide(profile, forecast, nextOccurrence),
        ),
    ).asResult()

    private fun decide(
        profile: PlantProfile?,
        forecast: List<DailyWeather>,
        nextOccurrence: String,
    ): AdjustmentDecision {
        val day = forecast.firstOrNull { it.date == nextOccurrence }
            ?: return AdjustmentDecision.KEEP
        val frostTolerant = profile?.frostTolerant ?: true
        if (day.frostRisk && !frostTolerant) return AdjustmentDecision.FROST_RISK
        if (day.heavyRain) return AdjustmentDecision.SKIP_RAIN
        if (day.maxTemperatureCelsius >= HEAT_THRESHOLD_C) return AdjustmentDecision.EXTRA_HEAT
        return AdjustmentDecision.KEEP
    }

    companion object {
        internal const val HEAT_THRESHOLD_C = 30.0
    }
}
