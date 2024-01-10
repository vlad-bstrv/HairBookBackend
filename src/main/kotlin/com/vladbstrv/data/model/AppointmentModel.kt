package com.vladbstrv.data.model

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class AppointmentModel(
    val id: Int = 0,
    val ownerId: Int = 0,
    val workingDayId: Int = 0,
    val servicesId: List<Int> = listOf(),
    val startTime: LocalTime
)