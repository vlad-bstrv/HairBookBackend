package com.vladbstrv.domain.usecase

import com.vladbstrv.data.model.ClientModel
import com.vladbstrv.domain.repository.ClientRepository

class ClientUseCase(
    private val clientRepository: ClientRepository
) {

    suspend fun addClient(client: ClientModel) = clientRepository.addClient(client)

    suspend fun getAllClients(userId: Int) = clientRepository.getAllClients(userId)

    suspend fun getClientByPhoneNumber(phoneNumber: String, ownerId: Int) = clientRepository
        .getClientByPhoneNumber(phoneNumber, ownerId)

    suspend fun updateClient(client: ClientModel, ownerId: Int) = clientRepository.updateClient(client, ownerId)

    suspend fun deleteClient(clientId: Int, ownerId: Int) = clientRepository.deleteClient(clientId, ownerId)
}
