package com.vladbstrv.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ClientTable: Table() {
    val id = integer("client_id").autoIncrement()
    val owner = integer("client_owner").references(UserTable.id)
    val firstName = varchar("first_name", 30)
    val lastName = varchar("last_name", 30)
    val phoneNumber = varchar("phone_number", 12).uniqueIndex()
    val comment = varchar("comment", 200)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}