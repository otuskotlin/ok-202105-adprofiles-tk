package backend.transport.mapping.kmp

import context.MpContext
import models.*
import ru.tk.adprofiles.openapi.models.*


fun MpContext.setQuery(query: InitProfileRequest) = apply {
    operation = MpContext.MpOperations.INIT
    onRequest = query.requestId ?: ""
}

fun MpContext.setQuery(query: CreateProfileRequest) = apply {
    operation = MpContext.MpOperations.CREATE
    onRequest = query.requestId ?: ""
    requestProfile = query.createProfile?.toModel() ?: ProfileModel()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase?.takeIf { workMode == WorkMode.STUB }.toModel()
}

fun MpContext.setQuery(query: ReadProfileRequest) = apply {
    operation = MpContext.MpOperations.READ
    onRequest = query.requestId ?: ""
    requestProfileId = ProfileIdModel(query.readProfileId ?: "")
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase?.takeIf { workMode == WorkMode.STUB }.toModel()
}

fun MpContext.setQuery(query: UpdateProfileRequest) = apply {
    operation = MpContext.MpOperations.UPDATE
    onRequest = query.requestId ?: ""
    requestProfile = query.createProfile?.toModel() ?: ProfileModel()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase?.takeIf { workMode == WorkMode.STUB }.toModel()
}

fun MpContext.setQuery(query: DeleteProfileRequest) = apply {
    operation = MpContext.MpOperations.DELETE
    onRequest = query.requestId ?: ""
    requestProfileId = ProfileIdModel(query.deleteProfileId ?: "")
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase?.takeIf { workMode == WorkMode.STUB }.toModel()
}


private fun UpdateableProfile.toModel() = ProfileModel(
    id = ProfileIdModel(id ?: ""),
    firsName = firsName ?: "",
    secondName = secondName ?: "",
    role = ProfileRoleModel.valueOf(role?.name ?: "NONE")
)

private fun CreateableProfile.toModel() = ProfileModel(
    firsName = firsName ?: "",
    secondName = secondName ?: "",
    role = ProfileRoleModel.valueOf(role?.name ?: "NONE")
)

private fun BaseDebugRequest.StubCase?.toModel() = when (this) {
    BaseDebugRequest.StubCase.SUCCESS -> MpStubCase.SUCCESS
    BaseDebugRequest.StubCase.DATABASE_ERROR -> MpStubCase.DATABASE_ERROR
    null -> MpStubCase.NONE
}

private fun BaseDebugRequest.Mode?.toModel() = when (this) {
    BaseDebugRequest.Mode.STUB -> WorkMode.STUB
    BaseDebugRequest.Mode.TEST -> WorkMode.TEST
    BaseDebugRequest.Mode.PROD -> WorkMode.PROD
    null -> WorkMode.PROD
}
