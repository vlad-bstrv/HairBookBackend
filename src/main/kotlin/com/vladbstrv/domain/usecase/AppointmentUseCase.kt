package com.vladbstrv.domain.usecase

import com.vladbstrv.data.model.AppointmentModel
import com.vladbstrv.domain.repository.AppointmentRepository

class AppointmentUseCase(private val repository: AppointmentRepository) {

    suspend fun insert(appointmentModel: AppointmentModel) = repository.insert(appointmentModel)

    suspend fun getAll(ownerId: Int) = repository.getAll(ownerId)

    suspend fun getById(id: Int, ownerId: Int) = repository.getById(id, ownerId)

    suspend fun delete(id: Int, ownerId: Int) = repository.delete(id, ownerId)

}