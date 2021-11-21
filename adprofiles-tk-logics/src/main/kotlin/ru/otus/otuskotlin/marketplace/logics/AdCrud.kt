package ru.otus.otuskotlin.marketplace.logics

import context.MpContext
import ru.otus.otuskotlin.marketplace.logics.chains.*

/**
 * Класс-фасад, содержащий все методы бизнес-логики
 */
class AdCrud {
    suspend fun create(context: MpContext) {
        AdCreate.exec(context.initSettings())
    }
    suspend fun get(context: MpContext) {
        AdGet.exec(context.initSettings())
    }
    suspend fun update(context: MpContext) {
        AdUpdate.exec(context.initSettings())
    }
    suspend fun delete(context: MpContext) {
        AdDelete.exec(context.initSettings())
    }

    // Метод для установки параметров чейна в контекст, параметры передаются в конструкторе класса
    private fun MpContext.initSettings() = apply { }
}
