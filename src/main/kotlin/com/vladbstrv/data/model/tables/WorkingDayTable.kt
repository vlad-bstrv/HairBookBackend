package com.vladbstrv.data.model.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time

object WorkingDayTable: Table() {
    val id = integer("id").autoIncrement().uniqueIndex()
    val ownerId = integer("owner_id").references(UserTable.id)
    val date = date("date").uniqueIndex()
    val startTime = time("start_time")
    val endTime = time("end_time")

    override val primaryKey: PrimaryKey = PrimaryKey(ClientTable.id)
}