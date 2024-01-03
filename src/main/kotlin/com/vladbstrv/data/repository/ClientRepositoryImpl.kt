package com.vladbstrv.data.repository

import com.vladbstrv.data.model.ClientModel
import com.vladbstrv.data.model.tables.ClientTable
import com.vladbstrv.domain.repository.ClientRepository
import com.vladbstrv.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ClientRepositoryImpl : ClientRepository {

    override suspend fun addClient(client: ClientModel) {
        dbQuery {
            ClientTable.insert { table ->
                table[owner] = client.owner
                table[firstName] = client.firstName
                table[lastName] = client.lastName
                table[phoneNumber] = client.phoneNumber
                table[comment] = client.comment
            }
        }
    }

    override suspend fun getAllClients(): List<ClientModel> = dbQuery {
        ClientTable.selectAll()
            .mapNotNull { rowToClient(it) }
    }

    override suspend fun getClientByPhoneNumber(phoneNumber: String, ownerId: Int) = dbQuery {
        ClientTable.select {
            (ClientTable.owner.eq(ownerId) and ClientTable.phoneNumber.eq(phoneNumber))
        }
            .map { rowToClient(it) }
            .singleOrNull()
    }


    override suspend fun updateClient(client: ClientModel, ownerId: Int) {
        dbQuery {
            ClientTable.update(
                where = {
                    ClientTable.owner.eq(ownerId) and ClientTable.id.eq(client.id)
                }
            ) { table ->
                table[owner] = ownerId
                table[firstName] = client.firstName
                table[lastName] = client.lastName
                table[phoneNumber] = client.phoneNumber
                table[comment] = client.comment

            }
        }
    }

    override suspend fun deleteClient(clientId: Int, ownerId: Int) {
        dbQuery {
            ClientTable.deleteWhere {
                ClientTable.id.eq(clientId) and ClientTable.owner.eq(ownerId)
            }
        }
    }

    private fun rowToClient(row: ResultRow?): ClientModel? {
        if (row == null) return null

        return ClientModel(
            id = row[ClientTable.id],
            owner = row[ClientTable.owner],
            firstName = row[ClientTable.firstName],
            lastName = row[ClientTable.lastName],
            phoneNumber = row[ClientTable.phoneNumber],
            comment = row[ClientTable.comment],
        )
    }
}