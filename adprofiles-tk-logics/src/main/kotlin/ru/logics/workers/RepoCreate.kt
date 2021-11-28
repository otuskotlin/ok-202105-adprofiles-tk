package logics.workers


import cor.ICorChainDsl
import cor.handlers.worker
import context.CorStatus
import context.MpContext
import repo.DbProfileModelRequest

internal fun ICorChainDsl<MpContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Data from request are stored in the DB Repository"

    on { status == CorStatus.RUNNING }

    handle {
        val result = profileRepo.create(DbProfileModelRequest(profile = dbProfile))
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
