package com.vladbstrv.data.model.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.time

object ServiceTable: Table() {
    val id = integer("service_id").autoIncrement()
    val owner = integer("client_owner").references(UserTable.id)
    val name = varchar("name", 30)
    val price = double("price")
    val time = time("time")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}