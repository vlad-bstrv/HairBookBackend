package com.vladbstrv

import com.vladbstrv.authentification.JwtService
import com.vladbstrv.data.model.tables.TestTestTable
import com.vladbstrv.data.repository.*
import com.vladbstrv.domain.repository.AppointmentRepository
import com.vladbstrv.domain.usecase.*
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

    val serviceRepository = ServiceRepositoryImpl()
    val serviceUseCase = ServiceUseCase(serviceRepository)

    val workingDayRepository = WorkingDayRepositoryImpl()
    val workingDayUseCase = WorkingDayUseCase(workingDayRepository)

    val appointmentRepository = AppointmentRepositoryImpl()
    val appointmentUseCase = AppointmentUseCase(appointmentRepository)

    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)
    configureRouting(userUseCase, clientUseCase, serviceUseCase, workingDayUseCase, appointmentUseCase)
}
