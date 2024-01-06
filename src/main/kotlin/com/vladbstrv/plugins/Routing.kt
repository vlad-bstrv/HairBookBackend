package com.vladbstrv.plugins

import com.vladbstrv.domain.usecase.ClientUseCase
import com.vladbstrv.domain.usecase.ServiceUseCase
import com.vladbstrv.domain.usecase.UserUseCase
import com.vladbstrv.routes.clientRoute
import com.vladbstrv.routes.serviceRoute
import com.vladbstrv.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userUseCase: UserUseCase,
    clientUseCase: ClientUseCase,
    serviceUseCase: ServiceUseCase
) {

    routing {
        userRoute(userUseCase)
        clientRoute(clientUseCase)
        serviceRoute(serviceUseCase)
    }
}


