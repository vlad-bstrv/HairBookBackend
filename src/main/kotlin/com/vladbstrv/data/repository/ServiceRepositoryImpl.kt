package com.vladbstrv.data.repository

import com.vladbstrv.data.model.ServiceModel
import com.vladbstrv.data.model.tables.ServiceTable
import com.vladbstrv.domain.repository.ServiceRepository
import com.vladbstrv.plugins.DatabaseFactory.dbQuery
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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


    override suspend fun insertService(service: ServiceModel) {
        dbQuery{
            ServiceTable.insert { table ->
                table[owner] = service.owner
                table[name] = service.name
                table[price] = service.price
                table[time] = service.time.toJavaLocalTime()
            }
        }
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

