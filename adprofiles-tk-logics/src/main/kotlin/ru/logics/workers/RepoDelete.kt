package logics.workers


import cor.ICorChainDsl
import cor.handlers.worker
import context.CorStatus
import context.MpContext
import repo.DbProfileIdRequest

internal fun ICorChainDsl<MpContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "The requested object will be deleted from the DB Repository"

    on { status == CorStatus.RUNNING }

    handle {
        val result = profileRepo.delete(DbProfileIdRequest(id = dbProfile.id))
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
