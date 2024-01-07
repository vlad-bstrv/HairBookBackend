package com.vladbstrv.data.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class WorkingDayModel(
    val id: Int = 0,
    val ownerId: Int = 0,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
)