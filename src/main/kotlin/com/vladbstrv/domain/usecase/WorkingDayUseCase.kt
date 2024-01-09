package com.vladbstrv.domain.usecase

import com.vladbstrv.data.model.WorkingDayModel
import com.vladbstrv.domain.repository.WorkingDayRepository

class WorkingDayUseCase(private val repository: WorkingDayRepository) {

    suspend fun insert(workingDayModel: WorkingDayModel) = repository.insertWorkingDay(workingDayModel)

    suspend fun getAll(ownerId: Int) = repository.getAllWorkingDays(ownerId)

    suspend fun getById(id: Int, ownerId: Int) = repository.getWorkingDayById(id, ownerId)

    suspend fun update(workingDayModel: WorkingDayModel) =
        repository.updateWorkingDay(workingDayModel)

    suspend fun delete(id: Int, ownerId: Int) = repository.deleteWorkingDay(id, ownerId)
}