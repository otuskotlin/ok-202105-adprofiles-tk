package repo.test

import kotlinx.coroutines.runBlocking
import models.CommonErrorModel
import models.ProfileIdModel
import models.ProfileModel
import models.ProfileRoleModel
import org.junit.Test
import repo.DbProfileModelRequest
import repo.IRepoProfile
import java.util.*
import kotlin.test.assertEquals


abstract class RepoProfileUpdateTest {
    abstract val repo: IRepoProfile

    @Test
    fun updateSuccess() {
        val result = runBlocking { repo.update(DbProfileModelRequest(updateObj)) }
        assertEquals(true, result.isSuccess)
        assertEquals(updateObj, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() {
        val result = runBlocking { repo.update(DbProfileModelRequest(updateObjNotFound)) }
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(listOf(CommonErrorModel(field = "id", message = "Not Found")), result.errors)
    }

    companion object: BaseInitProfiles() {
        override val initObjects: List<ProfileModel> = listOf(
            createInitTestModel("update")
        )
        private val updateId = initObjects.first().id
        private val updateIdNotFound = ProfileIdModel(UUID.randomUUID())

        private val updateObj = ProfileModel(
            id = updateId,
            firsName = "update firsName",
            secondName = "update secondName",
            role = ProfileRoleModel.CUSTOMER
        )

        private val updateObjNotFound = ProfileModel(
            id = updateIdNotFound,
            firsName = "update object not found firsName",
            secondName = "update object not found secondName",
            role = ProfileRoleModel.CUSTOMER
        )
    }
}
