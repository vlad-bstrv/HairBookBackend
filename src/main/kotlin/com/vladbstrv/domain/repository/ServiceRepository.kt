package com.vladbstrv.domain.repository

import com.vladbstrv.data.model.ServiceModel

interface ServiceRepository {

    suspend fun getAllServices(userId: Int): List<ServiceModel>

    suspend fun insertService(service: ServiceModel)
}