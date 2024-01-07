package com.vladbstrv.plugins

import com.vladbstrv.domain.usecase.ClientUseCase
import com.vladbstrv.domain.usecase.ServiceUseCase
import com.vladbstrv.domain.usecase.UserUseCase
import com.vladbstrv.domain.usecase.WorkingDayUseCase
import com.vladbstrv.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userUseCase: UserUseCase,
    clientUseCase: ClientUseCase,
    serviceUseCase: ServiceUseCase,
    workingDayUseCase: WorkingDayUseCase
) {

    routing {
        route("api/v1") {
            userRoute(userUseCase)
            clientRoute(clientUseCase)
            serviceRoute(serviceUseCase)
            appointmentRoute()
            workingDayRoute(workingDayUseCase)
        }
    }
}


