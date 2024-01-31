package com.vladbstrv.domain.repository

import com.vladbstrv.data.model.ServiceModel

interface ServiceRepository {

    suspend fun getAllServices(userId: Int): List<ServiceModel>

    suspend fun getById(userId: Int, serviceId: Int): ServiceModel

    suspend fun insertService(service: ServiceModel)

    suspend fun updateService(service: ServiceModel, ownerId: Int): ServiceModel

    suspend fun deleteService(serviceId: Int, ownerId: Int): Boolean
}