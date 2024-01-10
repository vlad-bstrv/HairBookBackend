package com.vladbstrv.domain.usecase

import com.vladbstrv.data.model.AppointmentModel
import com.vladbstrv.domain.repository.AppointmentRepository

class AppointmentUseCase(private val repository: AppointmentRepository) {

    suspend fun insert(appointmentModel: AppointmentModel) = repository.insert(appointmentModel)

    suspend fun getById(id: Int) = repository.getById(id)
}