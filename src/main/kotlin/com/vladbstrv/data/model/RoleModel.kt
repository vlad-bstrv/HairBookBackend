package com.vladbstrv.data.model

import com.vladbstrv.utils.Constants

enum class RoleModel {
    ADMIN, GUEST
}

fun String.getRoleByString(): RoleModel = when(this) {
    Constants.Role.ADMIN -> RoleModel.ADMIN
    else -> RoleModel.GUEST
}

fun RoleModel.getStringByRole(): String = when(this) {
    RoleModel.ADMIN -> Constants.Role.ADMIN
    else -> Constants.Role.GUEST
}