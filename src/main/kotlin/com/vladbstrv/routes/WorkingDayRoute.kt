package com.vladbstrv.routes

import com.vladbstrv.data.model.UserModel
import com.vladbstrv.data.model.requests.InsertWorkingDayRequest
import com.vladbstrv.data.model.requests.UpdateWorkingDayRequest
import com.vladbstrv.data.model.requests.toModel
import com.vladbstrv.domain.usecase.WorkingDayUseCase
import com.vladbstrv.utils.Constants
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

            put {
                val receiveNullable = call
                    .receiveNullable<UpdateWorkingDayRequest>() ?: return@put call
                    .respondText(
                        "",
                        status = HttpStatusCode.BadRequest
                    )
                val result = workingDayUseCase
                    .update(
                        receiveNullable.toModel(call.principal<UserModel>()!!.id)
                    )

                call.respond(status = HttpStatusCode.OK, result)
            }

            get {
                call.principal<UserModel>()?.let {
                    val workingDays = workingDayUseCase.getAll(it.id)
                    call.respond(message = workingDays, status = HttpStatusCode.OK)
                    return@get
                }
            }

            get("/{id}") {
                val idString = call.parameters["id"] ?: return@get call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest
                )

                val id = idString.toIntOrNull() ?: return@get call.respondText(
                    "id is not numbers",
                    status = HttpStatusCode.BadRequest
                )

                val ownerId = call.principal<UserModel>()!!.id

                workingDayUseCase.getById(id, ownerId)?.let {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = it
                    )
                }
            }

            delete("/{id}") {
                val idString = call.parameters["id"] ?: return@delete call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest
                )

                val id = idString.toIntOrNull() ?: return@delete call.respondText(
                    "id is not numbers",
                    status = HttpStatusCode.BadRequest
                )

                val ownerId = call.principal<UserModel>()!!.id

                val delete = workingDayUseCase.delete(id, ownerId)
                if (delete) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Constants.Success.DELETE_SUCCESSFULLY
                    )
                }
            }

        }
    }
}