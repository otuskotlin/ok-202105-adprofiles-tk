package logics.chains

import cor.ICorExec
import cor.chain
import context.MpContext
import logics.chains.helpers.mpValidation
import logics.chains.stubs.adUpdateStub
import logics.workers.*
import logics.workers.answerPrepareChain
import logics.workers.chainInitWorker
import logics.workers.checkOperationWorker
import logics.workers.chooseDb
import validation.validators.ValidatorStringNonEmpty

object ProfileUpdate: ICorExec<MpContext> by chain<MpContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = MpContext.MpOperations.UPDATE,
    )
    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    adUpdateStub(title = "Обработка стабкейса для UPDATE")

    mpValidation {
        validate<String?> {
            on { requestProfile.firsName }
            validator(ValidatorStringNonEmpty(field = "firsName"))
        }
        validate<String?> {
            on { requestProfile.secondName }
            validator(ValidatorStringNonEmpty(field = "secondName"))
        }
    }

    repoUpdate(title = "Обновление данных об объекте в БД")

    answerPrepareChain(title = "Подготовка ответа")
}).build()
