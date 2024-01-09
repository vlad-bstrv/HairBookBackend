package com.vladbstrv.domain.repository

import com.vladbstrv.data.model.WorkingDayModel

interface WorkingDayRepository {

    suspend fun insertWorkingDay(workingDayModel: WorkingDayModel): WorkingDayModel

    suspend fun getAllWorkingDays(ownerId: Int): List<WorkingDayModel>

    suspend fun getWorkingDayById(id: Int, ownerId: Int): WorkingDayModel?

    suspend fun updateWorkingDay(workingDayModel: WorkingDayModel): Boolean

    suspend fun deleteWorkingDay(id: Int, ownerId: Int): Boolean
}