package ru.repo

import kotlinx.coroutines.runBlocking
import models.CommonErrorModel
import models.ProfileIdModel
import models.ProfileModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import repo.DbProfileIdRequest
import repo.DbProfileModelRequest
import repo.DbProfileResponse
import repo.IRepoProfile
import java.sql.SQLException

class RepoProfileSQL(
    url: String = "jdbc:postgresql://localhost:5432/marketplacedevdb",
    user: String = "postgres",
    password: String = "admin",
    schema: String = "marketplace",
    initObjects: Collection<ProfileModel> = emptyList()
) : IRepoProfile {
    private val db by lazy { SqlConnector(url, user, password, schema).connect(ProfilesTable) }

    init {
        runBlocking {
            initObjects.forEach {
                save(it)
            }
        }
    }

    private suspend fun save(item: ProfileModel): DbProfileResponse {
        return safeTransaction({

            val res = ProfilesTable.insert {
                if (item.id != ProfileIdModel.NONE) {
                    it[id] = item.id.asUUID()
                }
                it[firsName] = item.firsName
                it[secondName] = item.secondName
                it[role] = item.role
            }

            DbProfileResponse(ProfilesTable.from(res), true)
        }, {
            DbProfileResponse(
                result = null,
                isSuccess = false,
                errors = listOf(CommonErrorModel(message = localizedMessage))
            )
        })
    }

    override suspend fun create(req: DbProfileModelRequest): DbProfileResponse {
        return save(req.profile)
    }

    override suspend fun read(req: DbProfileIdRequest): DbProfileResponse {
        return safeTransaction({
            val result = (ProfilesTable).select { ProfilesTable.id.eq(req.id.asUUID()) }.single()

            DbProfileResponse(ProfilesTable.from(result), true)
        }, {
            val err = when (this) {
                is NoSuchElementException -> CommonErrorModel(field = "id", message = "Not Found")
                is IllegalArgumentException -> CommonErrorModel(message = "More than one element with the same id")
                else -> CommonErrorModel(message = localizedMessage)
            }
            DbProfileResponse(result = null, isSuccess = false, errors = listOf(err))
        })
    }

    override suspend fun update(req: DbProfileModelRequest): DbProfileResponse {
        val item = req.profile
        return safeTransaction({
            ProfilesTable.update({ ProfilesTable.id.eq(item.id.asUUID()) }) {
                it[firsName] = item.firsName
                it[secondName] = item.secondName
                it[role] = item.role
            }
            val result = ProfilesTable.select { ProfilesTable.id.eq(item.id.asUUID()) }.single()

            DbProfileResponse(result = ProfilesTable.from(result), isSuccess = true)
        }, {
            DbProfileResponse(
                result = null,
                isSuccess = false,
                errors = listOf(CommonErrorModel(field = "id", message = "Not Found"))
            )
        })
    }

    override suspend fun delete(req: DbProfileIdRequest): DbProfileResponse {
        return safeTransaction({
            val result = ProfilesTable.select { ProfilesTable.id.eq(req.id.asUUID()) }.single()
            ProfilesTable.deleteWhere { ProfilesTable.id eq req.id.asUUID() }

            DbProfileResponse(result = ProfilesTable.from(result), isSuccess = true)
        }, {
            DbProfileResponse(
                result = null,
                isSuccess = false,
                errors = listOf(CommonErrorModel(field = "id", message = "Not Found"))
            )
        })
    }

    /**
     * Transaction wrapper to safely handle caught exception and throw all sql-like exceptions. Also remove lot's of duplication code
     */
    private fun <T> safeTransaction(statement: Transaction.() -> T, handleException: Throwable.() -> T): T {
        return try {
            transaction(db, statement)
        } catch (e: SQLException) {
            throw e
        } catch (e: Throwable) {
            return handleException(e)
        }
    }
}
