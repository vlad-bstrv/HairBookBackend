package com.vladbstrv.routes

import com.vladbstrv.data.model.UserModel
import com.vladbstrv.data.model.requests.InsertAppointmentRequest
import com.vladbstrv.data.model.requests.toModel
import com.vladbstrv.data.model.response.BaseResponse
import com.vladbstrv.domain.usecase.AppointmentUseCase
import com.vladbstrv.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.appointmentRoute(appointmentUseCase: AppointmentUseCase) {

    authenticate("jwt") {
        route("/appointment") {

            post {
                val appointment = call.receiveNullable<InsertAppointmentRequest>() ?: return@post call.respondText(
                    text = "missing parameters",
                    status = HttpStatusCode.BadRequest
                )
                val appointmentResponse =
                    appointmentUseCase.insert(appointment.toModel(call.principal<UserModel>()!!.id))

                call.respond(
                    message = appointmentResponse,
                    status = HttpStatusCode.OK
                )
            }

            get {
                try {
                    val userId = call.principal<UserModel>()!!.id
                    val appointment = appointmentUseCase.getAll(userId)
                    call.respond(HttpStatusCode.OK, appointment)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
                }

            }

            get("/{id}") {
                val idString = call.parameters["id"] ?: return@get call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest
                )

                val id = idString.toIntOrNull() ?: return@get call.respondText(
                    "id is not digits",
                    status = HttpStatusCode.BadRequest
                )

                val ownerId = call.principal<UserModel>()?.id ?: return@get call.respondText(
                    "error principal",
                    status = HttpStatusCode.BadRequest
                )

                val appointmentResponse = appointmentUseCase.getById(id, ownerId)

                call.respond(
                    message = appointmentResponse,
                    status = HttpStatusCode.OK
                )
            }

            delete("/{id}") {
                val idString = call.parameters["id"] ?: return@delete call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest
                )

                val id = idString.toIntOrNull() ?: return@delete call.respondText(
                    "id is not digits",
                    status = HttpStatusCode.BadRequest
                )

                val ownerId = call.principal<UserModel>()?.id ?: return@delete call.respondText(
                    "error principal",
                    status = HttpStatusCode.BadRequest
                )

                val deleteResult = appointmentUseCase.delete(id, ownerId)

                call.respond(
                    message = deleteResult,
                    status = HttpStatusCode.OK
                )
            }
        }
    }
}