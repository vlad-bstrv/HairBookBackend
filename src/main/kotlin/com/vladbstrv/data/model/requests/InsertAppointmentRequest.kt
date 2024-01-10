package com.vladbstrv.data.model.requests

import com.vladbstrv.data.model.AppointmentModel
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class InsertAppointmentRequest (
    val id: Int = 0,
    val workingDayId: Int,
    val servicesId: List<Int>,
    val startTime: LocalTime
)

fun InsertAppointmentRequest.toModel(ownerId: Int) = AppointmentModel(
    id = this.id,
    ownerId = ownerId,
    workingDayId = this.workingDayId,
    servicesId = this.servicesId,
    startTime = this.startTime
)