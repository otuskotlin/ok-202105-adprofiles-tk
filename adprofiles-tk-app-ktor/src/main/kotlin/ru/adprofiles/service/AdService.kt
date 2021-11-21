package ru.adprofiles.service

import context.MpContext
import context.setOperation
import ru.adprofiles.repo.AdCrud

class AdService() {
    private val adCrud = AdCrud()

    suspend fun getAd(context: MpContext) =
        adCrud.getAd(context.setOperation(MpContext.MpOperations.GET))

    suspend fun updateAd(context: MpContext) =
        adCrud.updateAd(context.setOperation(MpContext.MpOperations.UPDATE))

    suspend fun createAd(context: MpContext) =
        adCrud.createAd(context.setOperation(MpContext.MpOperations.CREATE))

    suspend fun deleteAd(context: MpContext) =
        adCrud.deleteAd(context.setOperation(MpContext.MpOperations.DELETE))
}
