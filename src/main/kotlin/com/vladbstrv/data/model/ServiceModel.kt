package com.vladbstrv.data.model

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class ServiceModel(
    val id: Int,
    val owner: Int,
    val name: String,
    val price: Double,
    val time: LocalTime
)
