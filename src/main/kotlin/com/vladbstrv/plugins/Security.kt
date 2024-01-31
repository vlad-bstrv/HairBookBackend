package com.vladbstrv.plugins

import com.vladbstrv.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val userUseCase by inject<UserUseCase>()

    authentication {
        jwt("jwt") {
            verifier(userUseCase.getGwtVerifier())
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
