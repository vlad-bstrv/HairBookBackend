package com.vladbstrv.data.model.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.time

object AppointmentTable: IntIdTable() {
    val owner = integer("client_owner").references(UserTable.id)
    val workingDayId = integer("working_day_id_app").references(WorkingDayTable.id)
    val startTime = time("start_time")
}
object AppointmentServicesJunctionTable: Table() {
    val appointment = reference("appointment", AppointmentTable.id)
    val service = reference("service", ServiceTable.id)

    override val primaryKey = PrimaryKey(appointment, service, name = "PK_AppointmentServices")
}