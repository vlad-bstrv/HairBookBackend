package com.vladbstrv.data.model

import java.time.LocalDateTime

data class ClientModel(
    val id: Int,
    val owner: Int,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String,
    val comment: String = "",
)
