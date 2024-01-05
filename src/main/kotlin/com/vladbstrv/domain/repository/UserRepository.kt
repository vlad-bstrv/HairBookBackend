package com.vladbstrv.domain.repository

import com.vladbstrv.data.model.UserModel

interface UserRepository {

    suspend fun getUserByEmail(email: String): UserModel?

    suspend fun insertUser(user: UserModel)
}