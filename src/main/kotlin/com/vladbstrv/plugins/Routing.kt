package com.vladbstrv.plugins

import com.vladbstrv.domain.usecase.*
import com.vladbstrv.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val userUseCase by inject<UserUseCase>()
    val clientUseCase by inject<ClientUseCase>()
    val serviceUseCase by inject<ServiceUseCase>()
    val appointmentUseCase by inject<AppointmentUseCase>()
    val workingDayUseCase by inject<WorkingDayUseCase>()

    routing {
        route("api/v1") {
            userRoute(userUseCase)
            clientRoute(clientUseCase)
            serviceRoute(serviceUseCase)
            appointmentRoute(appointmentUseCase)
            workingDayRoute(workingDayUseCase)
        }
    }
}


