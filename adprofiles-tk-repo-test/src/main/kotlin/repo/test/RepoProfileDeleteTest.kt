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


abstract class RepoProfileDeleteTest {
    abstract val repo: IRepoProfile

    @Test
    fun deleteSuccess() {
        val result = runBlocking { repo.delete(DbProfileIdRequest(successId)) }

        assertEquals(true, result.isSuccess)
        assertEquals(deleteSuccessStub, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteNotFound() {
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
            createInitTestModel("delete")
        )
        private val deleteSuccessStub = initObjects.first()
        val successId = deleteSuccessStub.id
        val notFoundId = ProfileIdModel(UUID.randomUUID())
    }
}
