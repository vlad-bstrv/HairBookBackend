package com.vladbstrv.data.model.requests

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class AddServiceRequest(
    val id: Int = 0,
    val owner: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val time: LocalTime,
)
