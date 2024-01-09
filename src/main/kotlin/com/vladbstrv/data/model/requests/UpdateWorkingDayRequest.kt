package com.vladbstrv.data.model.requests

import com.vladbstrv.data.model.WorkingDayModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class UpdateWorkingDayRequest (
    val id: Int = 0,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
)

fun UpdateWorkingDayRequest.toModel(ownerId: Int) = WorkingDayModel(
    id = this.id,
    ownerId = ownerId,
    date = this.date,
    startTime = this.startTime,
    endTime = this.endTime
)