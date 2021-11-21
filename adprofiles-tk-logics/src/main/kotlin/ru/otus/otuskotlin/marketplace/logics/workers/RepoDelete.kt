package ru.otus.otuskotlin.marketplace.logics.workers

import context.MpContext
import ru.otus.otuskotlin.marketplace.common.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.common.cor.handlers.worker

internal fun ICorChainDsl<MpContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Data from request are stored in the DB Repository"

    on { operation == MpContext.MpOperations.DELETE }

    handle {
        adRepo.delete(ad)
    }
}
