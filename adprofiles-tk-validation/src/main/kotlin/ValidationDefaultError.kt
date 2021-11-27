package validation

data class ValidationDefaultError(
    override val message: String,
) : IValidationError
