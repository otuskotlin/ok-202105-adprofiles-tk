package ru.otus.otuskotlin.marketplace.logics.chains

import context.MpContext
import ru.otus.otuskotlin.marketplace.common.cor.ICorExec
import ru.otus.otuskotlin.marketplace.common.cor.chain
import ru.otus.otuskotlin.marketplace.logics.workers.repoDelete

object AdDelete: ICorExec<MpContext> by chain<MpContext>({
    repoDelete(title = "Удаление объекта из БД")
}).build()
