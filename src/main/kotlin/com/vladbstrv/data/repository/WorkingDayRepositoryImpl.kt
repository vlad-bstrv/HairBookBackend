package com.vladbstrv.data.repository

import com.vladbstrv.data.model.WorkingDayModel
import com.vladbstrv.data.model.tables.WorkingDayTable
import com.vladbstrv.domain.repository.WorkingDayRepository
import com.vladbstrv.plugins.DatabaseFactory.dbQuery
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class WorkingDayRepositoryImpl : WorkingDayRepository {
    override suspend fun insertWorkingDay(workingDayModel: WorkingDayModel): WorkingDayModel {
        val result = dbQuery {
            WorkingDayTable.insert { table ->
                table[ownerId] = workingDayModel.ownerId
                table[date] = workingDayModel.date.toJavaLocalDate()
                table[startTime] = workingDayModel.startTime.toJavaLocalTime()
                table[endTime] = workingDayModel.endTime.toJavaLocalTime()
            }
        }
        return rowToWorkingDay(result.resultedValues?.first()) ?: throw IllegalStateException()
    }

    override suspend fun getAllWorkingDays(ownerId: Int): List<WorkingDayModel> = dbQuery {
        WorkingDayTable.select {
            WorkingDayTable.ownerId.eq(ownerId)
        }.mapNotNull {
            rowToWorkingDay(it)
        }
    }

    override suspend fun getWorkingDayById(id: Int, ownerId: Int): WorkingDayModel? = dbQuery {
        WorkingDayTable.select {
            WorkingDayTable.ownerId.eq(ownerId) and WorkingDayTable.id.eq(id)
        }
            .mapNotNull {
                rowToWorkingDay(it)
            }
            .singleOrNull()
    }

    override suspend fun updateWorkingDay(workingDayModel: WorkingDayModel, ownerId: Int): Boolean {
        val result = dbQuery {
            WorkingDayTable.update(
                where = {
                    WorkingDayTable.ownerId.eq(ownerId) and WorkingDayTable.id.eq(workingDayModel.id)
                }
            ) { table ->
                table[date] = workingDayModel.date.toJavaLocalDate()
                table[startTime] = workingDayModel.startTime.toJavaLocalTime()
                table[endTime] = workingDayModel.endTime.toJavaLocalTime()
            }
        }
        return result > 0
    }

    override suspend fun deleteWorkingDay(id: Int, ownerId: Int): Boolean {
        val result = dbQuery {
            WorkingDayTable.deleteWhere {
                this.id.eq(id) and this.ownerId.eq(ownerId)
            }
        }
        return result > 0
    }

    private fun rowToWorkingDay(resultRow: ResultRow?): WorkingDayModel? {
        if (resultRow == null) {
            return null
        }

        return WorkingDayModel(
            id = resultRow[WorkingDayTable.id],
            ownerId = resultRow[WorkingDayTable.ownerId],
            date = resultRow[WorkingDayTable.date].toKotlinLocalDate(),
            startTime = resultRow[WorkingDayTable.startTime].toKotlinLocalTime(),
            endTime = resultRow[WorkingDayTable.endTime].toKotlinLocalTime()
        )
    }
}