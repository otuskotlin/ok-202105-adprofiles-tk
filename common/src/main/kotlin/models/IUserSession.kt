package models

import context.MpContext


interface IUserSession<T> {
    val fwSession: T

    suspend fun notifyProfileChanged(context: MpContext)

    companion object {
        object EmptySession : IUserSession<Unit> {
            override val fwSession: Unit = Unit
            override suspend fun notifyProfileChanged(context: MpContext) {
                TODO("Not yet implemented")
            }

        }
    }
}

