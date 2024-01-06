package com.vladbstrv.data.model.tables

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 100).uniqueIndex()
    val login = varchar("login", 50).uniqueIndex()
    val password = varchar("password", 50)
    val firstName = varchar("first_name", 30)
    val lastName = varchar("last_name", 30)
    val role = varchar("user_role", 20)
    val isActive = bool("is_active")

    override val primaryKey: PrimaryKey = PrimaryKey(id)

}