package com.vladbstrv.domain.repository

import com.vladbstrv.data.model.ClientModel

interface ClientRepository {

    suspend fun addClient(client: ClientModel)

    suspend fun getAllClients(userId: Int): List<ClientModel>

    suspend fun getClientByPhoneNumber(phoneNumber: String, ownerId: Int): List<ClientModel>

    suspend fun updateClient(client: ClientModel, ownerId: Int)
    suspend fun deleteClient(clientId: Int, ownerId: Int)
}