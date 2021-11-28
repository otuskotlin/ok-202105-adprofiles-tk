package logics

import kotlinx.coroutines.runBlocking
import context.CorStatus
import context.MpContext
import models.MpStubCase
import models.ProfileIdModel
import kotlin.test.Test
import kotlin.test.assertEquals


class ProfileCrudTest {

    @Test
    fun createSuccessTest() {
        val crud = ProfileCrud()
        val context = MpContext(
            stubCase = MpStubCase.SUCCESS,
            requestProfile = ProfileStub.getModel { id = ProfileIdModel.NONE },
            operation = MpContext.MpOperations.CREATE,
        )
        runBlocking {
            crud.create(context)
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
    fun readSuccessTest() {
        val crud = ProfileCrud()
        val context = MpContext(
            stubCase = MpStubCase.SUCCESS,
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
        val crud = ProfileCrud()
        val context = MpContext(
            stubCase = MpStubCase.SUCCESS,
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
        val crud = ProfileCrud()
        val context = MpContext(
            stubCase = MpStubCase.SUCCESS,
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
