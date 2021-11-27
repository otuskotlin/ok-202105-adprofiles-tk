package logics.workers

import cor.ICorChainDsl
import cor.handlers.worker
import context.CorStatus
import context.MpContext
import repo.DbProfileModelRequest

internal fun ICorChainDsl<MpContext>.repoUpdate(title: String) = worker {
    this.title = title
    description = "Data from request updates the DB Repository object"

    on { status == CorStatus.RUNNING }

    handle {
        val result = profileRepo.update(DbProfileModelRequest(profile = dbProfile))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            responseProfile =  resultValue
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}
