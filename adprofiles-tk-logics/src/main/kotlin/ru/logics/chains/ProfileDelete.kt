package logics.chains

import cor.ICorExec
import cor.chain
import context.MpContext
import logics.chains.helpers.mpValidation
import logics.chains.stubs.adDeleteStub
import logics.workers.*
import validation.validators.ValidatorStringNonEmpty

object ProfileDelete: ICorExec<MpContext> by chain<MpContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = MpContext.MpOperations.DELETE,
    )
    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    adDeleteStub(title = "Обработка стабкейса для DELETE")

    mpValidation {
        validate<String?> {
            on { requestProfileId.asString() }
            validator(ValidatorStringNonEmpty(field = "id"))
        }
    }


    repoDelete(title = "Удаление объекта из БД")

    answerPrepareChain(title = "Подготовка ответа")
}).build()
