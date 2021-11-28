package logics

import kotlinx.coroutines.runBlocking
import context.ContextConfig
import context.CorStatus
import context.MpContext
import models.ProfileIdModel
import models.WorkMode
import repo.inmemory.RepoProfileInMemory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class ProfileCrudRepoTest {

    @Test
    fun createSuccessTest() {
        val repo = RepoProfileInMemory()
        val crud = ProfileCrud(config = ContextConfig(
            repoTest = repo
        )
        )
        val context = MpContext(
            workMode = WorkMode.TEST,
            requestProfile = ProfileStub.getModel { id = ProfileIdModel.NONE },
            operation = MpContext.MpOperations.CREATE,
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = ProfileStub.getModel()
        with(context.responseProfile) {
            assertTrue { id.asString().isNotBlank() }
            assertEquals(expected.firsName, firsName)
            assertEquals(expected.secondName, secondName)
            assertEquals(expected.role, role)
        }
    }

    @Test
    fun readSuccessTest() {
        val repo = RepoProfileInMemory(
            initObjects = listOf(ProfileStub.getModel())
        )
        val crud = ProfileCrud(config = ContextConfig(repoTest = repo))
        val context = MpContext(
            workMode = WorkMode.TEST,
            requestProfileId = ProfileStub.getModel().id,
            operation = MpContext.MpOperations.READ,
        )
        runBlocking {
            crud.read(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = ProfileStub.getModel()
        with(context.responseProfile) {
            assertEquals(expected.id, id)
            assertEquals(expected.firsName, firsName)
            assertEquals(expected.secondName, secondName)
            assertEquals(expected.role, role)
        }
    }

    @Test
    fun updateSuccessTest() {
        val repo = RepoProfileInMemory(
            initObjects = listOf(ProfileStub.getModel())
        )
        val crud = ProfileCrud(config = ContextConfig(repoTest = repo))
        val context = MpContext(
            workMode = WorkMode.TEST,
            requestProfile = ProfileStub.getModel(),
            operation = MpContext.MpOperations.UPDATE,
        )
        runBlocking {
            crud.update(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = ProfileStub.getModel()
        with(context.responseProfile) {
            assertEquals(expected.id, id)
            assertEquals(expected.firsName, firsName)
            assertEquals(expected.secondName, secondName)
            assertEquals(expected.role, role)
        }
    }

    @Test
    fun deleteSuccessTest() {
        val repo = RepoProfileInMemory(
            initObjects = listOf(ProfileStub.getModel())
        )
        val crud = ProfileCrud(config = ContextConfig(repoTest = repo))
        val context = MpContext(
            workMode = WorkMode.TEST,
            requestProfileId = ProfileStub.getModel().id,
            operation = MpContext.MpOperations.DELETE,
        )
        runBlocking {
            crud.delete(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = ProfileStub.getModel()
        with(context.responseProfile) {
            assertEquals(expected.id, id)
            assertEquals(expected.firsName, firsName)
            assertEquals(expected.secondName, secondName)
            assertEquals(expected.role, role)
        }
    }


}
