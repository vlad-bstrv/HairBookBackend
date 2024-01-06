package com.vladbstrv.domain.usecase

import com.vladbstrv.data.model.ServiceModel
import com.vladbstrv.domain.repository.ServiceRepository

class ServiceUseCase(
    private val serviceRepository: ServiceRepository
) {

    suspend fun getAllServices(userId: Int) = serviceRepository.getAllServices(userId)

    suspend fun insertService(serviceModel: ServiceModel) = serviceRepository.insertService(serviceModel)
}