package com.vladbstrv.data.repository

import com.vladbstrv.data.model.AppointmentModel
import com.vladbstrv.data.model.ServiceModel
import com.vladbstrv.data.model.tables.AppointmentServicesJunctionTable
import com.vladbstrv.data.model.tables.AppointmentTable
import com.vladbstrv.data.model.tables.ServiceTable
import com.vladbstrv.domain.repository.AppointmentRepository
import com.vladbstrv.plugins.DatabaseFactory.dbQuery
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime
import org.jetbrains.exposed.sql.*
import java.time.LocalTime

class AppointmentRepositoryImpl : AppointmentRepository {
    override suspend fun insert(appointmentModel: AppointmentModel): AppointmentModel {
        dbQuery {
            println("-------------Insert-------------$appointmentModel")
            AppointmentTable.insert { table ->
                table[owner] = appointmentModel.ownerId
                table[workingDayId] = appointmentModel.workingDayId
                table[startTime] = appointmentModel.startTime.toJavaLocalTime()
            }

            appointmentModel.servicesId.map { serviceId ->
                ServiceTable.select {
                    ServiceTable.id eq serviceId
                }.mapNotNull {
                    rowToService(it)
                }.single()
            }.also { service ->
                AppointmentServicesJunctionTable.batchInsert(service) {
                    this[AppointmentServicesJunctionTable.appointment] = appointmentModel.id
                    this[AppointmentServicesJunctionTable.service] = it.id
                }
            }
        }
        return appointmentModel
    }

    override suspend fun getById(id: Int): List<AppointmentModel> = dbQuery {
        AppointmentTable
            .join(AppointmentServicesJunctionTable, JoinType.INNER, additionalConstraint = {
                AppointmentTable.id eq AppointmentServicesJunctionTable.appointment
            })
            .join(ServiceTable, JoinType.INNER, additionalConstraint = {
                AppointmentServicesJunctionTable.service eq ServiceTable.id
            })
            .selectAll()
            .mapNotNull {
                AppointmentModel(
                    id = it[AppointmentTable.id].value,
                    ownerId = it[AppointmentTable.owner],
                    workingDayId = it[AppointmentTable.workingDayId],
                    servicesId = listOf(it[AppointmentServicesJunctionTable.service]),
                    startTime = it[AppointmentTable.startTime].toKotlinLocalTime()
                )
            }
    }

    suspend fun findAll(): List<AppointmentModel> {
        val dbQuery = dbQuery {
            AppointmentTable.join(ServiceTable, JoinType.INNER, additionalConstraint = {
                AppointmentTable.id eq ServiceTable.id
            })
                .selectAll()
                .limit(20)
                .map {
                    println(it)
                }
        }
        return listOf()
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

