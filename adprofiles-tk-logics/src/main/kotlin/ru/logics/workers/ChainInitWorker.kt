package logics.workers


import cor.ICorChainDsl
import cor.handlers.worker
import context.CorStatus
import context.MpContext

internal fun ICorChainDsl<MpContext>.chainInitWorker(title: String) = worker {
        this.title = title
        description = "При старте обработки цепочки, статус еще не установлен. Проверяем его"

        on {
            status == CorStatus.NONE
        }
        handle {
            status = CorStatus.RUNNING
        }
}
