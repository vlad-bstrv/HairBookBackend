package com.vladbstrv.routes

import com.vladbstrv.data.model.UserModel
import com.vladbstrv.data.model.requests.InsertWorkingDayRequest
import com.vladbstrv.data.model.requests.toModel
import com.vladbstrv.domain.usecase.WorkingDayUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.workingDayRoute(workingDayUseCase: WorkingDayUseCase) {
    authenticate("jwt") {
        route("working-day") {
            post {
                val receiveNullable = call
                    .receiveNullable<InsertWorkingDayRequest>() ?: return@post call
                    .respondText(
                        "",
                        status = HttpStatusCode.BadRequest
                    )
                val result = workingDayUseCase
                    .insert(receiveNullable.toModel(call.principal<UserModel>()!!.id))

                call.respond(status = HttpStatusCode.Created, result)
            }

            get {
                call.principal<UserModel>()?.let {
                    val workingDays = workingDayUseCase.getAll(it.id)
                    call.respond(message =  workingDays, status = HttpStatusCode.OK)
                    return@get
                }
            }
        }
    }
}