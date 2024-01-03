package com.vladbstrv.data.repository

import com.vladbstrv.data.model.UserModel
import com.vladbstrv.data.model.getRoleByString
import com.vladbstrv.data.model.getStringByRole
import com.vladbstrv.data.model.tables.UserTable
import com.vladbstrv.domain.repository.UserRepository
import com.vladbstrv.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserRepositoryImpl : UserRepository {

    override suspend fun getUserByEmail(email: String): UserModel? = dbQuery {
        UserTable.select { UserTable.email.eq(email) }
            .map { rowToUser(row = it) }
            .singleOrNull()
    }

    override suspend fun insertUser(user: UserModel) {
        dbQuery {
            UserTable.insert { table ->
                table[email] = user.email
                table[login] = user.login
                table[password] = user.password
                table[firstName] = user.firstName
                table[lastName] = user.lastName
                table[isActive] = user.isActive
                table[role] = user.role.getStringByRole()
            }
        }
    }

    private fun rowToUser(row: ResultRow?): UserModel? {
        if (row == null) return null

        return UserModel(
            id = row[UserTable.id],
            email = row[UserTable.email],
            login = row[UserTable.login],
            password = row[UserTable.password],
            firstName = row[UserTable.firstName],
            lastName = row[UserTable.lastName],
            isActive = row[UserTable.isActive],
            role = row[UserTable.role].getRoleByString()
        )
    }
}