package com.vladbstrv.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddClientRequest(
    val id: Int? = null,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String,
    val comment: String = "",
)
