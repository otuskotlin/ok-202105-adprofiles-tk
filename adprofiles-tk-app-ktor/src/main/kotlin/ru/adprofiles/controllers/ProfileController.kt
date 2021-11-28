package controllers

import backend.transport.mapping.kmp.toCreateResponse
import context.MpContext
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.tk.adprofiles.openapi.models.*
import services.ProfileService
import java.time.Instant

suspend fun ApplicationCall.initProfile(profileService: ProfileService) {
    val request = receive<InitProfileRequest>()
    val context = MpContext(
        startTime = Instant.now(),
    )

    val result = try {
        profileService.initProfile(context, request)
    } catch (e: Throwable) {
        profileService.errorProfile(context, e) as InitProfileResponse
    }
    respond(result)
}

suspend fun ApplicationCall.createProfile(profileService: ProfileService) {
    val context = MpContext(
        startTime = Instant.now(),
    )

    val result = try {
        val request = receive<CreateProfileRequest>()
        profileService.createProfile(context, request)
    } catch (e: Throwable) {
        context.addError(e)
        context.toCreateResponse()
    }
    respond(result)
}

suspend fun ApplicationCall.readProfile(profileService: ProfileService) {
    val request = receive<ReadProfileRequest>()
    val context = MpContext(
        startTime = Instant.now()
    )

    val result = try {
        profileService.readProfile(context, request)
    } catch (e: Throwable) {
        profileService.errorProfile(context, e) as ReadProfileResponse
    }
    respond(result)
}

suspend fun ApplicationCall.updateProfile(profileService: ProfileService) {
    val request = receive<UpdateProfileRequest>()
    val context = MpContext(
        startTime = Instant.now()
    )

    val result = try {
        profileService.updateProfile(context, request)
    } catch (e: Throwable) {
        profileService.errorProfile(context, e) as UpdateProfileResponse
    }
    respond(result)
}

suspend fun ApplicationCall.deleteProfile(profileService: ProfileService) {
    val request = receive<DeleteProfileRequest>()
    val context = MpContext(
        startTime = Instant.now()
    )

    val result = try {
        profileService.deleteProfile(context, request)
    } catch (e: Throwable) {
        profileService.errorProfile(context, e) as DeleteProfileResponse
    }
    respond(result)
}
