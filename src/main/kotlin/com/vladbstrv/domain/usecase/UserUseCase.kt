package com.vladbstrv.domain.usecase

import com.auth0.jwt.JWTVerifier
import com.vladbstrv.authentification.JwtService
import com.vladbstrv.data.model.UserModel
import com.vladbstrv.domain.repository.UserRepository

class UserUseCase(
    private val repository: UserRepository,
    private val jwtService: JwtService
) {

    suspend fun createUser(user: UserModel) = repository.insertUser(user)

    suspend fun findUserByEmail(email: String) = repository.getUserByEmail(email)

    fun generateToken(user: UserModel): String = jwtService.generateToken(user)

    fun getGwtVerifier(): JWTVerifier = jwtService.getVerifier()
}