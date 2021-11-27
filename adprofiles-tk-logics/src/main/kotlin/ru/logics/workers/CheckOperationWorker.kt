package logics.workers

import cor.ICorChainDsl
import cor.handlers.worker
import context.CorStatus
import context.MpContext
import exceptions.MpIllegalOperation

internal fun ICorChainDsl<MpContext>.checkOperationWorker(
    targetOperation: MpContext.MpOperations,
    title: String
) = worker{
    this.title = title
    description = "Если в контексте недопустимая операция, то чейн неуспешен"
    on {
        operation != targetOperation
    }
    handle {
        status = CorStatus.FAILING
        addError(
            e = MpIllegalOperation("Expected ${targetOperation.name} but was ${operation.name}")
        )
    }
}
