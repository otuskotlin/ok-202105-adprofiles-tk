package backend.transport.mapping.kmp


import context.MpContext
import exceptions.MpOperationNotSet
import models.IError
import models.PermissionModel
import models.ProfileIdModel
import models.ProfileModel
import ru.tk.adprofiles.openapi.models.*
import java.time.Instant
import java.util.*

fun MpContext.toInitResponse() = InitProfileResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) InitProfileResponse.Result.SUCCESS
    else InitProfileResponse.Result.ERROR
)

fun MpContext.toReadResponse() = ReadProfileResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    readProfile = responseProfile.takeIf { it != ProfileModel() }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) ReadProfileResponse.Result.SUCCESS
    else ReadProfileResponse.Result.ERROR
)

fun MpContext.toCreateResponse() = CreateProfileResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    createdProfile = responseProfile.takeIf { it != ProfileModel() }?.toTransport(),
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateProfileResponse.Result.SUCCESS
    else CreateProfileResponse.Result.ERROR
)

fun MpContext.toUpdateResponse() = UpdateProfileResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    updatedProfile = responseProfile.takeIf { it != ProfileModel() }?.toTransport(),
    result = if (errors.find { it.level == IError.Level.ERROR } == null) UpdateProfileResponse.Result.SUCCESS
    else UpdateProfileResponse.Result.ERROR
)

fun MpContext.toDeleteResponse() = DeleteProfileResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    deletedProfile = responseProfile.takeIf { it != ProfileModel() }?.toTransport(),
    result = if (errors.find { it.level == IError.Level.ERROR } == null) DeleteProfileResponse.Result.SUCCESS
    else DeleteProfileResponse.Result.ERROR
)

fun MpContext.toResponse() = when (operation) {
    MpContext.MpOperations.INIT -> toInitResponse()
    MpContext.MpOperations.CREATE -> toCreateResponse()
    MpContext.MpOperations.READ -> toReadResponse()
    MpContext.MpOperations.UPDATE -> toUpdateResponse()
    MpContext.MpOperations.DELETE -> toDeleteResponse()
    MpContext.MpOperations.NONE -> throw MpOperationNotSet("Operation for error response is not set")
}

fun MpContext.toLog(logId: String) = CommonLogModel(
    messageId = UUID.randomUUID().toString(),
    messageTime = Instant.now().toString(),
    source = "ok-marketplace",
    logId = logId,
    marketplace = MpLogModel(
        requestProfileId = requestProfileId.takeIf { it != ProfileIdModel.NONE }?.asString(),
        requestProfile = requestProfile.takeIf { it != ProfileModel() }?.toTransport(),
        responseProfile = responseProfile.takeIf { it != ProfileModel() }?.toTransport(),
        responseProfiles = responseProfiles.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    ),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
)

private fun IError.toTransport() = RequestError(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
)

private fun ProfileModel.toTransport() = ResponseProfile(
    id = id.takeIf { it != ProfileIdModel.NONE }?.asString(),
    firsName = firsName.takeIf { it.isNotBlank() },
    secondName = secondName.takeIf { it.isNotBlank() },
    role = ProfileRole.valueOf(role.name)
)

private fun PermissionModel.toTransport() = when (this) {
    PermissionModel.READ -> ProfilePermissions.READ
    PermissionModel.UPDATE -> ProfilePermissions.UPDATE
    PermissionModel.DELETE -> ProfilePermissions.DELETE
    PermissionModel.CREATE -> ProfilePermissions.CREATE
    PermissionModel.NONE -> null
}
