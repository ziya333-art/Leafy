package com.ujizin.leafy.domain.usecase.alarm.adjust

import com.ujizin.leafy.domain.model.Alarm
import com.ujizin.leafy.domain.model.AlarmAdjustment
import com.ujizin.leafy.domain.model.DailyWeather
import com.ujizin.leafy.domain.model.PlantProfile
import com.ujizin.leafy.domain.result.Result
import kotlinx.coroutines.flow.Flow

/**
 * Adjust alarm use case.
 * */
interface AdjustAlarmUseCase {

    /**
     * Decide how the alarm's next occurrence should behave,
     * given the plant's care profile and the weather forecast.
     *
     * @param alarm the alarm to be adjusted
     * @param profile the plant's care profile, if known
     * @param forecast the daily forecast covering the occurrence
     * @param nextOccurrence ISO-8601 date of the alarm's next ring
     * */
    operator fun invoke(
        alarm: Alarm,
        profile: PlantProfile?,
        forecast: List<DailyWeather>,
        nextOccurrence: String,
    ): Flow<Result<AlarmAdjustment>>
}
