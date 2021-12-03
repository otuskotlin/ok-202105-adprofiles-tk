package ru.repo

import models.ProfileIdModel
import models.ProfileModel
import models.ProfileRoleModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement

object ProfilesTable : Table("Profiles") {
    val id = uuid("id").autoGenerate().uniqueIndex()
    val firsName = varchar("firsName", 128)
    val secondName = varchar("secondName", 512)
    val role = enumeration("role", ProfileRoleModel::class)

    override val primaryKey = PrimaryKey(id)

    // Mapper functions from sql-like table to ProfileModel
    fun from(res: InsertStatement<Number>) = ProfileModel(
        id = ProfileIdModel(res[id]),
        firsName = res[firsName],
        secondName = res[secondName],
        role = res[role]
    )

    fun from(res: ResultRow) = ProfileModel(
        id = ProfileIdModel(res[id]),
        firsName = res[firsName],
        secondName = res[secondName],
        role = res[role]
    )
}
