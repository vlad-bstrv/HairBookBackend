package com.vladbstrv.plugins

import com.vladbstrv.authentification.JwtService
import com.vladbstrv.data.model.RoleModel
import com.vladbstrv.data.model.UserModel
import com.vladbstrv.data.repository.UserRepositoryImpl
import com.vladbstrv.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking

fun Application.configureSecurity() {

    val jwtService = JwtService()
    val repository = UserRepositoryImpl()
    val userUseCase = UserUseCase(repository, jwtService)

    authentication {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
            realm = "Service server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.findUserByEmail(email)
                user
            }
        }
    }
}
