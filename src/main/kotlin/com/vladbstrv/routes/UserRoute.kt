package com.vladbstrv.routes

import com.vladbstrv.authentification.hash
import com.vladbstrv.data.model.UserModel
import com.vladbstrv.data.model.getRoleByString
import com.vladbstrv.data.model.requests.LoginRequest
import com.vladbstrv.data.model.requests.RegisterRequest
import com.vladbstrv.data.model.requests.UserRequest
import com.vladbstrv.data.model.response.BaseResponse
import com.vladbstrv.domain.usecase.UserUseCase
import com.vladbstrv.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(userUseCase: UserUseCase) {

    val hashFunction = { p: String -> hash(password = p) }

    post("signup") {
        val registerRequest = call.receiveNullable<RegisterRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }

        try {
            val user = UserModel(
                id = 0,
                email = registerRequest.email.trim().lowercase(),
                login = registerRequest.login.trim().lowercase(),
                password = hashFunction(registerRequest.password.trim()),
                firstName = registerRequest.firstName.trim(),
                lastName = registerRequest.lastName.trim(),
                role = registerRequest.role.trim().getRoleByString()
            )

            userUseCase.createUser(user)
            call.respond(HttpStatusCode.OK, BaseResponse(true, userUseCase.generateToken(user)))

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Conflict,
                BaseResponse(false, e.message ?: Constants.Error.GENERAL)
            )
        }
    }

    post("/signin") {
        val loginRequest = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }

        try {
            val user = userUseCase.findUserByEmail(loginRequest.email.trim().lowercase())

            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.WRONG_EMAIL))
                return@post
            }

            if (user.password == hashFunction(loginRequest.password)) {
                call.respond(HttpStatusCode.OK, BaseResponse(true, userUseCase.generateToken(user)))
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BaseResponse(false, Constants.Error.INCORRECT_PASSWORD)
                )
            }

        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.Conflict,
                BaseResponse(false, e.message ?: Constants.Error.GENERAL)
            )
        }
    }

    authenticate("jwt") {
        get("/user") {
            try {
                val user = call.principal<UserModel>()

                if (user != null) {
                    call.respond(HttpStatusCode.OK, UserRequest(
                        id = user.id,
                        email = user.email,
                        login = user.login,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        role = user.role
                    ))
                } else {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, Constants.Error.USER_NOT_FOUND))
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    BaseResponse(false, e.message ?: Constants.Error.GENERAL)
                )
            }
        }
    }
}