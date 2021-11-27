package validation

interface IValidationFieldError : IValidationError {
    val field: String
}
