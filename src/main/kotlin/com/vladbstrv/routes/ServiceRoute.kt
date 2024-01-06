package com.vladbstrv.routes

import com.vladbstrv.data.model.ServiceModel
import com.vladbstrv.data.model.UserModel
import com.vladbstrv.data.model.requests.AddServiceRequest
import com.vladbstrv.data.model.response.BaseResponse
import com.vladbstrv.domain.usecase.ServiceUseCase
import com.vladbstrv.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.serviceRoute(serviceUseCase: ServiceUseCase) {

    authenticate("jwt") {

        get("api/v1/get-all-services") {
            try {
                val userId = call.principal<UserModel>()!!.id
                val services = serviceUseCase.getAllServices(userId)
                call.respond(HttpStatusCode.OK, services)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        post("api/v1/create-service") {
            call.receiveNullable<AddServiceRequest>()?.let {
                try {
                    val service = ServiceModel(
                        id = it.id,
                        owner = call.principal<UserModel>()!!.id,
                        name = it.name,
                        price = it.price,
                        time = it.time
                    )
                    serviceUseCase.insertService(service)
                    call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.ADDED_SUCCESSFULLY))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
                }
            }
        }
    }
}