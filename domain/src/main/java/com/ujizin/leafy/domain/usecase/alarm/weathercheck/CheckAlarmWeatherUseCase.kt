package com.ujizin.leafy.domain.usecase.alarm.weathercheck

import com.ujizin.leafy.domain.model.AlarmAdjustment
import com.ujizin.leafy.domain.result.Result
import kotlinx.coroutines.flow.Flow

/**
 * Check alarm weather use case.
 * */
interface CheckAlarmWeatherUseCase {

    /**
     * Check how today's weather affects the given alarm.
     *
     * @param alarmId the alarm's id
     * */
    operator fun invoke(
        alarmId: Long,
    ): Flow<Result<AlarmAdjustment>>
}
