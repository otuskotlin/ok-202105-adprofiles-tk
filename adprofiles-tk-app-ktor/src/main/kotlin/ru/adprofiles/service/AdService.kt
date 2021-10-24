package ru.adprofiles.service

import Ad.toAd
import ru.adprofiles.repo.AdCrud
import ru.tk.adprofiles.kmp.transport.models.AdDto

class AdService() {
    private val adCrud = AdCrud()
    suspend fun getAd(id: String) = adCrud.getAd(id)
    suspend fun updateAd(adDto: AdDto) = adCrud.updateAd(adDto.toAd())
    suspend fun createAd(adDto: AdDto) = adCrud.createAd(adDto.toAd())
    suspend fun deleteAd(id: String) = adCrud.deleteAd(id)
}
