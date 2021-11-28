package logics.workers

import cor.ICorChainDsl
import cor.handlers.worker
import context.CorStatus
import context.MpContext
import repo.DbProfileIdRequest


internal fun ICorChainDsl<MpContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Data from request are stored in the DB Repository"

    on { status == CorStatus.RUNNING }

    handle {
        val result = profileRepo.read(DbProfileIdRequest(id = requestProfileId))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            dbProfile = resultValue
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}
