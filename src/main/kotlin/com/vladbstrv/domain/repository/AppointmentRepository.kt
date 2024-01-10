package com.vladbstrv.domain.repository

import com.vladbstrv.data.model.AppointmentModel

interface AppointmentRepository {

    suspend fun insert(appointmentModel: AppointmentModel): AppointmentModel

    suspend fun getById(id: Int): List<AppointmentModel>
}