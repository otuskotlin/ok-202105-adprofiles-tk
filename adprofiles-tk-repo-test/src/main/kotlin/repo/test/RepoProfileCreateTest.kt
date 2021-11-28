package repo.test

import kotlinx.coroutines.runBlocking
import models.ProfileIdModel
import models.ProfileModel
import models.ProfileRoleModel
import org.junit.Test
import repo.DbProfileModelRequest
import repo.IRepoProfile
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


abstract class RepoProfileCreateTest {
    abstract val repo: IRepoProfile

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.create(DbProfileModelRequest(createObj)) }
        val expected = createObj.copy(id = result.result?.id ?: ProfileIdModel.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected, result.result)
        assertNotEquals(ProfileIdModel.NONE, result.result?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitProfiles() {

        private val createObj = ProfileModel(
            firsName = "create firsName",
            secondName = "create secondName",
            role = ProfileRoleModel.CUSTOMER
        )
        override val initObjects: List<ProfileModel> = emptyList()
    }
}
