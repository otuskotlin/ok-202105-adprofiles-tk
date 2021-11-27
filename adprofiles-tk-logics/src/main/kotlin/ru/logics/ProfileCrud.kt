package logics

import context.ContextConfig
import context.MpContext
import logics.chains.*

/**
 * Класс-фасад, содержащий все методы бизнес-логики
 */
class ProfileCrud(val config: ContextConfig = ContextConfig()) {
    suspend fun create(context: MpContext) {
        ProfileCreate.exec(context.initSettings())
    }
    suspend fun read(context: MpContext) {
        ProfileRead.exec(context.initSettings())
    }
    suspend fun update(context: MpContext) {
        ProfileUpdate.exec(context.initSettings())
    }
    suspend fun delete(context: MpContext) {
        ProfileDelete.exec(context.initSettings())
    }

    // Метод для установки параметров чейна в контекст, параметры передаются в конструкторе класса
    private fun MpContext.initSettings() = apply {
        config = this@ProfileCrud.config
    }
}
