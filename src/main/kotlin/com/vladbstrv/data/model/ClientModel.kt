package com.vladbstrv.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ClientModel(
    val id: Int = 0,
    val owner: Int,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String,
    val comment: String = "",
)
