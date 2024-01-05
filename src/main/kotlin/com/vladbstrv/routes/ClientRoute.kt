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

        get("api/v1/get-all-clients") {
            try {
                val userId = call.principal<UserModel>()!!.id
                val clients = clientUseCase.getAllClients(userId)
                call.respond(HttpStatusCode.OK, clients)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        get("api/v1/get-clients-by-phone-number") {
            val clientPhoneNumberRequest = call.request.queryParameters["phoneNumber"] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@get
            }
            try {
                val userId = call.principal<UserModel>()!!.id
                val client = clientUseCase.getClientByPhoneNumber(clientPhoneNumberRequest, userId)
                if (client != null) {
                    call.respond(HttpStatusCode.OK, client)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        post("api/v1/create-client") {
            val clientRequest = call.receiveNullable<AddClientRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }

            try {
                val client = ClientModel(
                    owner = call.principal<UserModel>()!!.id,
                    firstName = clientRequest.firstName,
                    lastName = clientRequest.lastName,
                    phoneNumber = clientRequest.phoneNumber,
                    comment = clientRequest.comment,
                )

                clientUseCase.addClient(client)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.ADDED_SUCCESSFULLY))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        post("api/v1/update-client") {
            val clientRequest = call.receiveNullable<AddClientRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }

            try {
                val ownedId = call.principal<UserModel>()!!.id
                val client = ClientModel(
                    id = clientRequest.id ?: 0,
                    owner = ownedId,
                    firstName = clientRequest.firstName,
                    lastName = clientRequest.lastName,
                    phoneNumber = clientRequest.phoneNumber,
                    comment = clientRequest.comment,
                )

                clientUseCase.updateClient(client, ownedId)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.UPDATE_SUCCESSFULLY))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        delete("api/v1/delete-client") {
            val clientRequest = call.request.queryParameters["id"]?.toInt() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@delete
            }

            try {
                val ownedId = call.principal<UserModel>()!!.id

                clientUseCase.deleteClient(clientRequest, ownedId)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.DELETE_SUCCESSFULLY))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }
    }

}