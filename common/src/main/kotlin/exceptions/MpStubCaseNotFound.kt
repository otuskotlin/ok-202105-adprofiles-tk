package exceptions

class MpStubCaseNotFound(stubCase: String): Throwable("There is no matchable worker to handle case: $stubCase")
