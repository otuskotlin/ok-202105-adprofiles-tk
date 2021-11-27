package validation.cor

interface IValidationOperation<C, T> {
    fun exec(context: C)
}

