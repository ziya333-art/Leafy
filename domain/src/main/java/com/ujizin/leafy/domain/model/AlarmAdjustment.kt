package com.ujizin.leafy.domain.model

/**
 * Weather-based adjustment decision for a single alarm occurrence.
 * */
data class AlarmAdjustment(
    val alarm: Alarm,
    val nextOccurrence: String, // ISO-8601 date of the occurrence this decision applies to
    val decision: AdjustmentDecision,
)

enum class AdjustmentDecision {
    /** Fire the reminder as scheduled. */
    KEEP,

    /** Heavy rain already watered the plant. */
    SKIP_RAIN,

    /** Heat calls for an additional watering. */
    EXTRA_HEAT,

    /** Frost is coming — hold off and protect non-hardy plants. */
    FROST_RISK,
}
