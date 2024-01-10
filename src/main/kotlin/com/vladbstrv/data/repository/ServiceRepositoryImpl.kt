package com.vladbstrv.data.repository

import com.vladbstrv.data.model.ServiceModel
import com.vladbstrv.data.model.tables.ServiceTable
import com.vladbstrv.domain.repository.ServiceRepository
import com.vladbstrv.plugins.DatabaseFactory.dbQuery
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalTime

class ServiceRepositoryImpl : ServiceRepository {
    override suspend fun getAllServices(userId: Int): List<ServiceModel> =
        dbQuery {
            ServiceTable.select {
                ServiceTable.owner.eq(userId)
            }
                .mapNotNull {
                    rowToService(it)
                }
        }


    override suspend fun insertService(service: ServiceModel)  {
        dbQuery{
            ServiceTable.insert { table ->
                table[owner] = service.owner
                table[name] = service.name
                table[price] = service.price
                table[time] = service.time.toJavaLocalTime()
            }
        }
    }

    override suspend fun updateService(service: ServiceModel, ownerId: Int): ServiceModel {
        dbQuery {
            ServiceTable.update (
                where = {
                    ServiceTable.owner.eq(ownerId) and ServiceTable.id.eq(service.id)
                }
            ) {table ->
                table[owner] = service.owner
                table[name] = service.name
                table[price] = service.price
                table[time] = service.time.toJavaLocalTime()

            }
        }

        return getServiceById(service.id) ?: throw IllegalStateException()
    }

    override suspend fun deleteService(serviceId: Int, ownerId: Int): Boolean {
        val result = dbQuery {
            ServiceTable.deleteWhere {
                id.eq(serviceId) and owner.eq(ownerId)
            }
        }

        return result > 0
    }

    private suspend fun getServiceById(serviceId: Int): ServiceModel? =
        dbQuery {
            ServiceTable.select {
                ServiceTable.id.eq(serviceId)
            }
                .mapNotNull { rowToService(it) }
                .singleOrNull()
        }


    private fun rowToService(row: ResultRow?): ServiceModel? {
        if (row == null) return null

        val time = row.toString().substringAfter("time=").dropLast(3)

        val serviceModelTimeString = ServiceModel(
            id = row[ServiceTable.id],
            owner = row[ServiceTable.owner],
            name = row[ServiceTable.name],
            price = row[ServiceTable.price],
            time = LocalTime.parse(time).toKotlinLocalTime(),
        )
        return serviceModelTimeString
    }
}

