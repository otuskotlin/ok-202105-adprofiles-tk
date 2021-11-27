package logics.chains.stubs

import ProfileStub
import cor.ICorChainDsl
import cor.handlers.chain
import cor.handlers.worker
import context.CorStatus
import context.MpContext
import exceptions.MpStubCaseNotFound
import models.MpStubCase

internal fun ICorChainDsl<MpContext>.adCreateStub(title: String) = chain{
    this.title = title

    on { status == CorStatus.RUNNING && stubCase != MpStubCase.NONE }

    worker {
        this.title = "Успешный стабкейс для CREATE"
        on { stubCase == MpStubCase.SUCCESS }
        handle {
            responseProfile = requestProfile.copy(id = ProfileStub.getModel().id, role = ProfileStub.getModel().role)
            status = CorStatus.FINISHING
        }
    }

    worker {
        this.title = "Обработка отсутствия подходящего стабкейса"
        on { status == CorStatus.RUNNING }
        handle {
            status = CorStatus.FAILING
            addError(
                e = MpStubCaseNotFound(stubCase.name),
            )
        }
    }

}
