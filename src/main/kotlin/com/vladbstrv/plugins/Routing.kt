package com.vladbstrv.plugins

import com.vladbstrv.domain.usecase.*
import com.vladbstrv.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userUseCase: UserUseCase,
    clientUseCase: ClientUseCase,
    serviceUseCase: ServiceUseCase,
    workingDayUseCase: WorkingDayUseCase,
    appointmentUseCase: AppointmentUseCase
) {

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


