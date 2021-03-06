package validation

interface IValidator<T> {
    infix fun validate(sample: T): ValidationResult
}
