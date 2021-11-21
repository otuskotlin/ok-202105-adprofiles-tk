package ru.otus.otuskotlin.marketplace.logics

import context.MpContext
import kotlinx.coroutines.runBlocking
import model.Ad
import ru.otus.otuskotlin.marketplace.backend.repo.inmemory.RepoAdInMemory
import kotlin.test.Test
import kotlin.test.assertEquals

class AdCrudTest {
    val crud = AdCrud()
    @Test
    fun createTest() {

        val context = MpContext(
            ad = Ad(id = "id", title = "create"),
            operation = MpContext.MpOperations.CREATE,
            adRepo = RepoAdInMemory()
        )
        val resp = runBlocking {
            crud.create(context)
        } as Ad
        val expected = Ad(id = "id", title = "create")
        assertEquals(expected, resp)
    }
}
