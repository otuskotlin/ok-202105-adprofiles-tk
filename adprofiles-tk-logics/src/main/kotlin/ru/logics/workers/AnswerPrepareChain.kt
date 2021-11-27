package logics.workers


import cor.ICorChainDsl
import cor.handlers.chain
import cor.handlers.worker
import context.CorStatus
import context.MpContext
import models.ProfileModel

internal fun ICorChainDsl<MpContext>.answerPrepareChain(title: String) = chain{
    this.title = title
    description = "Чейн считается успешным, если в нем не было ошибок и он отработал"
    worker {
        this.title = "Обработчик успешного чейна"
        on { status in setOf(CorStatus.RUNNING, CorStatus.FINISHING) }
        handle {
            status = CorStatus.SUCCESS
        }
    }
    worker {
        this.title = "Обработчик неуспешного чейна"
        on { status != CorStatus.SUCCESS }
        handle {
            status = CorStatus.ERROR
            responseProfile = ProfileModel()
            responseProfiles = mutableListOf()
        }
    }
}
