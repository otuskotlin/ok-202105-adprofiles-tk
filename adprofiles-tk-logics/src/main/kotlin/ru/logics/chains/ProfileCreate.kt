package logics.chains

import cor.ICorExec
import cor.chain
import context.MpContext
import logics.chains.helpers.mpValidation
import logics.chains.stubs.adCreateStub
import logics.workers.*
import validation.validators.ValidatorStringNonEmpty

object ProfileCreate: ICorExec<MpContext> by chain<MpContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = MpContext.MpOperations.CREATE,
    )
    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    adCreateStub(title = "Обработка стабкейса для CREATE")

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

    repoCreate("Запись объекта в БД")

    answerPrepareChain(title = "Подготовка ответа")
}).build()
