package com.vladbstrv.plugins

import com.vladbstrv.authentification.JwtService
import com.vladbstrv.data.repository.*
import com.vladbstrv.domain.repository.*
import com.vladbstrv.domain.usecase.*
import io.ktor.server.application.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            userModule,
            clientModule,
            serviceModule,
            workingDayModule,
            appointmentModule
        )
    }
}

val userModule = module {
    single { JwtService() }
    single<UserRepository> { UserRepositoryImpl() }
    singleOf(::UserUseCase)
}

val clientModule = module {
    single<ClientRepository> { ClientRepositoryImpl() }
    single { ClientUseCase(get()) }
}

val serviceModule = module {
    single<ServiceRepository> { ServiceRepositoryImpl() }
    single { ServiceUseCase(get()) }
}

val workingDayModule = module {
    single<WorkingDayRepository> { WorkingDayRepositoryImpl() }
    single { WorkingDayUseCase(get()) }
}

val appointmentModule = module {
    single<AppointmentRepository> { AppointmentRepositoryImpl() }
    single { AppointmentUseCase(get()) }
}