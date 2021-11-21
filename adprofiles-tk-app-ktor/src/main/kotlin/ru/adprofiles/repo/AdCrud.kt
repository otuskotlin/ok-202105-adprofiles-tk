package ru.adprofiles.repo

import context.MpContext
import model.Ad

class AdCrud {
    fun getAd(context: MpContext) = context.apply {
        ad = Ad(id = "id", title = "get")
    }

    fun updateAd(context: MpContext) = context.apply {
        ad = Ad(id = "id", title = "update")
    }

    fun createAd(context: MpContext) = context.apply {
        ad = Ad(id = "id", title = "create")
    }

    fun deleteAd(context: MpContext) = context.apply {
        ad = Ad(id = "id", title = "delete")
    }
}
