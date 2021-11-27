package repo.test

import kotlinx.coroutines.runBlocking
import models.CommonErrorModel
import models.ProfileIdModel
import models.ProfileModel
import org.junit.Test
import repo.DbProfileIdRequest
import repo.IRepoProfile
import java.util.*
import kotlin.test.assertEquals


abstract class RepoProfileReadTest {
    abstract val repo: IRepoProfile

    @Test
    fun readSuccess() {
        val result = runBlocking { repo.read(DbProfileIdRequest(successId)) }

        assertEquals(true, result.isSuccess)
        assertEquals(readSuccessStub, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() {
        val result = runBlocking { repo.read(DbProfileIdRequest(notFoundId)) }

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(
            listOf(CommonErrorModel(field = "id", message = "Not Found")),
            result.errors
        )
    }

    companion object: BaseInitProfiles() {
        override val initObjects: List<ProfileModel> = listOf(
            createInitTestModel("read")
        )
        private val readSuccessStub = initObjects.first()

        val successId = readSuccessStub.id
        val notFoundId = ProfileIdModel(UUID.randomUUID())

    }
}
