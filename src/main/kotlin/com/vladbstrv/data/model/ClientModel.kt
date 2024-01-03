package com.vladbstrv.data.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ClientModel(
    val id: Int,
    val owner: Int,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String,
    val comment: String = "",
)
