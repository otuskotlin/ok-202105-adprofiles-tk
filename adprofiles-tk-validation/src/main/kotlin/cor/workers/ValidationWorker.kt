package validation.cor.workers


import cor.ICorChainDsl
import cor.handlers.worker
import validation.cor.ValidationBuilder


fun <C> ICorChainDsl<C>.validation(block: ValidationBuilder<C>.() -> Unit) = worker {
    this.title = "Валидация"
    description = "Валидация логики"
//        on { status == CorStatus.RUNNING }
//        except { status = CorStatus.FAILING }
    val validator = ValidationBuilder<C>().apply(block).build()
    handle {
        validator.exec(this)
    }
}
