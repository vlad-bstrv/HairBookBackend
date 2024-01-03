package com.vladbstrv.data.model.tables

import com.vladbstrv.data.model.tables.UserTable.autoIncrement
import com.vladbstrv.plugins.UserService.Users.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object AppointmentTable: Table() {
    val id = integer("id").autoIncrement()
    val owner= integer("appointment_owner").references(UserTable.id)
    val client = integer("client_id").references(ClientTable.id)
    val date = varchar("create_date", 50)
    val comment = varchar("comment", 200).nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}