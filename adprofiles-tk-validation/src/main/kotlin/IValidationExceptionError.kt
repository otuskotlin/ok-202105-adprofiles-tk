package validation

interface IValidationExceptionError: IValidationError {
    val exception: Throwable
}
