package logics.chains.helpers

import cor.ICorChainDsl
import context.CorStatus
import context.MpContext
import models.CommonErrorModel
import validation.IValidationFieldError
import validation.cor.ValidationBuilder
import validation.cor.workers.validation

fun ICorChainDsl<MpContext>.mpValidation(block: ValidationBuilder<MpContext>.() -> Unit) = validation {
    errorHandler { validationResult ->
        if (validationResult.isSuccess) return@errorHandler
        val errs = validationResult.errors.map {
            CommonErrorModel(
                message = it.message,
                field = if (it is IValidationFieldError) it.field else "",
            )
        }
        errors.addAll(errs)
        status = CorStatus.FAILING
    }
    block()
}
