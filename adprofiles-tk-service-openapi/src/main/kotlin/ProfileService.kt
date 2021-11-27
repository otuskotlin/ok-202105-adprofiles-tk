package services

import backend.transport.mapping.kmp.*
import context.MpContext
import logics.ProfileCrud
import ru.tk.adprofiles.openapi.models.*
import services.exceptions.DataNotAllowedException

class ProfileService(
    private val crud: ProfileCrud,
) {

    suspend fun handleProfile(context: MpContext, request: BaseMessage): BaseMessage = try {
        when (request) {
            is InitProfileRequest -> initProfile(context, request)
            is CreateProfileRequest -> createProfile(context, request)
            is ReadProfileRequest -> readProfile(context, request)
            is UpdateProfileRequest -> updateProfile(context, request)
            is DeleteProfileRequest -> deleteProfile(context, request)
            else -> throw DataNotAllowedException("Request is not Allowed", request)
        }
    } catch (e: Throwable) {
        errorProfile(context, e)
    }

    suspend fun createProfile(context: MpContext, request: CreateProfileRequest): CreateProfileResponse {
        crud.create(context.setQuery(request))
        return context.toCreateResponse()
    }

    suspend fun readProfile(context: MpContext, request: ReadProfileRequest): ReadProfileResponse {
        crud.read(context.setQuery(request))
        return context.toReadResponse()
    }

    suspend fun updateProfile(context: MpContext, request: UpdateProfileRequest): UpdateProfileResponse {
        crud.update(context.setQuery(request))
        return context.toUpdateResponse()
    }


    suspend fun deleteProfile(context: MpContext, request: DeleteProfileRequest): DeleteProfileResponse {
        crud.delete(context.setQuery(request))
        return context.toDeleteResponse()
    }

    suspend fun errorProfile(context: MpContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.toReadResponse()
    }

    suspend fun initProfile(context: MpContext, request: InitProfileRequest): InitProfileResponse {
        context.setQuery(request)
        return context.toInitResponse()
    }

    suspend fun finishProfile(context: MpContext) {
        // TODO handle user disconnection
    }
}

