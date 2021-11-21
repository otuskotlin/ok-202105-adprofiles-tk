package ru.adprofiles.Controller

import Ad.toAd
import Ad.toAdDto
import context.MpContext
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import ru.adprofiles.service.AdService
import ru.tk.adprofiles.kmp.transport.models.AdDto

suspend fun ApplicationCall.getAd(adService: AdService) {
    val request = receive<AdDto>()
    val context = MpContext(request.toAd())
    respond(adService.getAd(context).ad.toAdDto())
}

suspend fun ApplicationCall.createAd(adService: AdService) {
    val request = receive<AdDto>()
    val context = MpContext(request.toAd())
    respond(adService.createAd(context).ad.toAdDto())
}

suspend fun ApplicationCall.updateAd(adService: AdService) {
    val request = receive<AdDto>()
    val context = MpContext(request.toAd())
    respond(adService.updateAd(context).ad.toAdDto())
}

suspend fun ApplicationCall.deleteAd(adService: AdService) {
    val request = receive<AdDto>()
    val context = MpContext(request.toAd())
    respond(adService.deleteAd(context).ad.toAdDto())
}
