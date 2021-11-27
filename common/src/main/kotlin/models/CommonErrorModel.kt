package models

import exceptions.NoneException


data class CommonErrorModel(
    override var field: String = "",
    override var level: IError.Level = IError.Level.ERROR,
    override var message: String = "",
    override var exception: Throwable = NoneException,
) : IError {
    constructor(e: Throwable, level: IError.Level = IError.Level.ERROR, field: String = "") : this(
        field = field,
        level = level,
        message = e.message ?: "",
        exception = e
    )
}
