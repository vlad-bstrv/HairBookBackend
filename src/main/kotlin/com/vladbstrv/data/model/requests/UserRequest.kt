package com.vladbstrv.data.model.requests

import com.vladbstrv.data.model.RoleModel
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val id: Int,
    val email: String,
    val login: String,
    val firstName: String,
    val lastName: String,
    val role: RoleModel,
)
