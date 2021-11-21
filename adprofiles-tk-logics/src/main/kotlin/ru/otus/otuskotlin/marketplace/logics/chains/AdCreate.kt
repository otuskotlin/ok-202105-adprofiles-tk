package ru.otus.otuskotlin.marketplace.logics.chains

import context.MpContext
import ru.otus.otuskotlin.marketplace.common.cor.ICorExec
import ru.otus.otuskotlin.marketplace.common.cor.chain
import ru.otus.otuskotlin.marketplace.logics.workers.repoCreate

object AdCreate: ICorExec<MpContext> by chain<MpContext>({
    repoCreate("Запись объекта в БД")
}).build()
