package com.vladbstrv.data.repository

import com.vladbstrv.data.model.AppointmentModel
import com.vladbstrv.data.model.tables.AppointmentServicesJunctionTable
import com.vladbstrv.data.model.tables.AppointmentTable
import com.vladbstrv.data.model.tables.ServiceTable
import com.vladbstrv.domain.repository.AppointmentRepository
import com.vladbstrv.plugins.DatabaseFactory.dbQuery
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class AppointmentRepositoryImpl : AppointmentRepository {
    override suspend fun insert(appointmentModel: AppointmentModel): AppointmentModel {
        dbQuery {
            val id = AppointmentTable.insertAndGetId { table ->
                table[owner] = appointmentModel.ownerId
                table[workingDayId] = appointmentModel.workingDayId
                table[startTime] = appointmentModel.startTime.toJavaLocalTime()
            }

            appointmentModel.servicesId.forEach {
                AppointmentServicesJunctionTable.insert { table ->
                    table[appointment] = id.value
                    table[service] = it
                }
            }
        }
        return appointmentModel
    }

    override suspend fun getAll(ownerId: Int): List<AppointmentModel> = dbQuery {
        val result = AppointmentTable
            .join(AppointmentServicesJunctionTable, JoinType.INNER, additionalConstraint = {
                AppointmentTable.id eq AppointmentServicesJunctionTable.appointment
            })
            .join(ServiceTable, JoinType.INNER, additionalConstraint = {
                AppointmentServicesJunctionTable.service eq ServiceTable.id
            })
            .select {
                AppointmentTable.owner eq ownerId
            }
            .mapNotNull {
                rowToAppointment(it)
            }
        groupAppointment(result)
    }

    override suspend fun getById(id: Int, ownerId: Int) = dbQuery {
        val result = AppointmentTable
            .join(AppointmentServicesJunctionTable, JoinType.INNER, additionalConstraint = {
                AppointmentTable.id eq AppointmentServicesJunctionTable.appointment
            })
            .join(ServiceTable, JoinType.INNER, additionalConstraint = {
                AppointmentServicesJunctionTable.service eq ServiceTable.id
            })
            .select {
                (AppointmentTable.owner eq ownerId) and (AppointmentTable.id eq id)
            }
            .mapNotNull {
                rowToAppointment(it)
            }
        println("result ${groupAppointment(result).first()}")
        groupAppointment(result).first()

    }

    override suspend fun update(appointmentModel: AppointmentModel, ownerId: Int): AppointmentModel {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int, ownerId: Int): Boolean {
        val result = dbQuery {
            AppointmentServicesJunctionTable.deleteWhere { appointment eq id }

            AppointmentTable
                .deleteWhere {
                    owner.eq(ownerId) and AppointmentTable.id.eq(id)
                }
        }
        return result > 0
    }

    private fun rowToAppointment(row: ResultRow?): AppointmentModel? {
        if (row == null) return null

        val appointment = AppointmentModel(
            id = row[AppointmentTable.id].value,
            ownerId = row[AppointmentTable.owner],
            workingDayId = row[AppointmentTable.workingDayId],
            servicesId = mutableListOf(row[AppointmentServicesJunctionTable.service]),
            startTime = row[AppointmentTable.startTime].toKotlinLocalTime()
        )
        return appointment
    }

    private fun groupAppointment(list: List<AppointmentModel>): List<AppointmentModel> {
        val newList = mutableListOf<AppointmentModel>()
        list.forEach { oldModel ->
            if (newList.none { it.id == oldModel.id }) newList.add(oldModel)

            for (model in newList) {
                if (model.id == oldModel.id) {
                    model.servicesId.addAll(oldModel.servicesId)
                    val distinct = model.servicesId.distinct()
                    model.servicesId.clear()
                    model.servicesId.addAll(distinct)
                }
            }
        }
        return newList
    }
}

