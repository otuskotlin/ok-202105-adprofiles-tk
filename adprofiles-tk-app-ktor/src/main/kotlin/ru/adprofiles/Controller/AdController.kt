package ru.adprofiles.Controller

import Ad.toAdDto
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.adprofiles.service.AdService
import ru.tk.adprofiles.kmp.transport.models.AdDto

suspend fun ApplicationCall.getAd(adService: AdService) {
    val request = parameters["id"]!!

    respond(adService.getAd(request))
}

suspend fun ApplicationCall.createAd(adService: AdService) {
    val request = receive<AdDto>()
    respond(adService.createAd(request))
}

suspend fun ApplicationCall.updateAd(adService: AdService) {
    val request = receive<AdDto>()
    respond(adService.updateAd(request))
}

suspend fun ApplicationCall.deleteAd(adService: AdService) {
    val request = receive<String>()
    respond(adService.deleteAd(request))
}
