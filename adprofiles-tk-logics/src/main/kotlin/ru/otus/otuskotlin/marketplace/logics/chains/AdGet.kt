package ru.otus.otuskotlin.marketplace.logics.chains

import context.MpContext
import ru.otus.otuskotlin.marketplace.common.cor.ICorExec
import ru.otus.otuskotlin.marketplace.common.cor.chain
import ru.otus.otuskotlin.marketplace.logics.workers.repoGet

object AdGet: ICorExec<MpContext> by chain<MpContext>({
    repoGet(title = "Получение объекта из БД")
}).build()
