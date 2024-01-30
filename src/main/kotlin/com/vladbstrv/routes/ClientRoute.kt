package com.vladbstrv.routes

import com.vladbstrv.data.model.ClientModel
import com.vladbstrv.data.model.UserModel
import com.vladbstrv.data.model.requests.AddClientRequest
import com.vladbstrv.data.model.response.BaseResponse
import com.vladbstrv.domain.usecase.ClientUseCase
import com.vladbstrv.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.clientRoute(clientUseCase: ClientUseCase) {

    authenticate("jwt") {
        route("/client") {

            get {
                try {
                    val userId = call.principal<UserModel>()!!.id
                    val clients = clientUseCase.getAllClients(userId)
                    call.respond(HttpStatusCode.OK, clients)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
                }
            }

            get("/{phoneNumber}") {
                call.parameters["phoneNumber"]?.let {
                    try {
                        val userId = call.principal<UserModel>()!!.id
                        val clients = clientUseCase.getClientByPhoneNumber(it, userId)
                        call.respond(HttpStatusCode.OK, clients)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
                    }
                }
            }


            post {
                call.receiveNullable<AddClientRequest>()?.let {
                    try {
                        val client = ClientModel(
                            owner = call.principal<UserModel>()!!.id,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            phoneNumber = it.phoneNumber,
                            comment = it.comment,
                        )
                        clientUseCase.addClient(client)
                        call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.ADDED_SUCCESSFULLY))
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
                    }
                }
            }

            put {
                call.receiveNullable<AddClientRequest>()?.let {
                    try {
                        val ownedId = call.principal<UserModel>()!!.id
                        val client = ClientModel(
                            id = it.id,
                            owner = ownedId,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            phoneNumber = it.phoneNumber,
                            comment = it.comment,
                        )

                        clientUseCase.updateClient(client, ownedId)
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

                        val resultDeleteClient = clientUseCase.deleteClient(it.toInt(), ownedId)
                        if (resultDeleteClient) {
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
