package com.vladbstrv.data.model.tables

import org.jetbrains.exposed.sql.Table

object TestTestTable: Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 30)
    override val primaryKey: PrimaryKey = PrimaryKey(AppointmentTable.id)
}