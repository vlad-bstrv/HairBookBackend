package com.vladbstrv.domain.usecase

import com.vladbstrv.data.model.ServiceModel
import com.vladbstrv.domain.repository.ServiceRepository

class ServiceUseCase(
    private val serviceRepository: ServiceRepository
) {

    suspend fun getAllServices(userId: Int) = serviceRepository.getAllServices(userId)

    suspend fun insertService(serviceModel: ServiceModel) = serviceRepository.insertService(serviceModel)

    suspend fun updateService(serviceModel: ServiceModel, ownerId: Int) = serviceRepository
        .updateService(serviceModel, ownerId)

    suspend fun deleteService(serviceId: Int, ownedId: Int): Boolean =
        serviceRepository.deleteService(serviceId, ownedId)

    suspend fun getServiceById(serviceId: Int, ownerId: Int): ServiceModel =
        serviceRepository.getById(serviceId = serviceId, userId = ownerId)
}