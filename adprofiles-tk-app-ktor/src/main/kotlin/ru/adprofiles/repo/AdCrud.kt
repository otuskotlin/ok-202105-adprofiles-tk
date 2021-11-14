package ru.adprofiles.repo

import Ad.toAdDto
import models.Ad

class AdCrud {
    fun getAd(id: String) = Ad(id = id, title = "get").toAdDto()
    fun updateAd(ad: Ad) = ad.apply { title = "update" }.toAdDto()
    fun createAd(ad: Ad) = ad.apply {
        id = id
        title = "create"
    }.toAdDto()
    fun deleteAd(id: String) = Ad(id = id, title = "delete").toAdDto()
}
