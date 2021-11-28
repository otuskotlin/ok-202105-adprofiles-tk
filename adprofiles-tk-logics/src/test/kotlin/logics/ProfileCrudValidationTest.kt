package logics

import context.CorStatus
import context.MpContext
import kotlinx.coroutines.runBlocking
import models.MpStubCase
import models.ProfileIdModel
import org.junit.Test
import kotlin.test.assertEquals

class ProfileCrudValidationTest {
    @Test
    fun `create success`() {
        val crud = ProfileCrud()
        val context = MpContext(
            stubCase = MpStubCase.SUCCESS,
            requestProfile = ProfileStub.getModel { id = ProfileIdModel("11111111-1111-1111-1111-111111111111") },
            operation = MpContext.MpOperations.CREATE,
        )
        runBlocking {
            crud.create(context)
        }
        println(context.status)
        println(context.errors)
        println(context.requestProfile)
        assertEquals(CorStatus.SUCCESS, context.status)

        assertEquals(CorStatus.SUCCESS, context.status)
        assertEquals(0, context.errors.size)
    }

    @Test
    fun `create failing`() {
        val crud = ProfileCrud()
        val context = MpContext(
            stubCase = MpStubCase.DATABASE_ERROR,
            requestProfile = ProfileStub.getModel { id = ProfileIdModel.NONE },
            operation = MpContext.MpOperations.CREATE,
        )
        runBlocking {
            crud.create(context)
        }

        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(1, context.errors.size)
    }

}
