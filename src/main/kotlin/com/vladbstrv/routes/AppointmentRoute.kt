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

        post("/appointment") {
            val appointment = call.receiveNullable<InsertAppointmentRequest>() ?: return@post call.respondText(
                text = "missing parameters",
                status = HttpStatusCode.BadRequest
            )
            val appointmentResponse = appointmentUseCase.insert(appointment.toModel(call.principal<UserModel>()!!.id))

            call.respond(
                message = appointmentResponse,
                status = HttpStatusCode.OK
            )
        }

        get("/appointment") {
            try {
                val userId = call.principal<UserModel>()!!.id
                val appointment = appointmentUseCase.getById(userId)
                call.respond(HttpStatusCode.OK, appointment)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }

        }
    }
}