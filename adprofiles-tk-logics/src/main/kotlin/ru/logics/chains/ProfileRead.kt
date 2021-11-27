package logics.chains


import cor.ICorExec
import cor.chain
import context.MpContext
import logics.chains.helpers.mpValidation
import logics.chains.stubs.adReadStub
import logics.workers.*
import logics.workers.answerPrepareChain
import logics.workers.chainInitWorker
import logics.workers.checkOperationWorker
import logics.workers.chooseDb
import validation.validators.ValidatorStringNonEmpty

object ProfileRead: ICorExec<MpContext> by chain<MpContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = MpContext.MpOperations.READ,
    )
    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    adReadStub(title = "Обработка стабкейса для READ")

    mpValidation {
        validate<String?> {
            on { requestProfileId.asString() }
            validator(ValidatorStringNonEmpty(field = "id"))
        }
    }

    repoRead(title = "Чтение объекта из БД")

    answerPrepareChain(title = "Подготовка ответа")
}).build()
