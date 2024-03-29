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
        route("service") {

            get {
                try {
                    val userId = call.principal<UserModel>()!!.id
                    val services = serviceUseCase.getAllServices(userId)
                    call.respond(HttpStatusCode.OK, services)
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

                val serviceResponse = serviceUseCase.getServiceById(serviceId = id, ownerId = ownerId)

                call.respond(
                    message =  serviceResponse,
                    status =  HttpStatusCode.OK
                )
            }

            post {
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


            put {
                call.receiveNullable<AddServiceRequest>()?.let {
                    try {
                        val ownedId = call.principal<UserModel>()!!.id
                        val service = ServiceModel(
                            id = it.id,
                            owner = call.principal<UserModel>()!!.id,
                            name = it.name,
                            price = it.price,
                            time = it.time
                        )

                        serviceUseCase.updateService(service, ownedId)
                        call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.UPDATE_SUCCESSFULLY))

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
                    }
                }
            }

            delete("/{id?}") {
                call.parameters["id"]?.let {
                    try {
                        val ownedId = call.principal<UserModel>()!!.id

                        val resultDeleteService = serviceUseCase.deleteService(it.toInt(), ownedId)
                        if (resultDeleteService) {
                            call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.DELETE_SUCCESSFULLY))
                        } else {
                            call.respond(HttpStatusCode.NotFound, BaseResponse(false, Constants.Error.USER_NOT_FOUND))
                        }

                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
                    }
                }
            }
        }
    }
}
