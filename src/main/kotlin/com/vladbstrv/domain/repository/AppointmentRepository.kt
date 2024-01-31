package com.vladbstrv.domain.repository

import com.vladbstrv.data.model.AppointmentModel

interface AppointmentRepository {

    suspend fun insert(appointmentModel: AppointmentModel): AppointmentModel

    suspend fun getAll(ownerId: Int): List<AppointmentModel>

    suspend fun getById(id: Int, ownerId: Int): AppointmentModel

    suspend fun update(appointmentModel: AppointmentModel, ownerId: Int): AppointmentModel
    suspend fun delete(id: Int, ownerId: Int): Boolean

}