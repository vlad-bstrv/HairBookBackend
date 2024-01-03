package com.vladbstrv

import com.vladbstrv.authentification.JwtService
import com.vladbstrv.data.repository.ClientRepositoryImpl
import com.vladbstrv.data.repository.UserRepositoryImpl
import com.vladbstrv.domain.usecase.ClientUseCase
import com.vladbstrv.domain.usecase.UserUseCase
import com.vladbstrv.plugins.*
import com.vladbstrv.plugins.DatabaseFactory.initializationDatabase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val clientRepository = ClientRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jwtService)
    val clientUseCase = ClientUseCase(clientRepository)

    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)
    configureRouting(userUseCase, clientUseCase)
}
