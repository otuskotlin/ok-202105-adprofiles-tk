package context

fun MpContext.setOperation(query: MpContext.MpOperations) = apply {
    operation = query
}
